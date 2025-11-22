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

//  클라이언트의 이미지 업로드를 처리할 서블릿
public class PracServlet extends HttpServlet{
	
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "servlet"; 
	String pwd =  "1234";
	
	// 클라이언트의 post 요청을 처리할 메서드
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter(); 	// 리스펀스 객체가 보유한 스트림 얻기
		
		// 업로드를 처리한 cos 컴포넌트를 사용해보자.
		// cos란 서블릿 환경에서 멀티파트(Multipart) 요청, 즉 파일 업로드를 쉽게 처리할 수 있게 해주는 라이브러리다.
		// 이 라이브러리 안에, MultipartRequest 라는 클래스가 있는데 이 클래스는 파일 데이터가 포함된 복잡한 멀티파트 요청을 추상화해서
		// 개발자가 파일 업로드를 몇 줄의 코드로 쉽게 구현할 수 있게 해주는 클래스다. (이런 게 있다는 걸 인지하고, 필요할 때 찾아 쓰는 게 실력)
		// MultipartRequest 는 일반 클래스라서, new 를 써서 인스턴스를 직접 생성할 수 있다.
		// 또한 파일명에 한글이 포함되어 있어도 깨지지 않도록 처리되어 있다.
		
		int maxSize = 1024*1024*10;	  // 업로드 가능한 최대 용량을 설정하기 위해 변수로 뺀다.
		// 변수로 빼는 이유는, 유지보수를 위해. 그냥 에라모르겠다 기본단위인 바이트값(10,485,760) 넣어버리면 유지보수가 힘드니까.
		// 그래서 변수 설정할 때도 이렇게 1024로 풀어서 10MB인걸 보기 좋게 설정. 나중에 용량 늘리거나 줄일때 맨 마지막 10만 수정하면 된다.
		
		MultipartRequest multi = new MultipartRequest(req, "c:\\prac_upload", maxSize, "utf-8");
		
		// 클라이언트가 전송한 데이터 중, 텍스트 데이터(사진 제목)를 파라미터로 받아보자.
		// 데이터 인코딩 형식이 multipart/for-data 일 때는 기존 파라미터를 받는 코드인 getParameter()는 안되기 때문에
		// 업로드를 처리한 컴포넌트를 통해 파라미터 값을 직접 추출해줘야 한다.
		String title = multi.getParameter("title");
		out.print("클라이언트가 전송한 제목 " + title);
		
		// 이렇게 텍스트만 추출하면 끝이 아니다. 업로드 된 파일의 파일명이 대체 무슨 파일명일지 모른다.
		// 혹시라도 파일명이 이상하면 웹 브라우저에서 표현 시 불안할 수 있기 때문에 파일명을 개발자가 정한 규칙, 알고리즘으로 변경한다.
		// 보통 대표적인 방법으로는 현재 시간(밀리세컨드까지), 해시값으로 16진수 문자열을 만든다.
		// 보통 웹사이트에서 파일 다운할 때 보면 가끔 1623587298.jpg 이런 파일명으로 다운될 때가 있는데, 그게 이 경우.
		long time = System.currentTimeMillis();
		
		out.print(time);
		out.print("<br>");
		out.print("업로드 성공");
		
		// 여기서 끝이 아니다.
		// 파일을 업로드하는 클라이언트들은 개발자가 아니기 때문에, 제목을 "지난 여름휴가 사진.jpg" 이런 식으로 저장할 것이다.
		// 따라서 개발자들은 혹시모를 띄어쓰기나 한글로 인한 오류방지를 위해 서버에 저장할 때, 방금 업로드 된 파일명을 조사해서
		// 현재 시간과 확장자를 조합해 새로운 파일명을 만든다. 업로드 된 파일 정보는 이미 파일 컴포넌트 스스로 알고 있다.
		// 지금의 경우, multi 다.
		String oriName = multi.getOriginalFileName("photo");		// HTML에서 부여한 파라미터명을 매개변수로 넣어준다.
		out.print("<br>");
		out.print(oriName);
		
		String extend = StringUtil.getExtendFrom(oriName); 	// util 폴더에 api처럼 쓰려고 저번에 저장해놓은 클래스를 써서 변수로 받는다.
		// 기능은 파일 경로에서 확장자만을 추출.
		
		out.print("<br>");
		out.print("추출된 확장자는 " + extend + " ");
		
		// 이제 파일명과 확장자를 다 정했기 때문에 업로드 된 파일의 이름을 변경한다.
		// 자바에서는 파일명을 변경하거나 삭제하려면 java.io.File 클래스를 이용한다.
		File file = multi.getFile("photo");	 	// 서버에 업로드 된 파일을 반환.
		
