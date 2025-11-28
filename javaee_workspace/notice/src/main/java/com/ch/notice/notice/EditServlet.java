package com.ch.notice.notice;

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

// 클라이언트의 수정 요청을 처리하는 서블릿
// 수정 내용폼의 데이터가 규모가 크기 때문에 Get 이 아닌 Post 요청이 들어올 것이다.
public class EditServlet extends HttpServlet{

	Connection connection; // 접속 정보를 가진 객체
	PreparedStatement pstmt; // 쿼리 수행 객체
	// ResultSet 은 select 만 필요
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html;charset=utf-8");	// utf-8 한 줄 더 response 할 필요 없이 charset으로 이어쓸 수 있다.
		request.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();	 // tomcat이 html 작성 시 사용할 내용을 담을 문자기반 출력스트림
		
		// 클라이언트가 전송한 파라미터들을 이용하여 쿼리문 수행
		// DML 중 수정 SQL - UPDATE NOTICE set title=넘겨받은파라미터, writer=파라미터값, content=파라미터값
		//							  WHERE notice_id=파라미터값
		
		String title = request.getParameter("title");
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");
		String notice_id = request.getParameter("notice_id");
		
//		out.println("title="+title);
//		out.println("writer=" + writer);
//		out.println("content=" + content);
//		out.println("notice_id=" + notice_id);
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 접속
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java", "servlet", "1234");
			if (connection==null) {
				out.print("접속 실패");
			} else {
				// out.print("접속 성공");
				// 접속이 성공되었으므로, 쿼리를 실행할 수 있다. DML(insert, update, delete)
				String sql = "update notice set title=?, writer=?, content=? where notice_id=?";
				// 바인드 변수 개념은 요청이 있을 경우 설명
				pstmt = connection.prepareStatement(sql);	
				
				pstmt.setString(1, title);    
				pstmt.setString(2, writer);  
				pstmt.setString(3, content);
				pstmt.setInt(4, Integer.parseInt(notice_id));
				
				// 실행
				int result = pstmt.executeUpdate();	// DML 수행 후, 영향을 받은 레코드 수
				
				out.print("<script>");
				if (result > 0) {
					out.print("alert('게시글 수정 성공: " + result + "건 수정됨');");
					
					// 주의) detail.jsp는 반드시 notice_id 값을 필요로 하므로, 링크 사용시 /notice/detail.jsp 만 적으면 에러가 난다.
					// 따라서 사용자가 방금 보았던 상세 글로 다시 이동하게 하려면
					out.print("location.href='/notice/detail.jsp?notice_id="+ notice_id +"' ");	// 상세글로 이동
				} else {
					out.print("alert('게시글 수정 실패');");
					out.print("history.back();");	 // 웹브라우저의 뒤로가기 버튼과 동일한 효과.
				}
				out.print("</script>");
				
			}
		} catch (ClassNotFoundException e) {
			out.print("드라이버 로드 실패");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
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
