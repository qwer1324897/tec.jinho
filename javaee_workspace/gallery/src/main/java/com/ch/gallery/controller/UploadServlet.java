package com.ch.gallery.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.gallery.util.StringUtil;
import com.oreilly.servlet.MultipartRequest;

import oracle.net.aso.n;
// 클라이언트의 업로드를 처리할 서블릿
public class UploadServlet extends HttpServlet{
	
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "servlet"; 
	String pwd =  "1234";
	
	// 클라이언트의 post 요청을 처리 할 메서드
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();		// 응답 객체가 보유한 스트림 얻기
		
		// 업로드를 처리할 cos 컴포넌트를 사용해보자
		// MultipartRequest 객체는 일반 클래스이므로, 개발자가 new 연산자를 이용하여 인스턴스를 직접 생성할 수 있다.
		// 따라서 이 객체가 지원하는 생성자를 조사해서 사용하면 된다.
		
		// MultipartRequest 는 생성자에서 업로드 처리를 하는 객체이다.
		// API 에 의하면 4번 째 생성자는 용량 뿐만 아니라, 파일명에 한글이 포함되어 있어도 깨지지 않도록 처리가 되어 있다.
		// 용량의 기본 단위는 바이트다.
		int maxSize=1024*1024*10;	// 유지 보수를 위해 직접 10MB를 계산해서 값을 넣지 않고, 이렇게 풀어서 쓴다.
		MultipartRequest multi = new MultipartRequest(req, "C:\\upload", maxSize, "utf-8");
		
		// 클라이언트가 전송한 데이터 중 텍스트 기반의 데이터를 파라미터를 이용하여 받아보자
		// 클라이언트가 전송한 데이터 인코딩 형식이 multipart/for-data 일 때는 기존에 파라미터를 받는 코드인
		// request.getParameter()는 동작하지 않기 때문에, 업로드를 처리한 컴포넌트를 통해 파라미터 값들을 추출해 줘야 한다.
	    String title = multi.getParameter("title");	
	    out.print("클라이언트가 전송한 제목 " + title);
		
		// 이미 업로드 된 파일은, 사용자가 정한 파일명이므로, 웹 브라우저에서 표현 시 불안할 수 있음.
		// 따라서 파일명을 개발자가 정한 규칙, 또는 알고리즘으로 변경한다.
		// 방법) 예 - 현재 시간(밀리세컨드까지 표현), 해시 - 16진수 문자열
		long time = System.currentTimeMillis();
		
		out.print(time);
		out.print("<br>");
		out.print("업로드 성공");
		
		// 클라이언트들은 개발자가 아니기 때문에, 파일명을 "지난 여름 사진.jpg" 이런식으로 저장한다. 따라서 개발자들은
		// 파일명으로 인한 오류방지를 위해 서버에 저장할 때, 방금 업로드 된 파일명을 조사하여 현재 시간과 확장자를 조합해 새로운 파일명을 만든다.
		// 이미 업로드 된 파일 정보는 파일 컴포넌트 스스로 알고 있다. 지금의 경우, multi 다.
		String oriName = multi.getOriginalFileName("photo");	// HTML 에서 부여한 파라미터명을 매개변수로 넣어준다.
		out.print("<br>");
		out.print(oriName);
		
		String extend = StringUtil.getExtendFrom(oriName);	// util 폴더에 api처럼 쓰려고 직접 지정해놓은 클래스 사용. 기능은 파일 경로에서 확장자만을 추출.
		
		out.print("<br>");
		out.print("추출된 확장자는" + extend);
		
		// 파일명과 확장자를 다 정했기 때문에 이제 업로드 된 파일의 이름을 변경한다.
		// 자바에서는 파일명을 변경하거나 삭제하려면 java.io.File 클래스를 이용한다.
		File file=multi.getFile("photo");	// 서버에 업로드 된 파일을 반환해준다.
		out.print("<br>");
		out.print(file);
		
		// File 클래스 메서드 중 파일명을 바꾸는 메서드 renameTo() 를 사용한다.
		// renameTo() 메서드의 매개변수에는 새롭게 생성 될 파일의 경로를 넣어야 한다.
		String filename = time+"."+extend;	// 재사용성이 있으므로, 변수 설정
		boolean result = file.renameTo(new File("C:/upload/"+time+"."+extend));	// 이제 이걸 최종적으로 불리언으로 받아서 trycatch 처럼 마무리.
		out.print("<br>");
		
		if(result) {
			out.print("업로드 성공");
			
			Connection connection = null;
			PreparedStatement pstmt = null;
			
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				connection = DriverManager.getConnection(url, user, pwd);
				if(connection==null) {
					out.println("접속 실패");
				} else {
					out.println("접속 성공");
					String sql = "insert into gallery(gallery_id, title, filename) values(seq_gallery.nextval, ?,?)";
					pstmt = connection.prepareStatement(sql);	// 접속 객체로부터 쿼리수행 객체 인스턴스 얻기
					
					// 쿼리문 수행에 앞서 바인드 변수 값을 결정하자
					pstmt.setString(1, title);
					pstmt.setString(2, filename);
					
					// 최종 쿼리 수행. DML이므로 executeUpdate() 사용.
					// executeUpdate() 는 쿼리 수행 후 영향을 받은 레코드 수를 반환하므로,
					// insert 가 성공이라면 0이 아니여야 한다.
					int n = pstmt.executeUpdate();
					if(n<1) {
						out.print("등록 실패");
					} else {
						out.print("등록 성공");
						// 목록으로 자동 전환
						resp.sendRedirect("/upload/list.jsp");
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if(pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			// 오라클에 접속해 insert 문을 수행한다.
			
		} else {
			out.print("업로드 실패");
		}
	}
}





