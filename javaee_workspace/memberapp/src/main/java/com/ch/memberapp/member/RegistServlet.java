package com.ch.memberapp.member;

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

import com.ch.memberapp.util.ShaManager;

// 회원가입 요청을 처리하는 서블릿
public class RegistServlet extends HttpServlet{
	
	String dburl = "jdbc:mysql://localhost:3306/java";
	String dbuser = "servlet";
	String dbpwd = "1234";
	
	Connection connection;
	PreparedStatement pstmt;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8"); 
		request.setCharacterEncoding("UTF-8");
		
		// 넘겨받은 파라미터 중, 비밀번호를 암호화 시켜서 DB 에 insert
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("name");
		
		PrintWriter out = response.getWriter();
		
		out.print("아이디는 " + id + "<br>");
		out.print("비밀번호는 " + ShaManager.getHash(pwd) + "<br>");
		out.print("이름은 " + name + "<br>");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			out.print("드라이버 로드 성공");
			connection = DriverManager.getConnection(dburl, dbuser, dbpwd);
			if (connection==null) {
				out.print("접속 실패");
			} else {
				out.print("접속 성공");
				
				String sql = "insert into member(id, pwd, name) values(?,?,?)";
				pstmt = connection.prepareStatement(sql);
				
				pstmt.setString(1, id);
				pstmt.setString(2, ShaManager.getHash(pwd));
				pstmt.setString(3, name);
				
				int result = pstmt.executeUpdate();
				
				StringBuffer tag = new StringBuffer();
				tag.append("<script>");
				
				if (result<1) {
					tag.append("alert('가입실패');");
					response.sendRedirect("/member/signup.jsp");
				} else {
					tag.append("alert('가입성공');");
					tag.append("location.href='/memberapp/login.jsp");
					// 브라우저로 하여금 지정한 url 로 다시 재접속하도록 명령
				}
				tag.append("</script>");
				out.print(tag.toString());	// 스트림에 스크립트 담아놓기, 추후에 톰캣이 이 스트림을 보고 코딩을 한다.
				
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			out.print("드라이버 로드 실패");
		 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
