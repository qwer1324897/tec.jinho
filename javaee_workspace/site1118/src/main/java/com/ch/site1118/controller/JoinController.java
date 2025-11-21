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

import com.ch.site1118.util.EmailManager;

// 클라이언트가 전송한 파라미터들을 받아서, 오라클에 넣어보자
// 클라리언트의 요청이 웹브라우저 이므로, 웹상의 요청을 받을 수 있다.
// 따라서 오직 서버에서만 실행될 수 있는 클래스인 서블릿으로 정의한다.
public class JoinController extends HttpServlet{
	
	EmailManager emailManager = new EmailManager();
	
	// doXXX 형 메서드 중 post 방식을 처리하기 위한 doPost 메서드를 오버라이드 한다.
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 나의 이름이 웹 브라우저에 출력되게 해보자.
		response.setCharacterEncoding("utf-8"); // 이 html이 지원하는 인코딩 타입을 지정(한글 깨지지 않기 위함)
		response.setContentType("text/html"); // 브라우저에게 전송할 데이터가 html 문서임을 알려줌
		PrintWriter out = response.getWriter();	// 응답 객체가 보유한 출력스트림 얻기
		// 주의. 아래의 코드에 의해 클라이언트의 브라우저에 곧바로 데이터가 전송되는 것이 아닌 
		// 추후 응답이 마무리되는 시점에 Tomcat과 같은 컨테이너 서버가
		// out.print()에 의해 누적된 문자열을 이용하여 새로운 html 문서를 작성할 때 사용됨.
		out.print("<h1>김태호</h1>");
		
		// JDBC를 오라클에 insert
		// 드라이버가 있어야 오라클을 제어할 수 있다. 따라서 드라이버 jar 파일을 클래스패스에 등록해야 한다.
		// 하지만 현재 사용중인 IDE가 이클립스라면, 굳이 환경변수까지 등록할 필요없고, 이클립스에 등록하면 된다.
		// 드라이버 로드
		
		Connection connection=null;	// finally 에서 닫기 위해 여기서 선언한다.
		PreparedStatement pstmt = null;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			out.print("드라이버 로드 성공");
			
			// 오라클에 접속
			String url="jdbc:oracle:thin:@localhost:1521:XE";
			String user="servlet";
			String password="1234";
			
			// 접속 후, 접속이 성공했는지 알기 위해서는, Connection 인터페이스의 null 여부를 확인한다.
		 	connection=DriverManager.getConnection(url, user, password);
			if (connection==null) {
				out.print("\n접속 실패");
			} else {
				out.print("\n접속 성공");
				
				// 쿼리 수행 PreparedStatement 인터페이스가 담당
				// JDBC는 DB 제품의 종류가 무엇이든 상관없이 DB를 제어할 수 있는 코드가 동일하다.
				// JDBC 드라이버 제작을 벤더사가 하기 때문에 JavaEE 를 다룰 때 별도의 개발툴킷을 설치할 필요가 없다.
				// 오라클사는 JavaEE에 대한 스펙만을 명시하고, 실제 서버는 개발하지 않는다.
				// 결국 JavaEE 스펙을 따라 서버를 개발하는 벤더사들 모두가 각자 고유의 기술로 서버는 개발하지만,
				// 반드시 JavaEE 에 명시된 객체명. api 를 유지해야 하므로, JAVA 개발자들에게는 어떠한 종류의 서버이던 코드가 유지된다.
				String sql = "insert into member(member_id, id, pwd, name, email)";
				sql+=" values(seq_member.nextval, ?,?,?,?)";	// 바인드 변수
				pstmt=connection.prepareStatement(sql);				
				
				// 바인드 변수를 사용하려면, ? 의 값이 무엇인지 개발자가 PreparedStatement 에게 알려줘야 한다.
				// 클라이언트가 전송한 파라미터 받기
				// 네트워크로 전송 된 모든 파라미터는 모두 문자열로 인식한다.
				request.setCharacterEncoding("utf-8");	// 요청 정보를 가진 객체인 request에게 인코딩 지정
				String id = request.getParameter("id");
				String pwd = request.getParameter("pwd");
				String name = request.getParameter("name");
				String email = request.getParameter("email");
				
				// PreparedStatement 에게 쿼리문에 사용 할 바인드 변수 값을 알려준다.
				pstmt.setString(1, id);
				pstmt.setString(2, pwd);
				pstmt.setString(3, name);
				pstmt.setString(4, email);
				
				// 쿼리문을 실행하자.
				int result = pstmt.executeUpdate(); // DML(insert,update,delete) 수행 시 사용하는 메서드
				// executeUpdate() 는 반환값이 int. 이 int 의 의미는 현재 쿼리문에 의해 영향 받은 레코드 수를 반환한다.
				// 예) insert 후 1건이 반영되므로 1이 반환. update, delete는 n이 반환.
				// 0이 반환되면 반영된 레코드가 없다는 의미로, 쿼리 반영 실패를 의미한다.
				if (result!=0) {
					out.print(" 가입 성공");
					emailManager.send(email);
					
					// 회원 목록 페이지 보여주기
					response.sendRedirect("/member/list");	// 브라우저로 하여금 지정한 url 로 다시 접속(들어오라는) 명령.
					
				} else {
					out.print(" 가입 실패");
				}
			}
		} catch (ClassNotFoundException e) {
			out.print("드라이버 로드 실패");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}	
}




