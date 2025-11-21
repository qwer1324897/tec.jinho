package com.ch.site1118.controller;

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

// 회원 등록 요청을 처리할 서블릿 클래스
// HTTP 요청 방식 중, 클라이언트가 서버로 데이터를 전송하는 빙식은 POST 방식이다.
// 따라서, HTTP 서블릿이 보유한 doXXX형 메서드 중, doPost를 재정의 해야 함.

public class RegistController extends HttpServlet{
	
	// post 요청을 처리하는 메서드
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("클라이언트의 post 요청 감지");  // 웹 브라우저가 아닌 현재 톰캣의 콘솔에 출력
		// 클라이언트가 전송한 id, pw, name을 받아서 출력해보자.
		
		// 파라미터가 영어가 아닐 때 깨지지 않으려면, request 객체 인코딩을 지정해야 한다.
		request.setCharacterEncoding("utf-8");  // 당연히 파라미터를 받기 전에 세팅해야 한다.
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pwd");
		String name = request.getParameter("name");
		
		System.out.println("전송받은 아이디는 " +  id);
		System.out.println("전송받은 패스워드는 " + pw);
		System.out.println("전송받은 이름는 " + name);
		
		// 응답객체가 보유한 문자기반의 출력스트림에, 개발자가 유저에게 전달하고 싶은 메세지를 보관.
		response.setContentType("text/html"); 	// 브라우저에게 이 문서의 형식이 html임을 알린다.
		response.setCharacterEncoding("utf-8");
		// 이 html에서 사용 될 문자열에 대해 전 세계 모든 언어가 깨지지 않도록 utf-8 인코딩 한다.
		
		PrintWriter out = response.getWriter();
		
		// mysql에 넣어주기
		
		// java 언어가 해당 DB 서버를 제어하려면, 접속에 앞서 최우선으로 해당 DB제품을 핸들링할 수 있는
		// jar형태의 라이브리러리(=드라이버)를 보유하고 있어야 한다.
		// 보통 드라이버는 java 가 자체적으로 보유할 수 없다. (java 입장에서는 어떤 DB가 존재하는 지 알 수 없기 때문)
		// 따라서 드라이버는 DB 제품을 판매하는 벤더사에 있다.
		
		// jvm 의 3가지 메모리 영역 중 Method 영역에 동적으로 클래스를 Load 시킨다.
		// 보통은 jvm 을 자동으로 로드해주지만, 개발자가 원하는 시점에 로드시킬 경우
		// 아래와 같은 Class 클래스가 static 메서드인 forName() 메서드를 사용하기도 한다.
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이버 로드 성공");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("드라이버 로드 실패");
		}
		
		// mysql 접속
		// 자바에서 DB를 다루는 기술을 가리켜 JDBC(Java DataBase Connectivity)라고 한다.
		// 이 기술은 JavaEE 와 Java.sql 패키지에서 주로 지원한다.
		// 현재 우리가 개발중인 분야가 JavaEE 라면, JavaEE는 이미 JavaSE를 포함하고 있다.
		
		String url="jdbc:mysql://localhost:3306/java";
		String user="servlet";
		String pass="1234";
		
		Connection connection=null;
		PreparedStatement pstmt = null;
		
		try {
			connection = DriverManager.getConnection(url, user, pass);
			// Connection - 접속에 성공하면 그 정보를 가진 객체이므로, 접속을 끊고 싶을 때 이 객체를 이용하면 된다.
			// 예) con.close(); > 접속 해제
			// 주의) jdbc에서 DB에 접속 성공 여부를 판단할 때는 try문이 성공, catch문이 실패라고 생각하면 안 된다.
			// getConnection() 메서드가 반환해주는 Connection 인터페이스의 null 여부로 판단해야 한다.
			if (connection == null) {
				System.out.println("접속 실패");
			} else {
				System.out.println("접속 성공");
				
				// insert 문 수행
				// JDBC 객체 중 쿼리수행을 담당하는 객체는 PreparedStatement 인터페이스다.
				// 당연히 이 객체는 접속을 성공해야만 얻을 수 있다.
				pstmt = connection.prepareStatement("insert into member(id, pwd, name) values('"+id+"','"+pw+" ',' "+name+" ')");
				
				// 준비된 쿼리문을 실행하자
				int result = pstmt.executeUpdate();	// DML, 메서드 실행 후 반한되는 값은 이 메서드에 영향받은 레코드 수가 반환된다.
				// 따라서 1 보다 작은 수가 반환 되면, 이 쿼리에 의해 영향을 받은 레코드가 없으므로, 수행에 실패한다.
				if (result<1) {
					System.out.println("등록 실패");
					out.print("<script>"); // html 문서에 스크립트"
					out.print("alert('등록 실패');"); 
					out.print("</script>"); 
				} else {
					System.out.println("등록 성공");
					out.print("<script>"); // html 문서에 스크립트"
					out.print("alert('등록 성공');"); 
					out.print("</script>"); 
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {	// 존재할 때만 닫음
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}