		out.print("<br>");
		out.print(file);
		
		// 이제 파일명을 반환받았으니, File 클래스의 메서드 중, 파일명을 바꾸는 메서드 renameTo()를 사용한다.
		// 항상 api 는 이런 게 있다는 걸 인지해놓고, 필요할 때 찾아쓰는 게 실력.
		// renameTo() 메서드의 매개변수에는 새롭게 생성 될 파일의 경로를 넣어야 한다.
		String fileName = time+"."+extend;		// 파일명은 재사용성이 높으니까 변수로 받아둔다. 안 하고 그냥 time+"."+extend 이거 쓰면 되긴 함.
		
		// 이제 이 fileName을 renameTo() 메서드를 써서 파일명을 받을건데, 중요한 점이 있다. 
		// 파일 저장이 확실하게 완료된 경우에만 DB에 기록하기 위한 안전장치가 필요하다.

		// 1. renameTo() 메서드의 특성 (기술적 이유)
		// File 클래스의 renameTo() 메서드는 동작이 실패했을 때 에러(Exception)를 터뜨리지 않고, 단순히 결과(성공/실패)를 true or  false 로 반환한다.
		// 성공 시: true 반환. 실패 시: false 반환 (예: 경로가 잘못됨, 이미 같은 이름의 파일이 있음, 권한이 없음 등)
		// 따라서 보통의 경우처럼 try-catch로 예방이 불가능하다. 반드시 변수(boolean result)로 결과를 받아서 if 문으로 성공 여부를 직접 확인해야 한다.

		// 2. 데이터의 무결성 보장 (논리적 이유)
		// 이 부분이 가장 중요하다. 웹 서비스에서 "파일 업로드"와 "DB 저장"은 한 몸처럼 움직여야 한다.
		// 만약 renameTo() 메서드를 쓰면서 if 문 없이 없이 그냥 코드를 짰는데(혹은 다른 메서드의 경우, try-catch 문을 안 쓴 채로 코드를 짰는데)
		// 파일 이름 변경에 실패했다고 가정해 보자.
		
		// 상황: 파일 이름 변경 실패 (실제 파일은 저장되지 않음/사라짐/이동 실패).
		// 문제: 코드는 계속 진행되어 insert into gallery 쿼리를 실행함.
		
		// 결과: DB에는 "사진A.jpg"라는 데이터가 있다. 서버의 실제 폴더에는 "사진A.jpg"가 없다.
		// 사용자 입장에선 게시글을 클릭했는데 이미지가 안 뜬다. (엑스박스)
		
		// 이러한 가짜 데이터가 생기는 것을 막기 위해, "파일 처리가 완벽하게 끝났어? 그렇다면 (if) 그때 DB에 넣어"라고 순서를 제어해야 한다.(혹은 try-catch)
		
		boolean result = file.renameTo(new File("c:\\prac_upload", fileName));	// 이제 이걸 if 문으로 try-catch 처럼 유효성 검사를 한다.
		out.print("<br>");
		
		if (result) {
			out.print("업로드 성공");
			
			Connection connection = null;
			PreparedStatement pstmt = null;
			
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				connection = DriverManager.getConnection(url, user, pwd);
				
				if (connection==null) {
					out.print("접속 실패");
				} else {
					out.print("접속 성공");
					String sql = "insert into gallery(gallery_id, title, filename) values(seq_gallery.nextval, ?,?)";
					pstmt = connection.prepareStatement(sql);	// 접속 객체로부터 쿼리수행 객체 인스턴스 얻기
					
					// 쿼리문 수행에 앞서 바인드 변수 값을 결정.
					pstmt.setString(1, title);
					pstmt.setString(2, fileName);
					
					// 이제 최종 쿼리 수행. DML 이므로 executeUpdate() 사용.
					// excuteUpdate() 는 쿼리 수행 후 영향을 받은 레코드 수를 반환하므로,
					// insert 가 성공이라면 0이 아니어야 한다.
					int n = pstmt.executeUpdate();
					if (n<1) {
						out.print("등록 실패");
					} else {
						out.print("등록 성공");
						// 목록으로 자동 전환
						resp.sendRedirect("/upload/praclist.jsp");
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (pstmt !=null) {
					try {
						pstmt.close();
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					} 
				}
			}
		} else { 	// 오라클에 접속 해 insert 문을 수행한다.	
			out.print("업로드 실패");
		}   
	}
}