package com.ch.site1118.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.Tag;

// 오라클에 들어있는 회원의 목록을 가져와서 화면에 출력
public class MemberList extends HttpServlet{
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user="servlet";
	String pwd="1234";
	
	// 클라이언트인 브라우저가 목록을 달라고 요청할 것이기 때문에, doXXX 형 메서드 중 doGet()을 재정의 하자.
	// 클라이언트는 목록을 원한다.
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		response.setContentType("text/html;charset=utf-8");	// 이렇게 세미콜론으로 연결하면 아래에 따로 Encoding utf-8 안 해도 된다.
		PrintWriter out = response.getWriter();
		
		Connection connection = null;  // 접속 후 그 정보를 가진 객체. 따라서 이 객체가 null인 경우 접속은 실패.
		PreparedStatement pstmt = null;	// 쿼리문 수행 객체, 오직 Connection 객체로부터 인스턴스 얻음
															// 쿼리문은 접속을 전제로 하기 때문
		ResultSet rs = null;	// select 문의 결과인 표를 가진 객체
		
		
		// 드라이버 로드
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");	// 드라이버 로드
			out.print(" 드라이버 로드 성공<br>");
			
			// 오라클에 접속
			connection = DriverManager.getConnection(url, user, pwd);
			if (connection==null) {
				out.print("접속 실패<br>");
			} else {
				out.print("접속 성공<br>");
				String sql = "select * from member order by  member_id asc";	// 오름차순
				pstmt = connection.prepareStatement(sql);	// 쿼리 수행객체 생성.
				
				// DML 인 경우, executeUpdate() 였지만, select문 인 경우, 원격지 서버의 레코드(표)를 
				// 네트워크로 가져와야 하므로, 그 표 결과를 그대로 반영할 객체가 필요하다.
				// 이러한 객체를 가리켜 ResultSet 이라 한다.
				rs = pstmt.executeQuery();
				// rs를 그냥 표 자체로 생각해도 무방하지만, rs 내에 존재하는 레코드들을 접근하기 위해서는
				// 레코드를 가리키는 포인터 역할을 해주는 커서를 제어해야 한다. 이 커서는 rs가 생성되면,
				// 생성 즉시에는 어떠한 레코드도 가리키지 않은 상태이므로, 개발자가 첫 번째 레코드로 접근하려면 포인터를 한 칸 내려야 한다.
				
				StringBuffer tag = new StringBuffer();
				
				tag.append("<table width=\"100%\"border=\"1px\">");
				tag.append("<thead>");
				tag.append("<tr>");
				tag.append("<th>id</th>");
				tag.append("<th>pwd</th>");
				tag.append("<th>name</th>");
				tag.append("<th>regdate</th>");
				tag.append("<th>email</th>");
				tag.append("</tr>");
				tag.append("</thead>");
				tag.append("<tbody>");
				while(rs.next()) {
					// System.out.println(rs.getString("id") + "," + rs.getString("name")+ "," + rs.getString("email")); // 현재 커서가 위치한 row 한 줄 내에서 컬럼명이 id 인 컬럼의 값을 반환
				    tag.append("<tr>");
				    tag.append("<td>" + rs.getString("id") + "</td>");     
				    tag.append("<td>" + rs.getString("pwd") + "</td>");  
				    tag.append("<td>" + rs.getString("name") + "</td>"); 
				    tag.append("<td>" + rs.getString("regdate") + "</td>"); 
				    tag.append("<td>" + rs.getString("email") + "</td>"); 
				    tag.append("</tr>");
				}
				tag.append("</tbody>");
				tag.append("</table>");
				
				out.print(tag);
				
				out.print("<a href='/member/join.html'>가입하기</a>");
			}
		} catch (ClassNotFoundException e) {
			out.print(" 드라이버 로드 실패<br>");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
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
}

