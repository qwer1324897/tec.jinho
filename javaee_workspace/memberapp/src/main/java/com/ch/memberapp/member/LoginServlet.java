package com.ch.memberapp.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ch.memberapp.util.ShaManager;

// 로그인 요청을 처리하는 서블릿
public class LoginServlet extends HttpServlet{
	
	String dburl = "jdbc:mysql://localhost:3306/java";
	String dbuser = "servlet";
	String dbpwd = "1234";
	
	Connection connection;
	PreparedStatement pstmt;
	ResultSet rs; 	// select 문의 결과를 담게 될 객체. 참고) 데이터를 가저오자마자 커서는 before first 에 가 있다.
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		
		PrintWriter out = response.getWriter();
		
		out.print("클라이언트가 전송한 아이디: " + id + "<br>");
		out.print("클라이언트가 전송한 비밀번호: " + pwd + "<br>");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			out.print("드라이버 로드 성공");
			connection = DriverManager.getConnection(dburl, dbuser, dbpwd);
			StringBuffer tag = new StringBuffer();
			
			
			if (connection==null) {
				out.print("연결 실패");
			} else {
				out.print("연결 성공");
				
				// 쿼리 준비
				String sql = "select * from member where id=? and pwd=?";
				pstmt = connection.prepareStatement(sql);
				pstmt.setString(1, id);
				pstmt.setString(2, ShaManager.getHash(pwd));
				
				// 쿼리 실행
				rs = pstmt.executeQuery();
				
				tag.append("<script>");
				if (rs.next()==false) {		// rs 의 커서를 next() 했을 때 true 를 반환하면 레코드가 존재한다는 뜻이므로, 이 회원은 로그인이 성공됨.
					tag.append("alert('로그인 실패');");
					response.sendRedirect("/member/login.jsp");
				} else {
					tag.append("alert('로그인 성공');");
					tag.append("location.href='/'"); 	// 클라이언트 브라우저가 루트 페이지로 재접속하게 만듬
					
					// 로그인을 성공한 회원의 경우, 브라우저를 끄지 않는 한 계속 기억 효과를 내야 하므로,
					// 서버의 메모리에 회원 정보를 저장할 수 있는 객체를 올려야 함. 이러한 목적의 객체를 Session 객체라 한다.
					// 지금의 경우, 로그인 성공 이후 회원에게 회원정보를 기억한 효과를 내려면 회원 정보를 Session 에 담아두면 된다.
					// 그라고 담아진 정보는 사용자가 브라우저를 닫기 전까지 계속 사용할 수 있음. 
					// 예외) 서버에서 정해놓은 시간동안 재요청이 없으면 자동으로 세션을 소멸시킨다.
					
					HttpSession session = request.getSession(); 	// 톰캣이 관리하므로, 개발자가 직접 new 할 수 없는 인터페이스. 즉, 시스템으로 얻어와야 한다.
					// 주의) 세션은 브라우저로 들어오거나, 개발자가 세션을 건드린다고 해서 생성되진 않는다.
					// 로그인이 아닌 브라우저의 요청은 세션을 만들 필요가 없기 때문.
					String sessionId = session.getId(); 	// 현재 생성된 세션에 자동으로 발급된 고유값
					System.out.println("이 요청에 의해 생성된 세션의 id는 " + sessionId);
					
					Member member = new Member();
					
					member.setMember_id(rs.getInt("member_id")); //  member_id도 integer 타입으로 최초에 해놓으면 바로 getString할 수 있는거 아닌가
					member.setId(rs.getString("id"));
					member.setPwd(rs.getString("pwd"));
					member.setName(rs.getString("name"));
					member.setRegdate(rs.getString("regdate"));
					
					// 회원 1명에 대한 정보가 채워진 DTO의 인스턴스를 세션에 담아두자(브라우저를 끌 때 까지는 회원 정보를 계속 보여줄 수 있다)
					// HttpSession 은 Map 을 상속받는다. 따라서 Map형이다.
					// Map은 java의 컬렉션 프레임웍이다(자료구조) - 다수의 데이터 중 오직 객체만을 대상으로 효율적 데이터 처리를 위해 지원하는 자바의 라이브러리.
					// java util 패키지에서 지원한다.
					// 1. 순서 있는 객체를 다룰 때 지원되는 자료형(배열과 흡사) List
					// 2. 순서가 없는 객체를 다룰 때 Set
					// 3. 순서가 없는 객체 중 특히 key-value 의 쌍을 갖는 데이터 조합 - Map
					//		오전에 사용했던 js의 객체 표기법 자체가 사실은 Map으로 구성됨
					/*
					 * 	let member = {
					 * 		name : "Jin",
					 * 		age : 30
					 * }*/
					session.setAttribute("member", member);
				}
				 tag.append("</script>");
				out.print(tag.toString());	// 스트림에 스크립트 담아놓기, 추후에 톰캣이 이 스트림을 보고 코딩을 한다.
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
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