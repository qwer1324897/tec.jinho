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

// 글 한 건 삭제 요청을 처리하는 서블릿
// delete from notice where notice_id=넘겨받은 파라미터값;
// pk값은 내용이 길지 않으며, 보안상 중요하지도 않기 때문에
public class DeleteServlet extends HttpServlet{
	
	String url = "jdbc:mysql://localhost:3306/java";
	String user = "servlet";
	String password = "1234";

	Connection connection; // 접속 정보를 가진 객체
	PreparedStatement pstmt; // 쿼리 수행 객체
	// ResultSet 은 select 만 필요
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 클라이언트가 요청을 시도하면서 함께 지참해 온, notice_id 파라미터값을 받자
		String notice_id = request.getParameter("notice_id");
		System.out.println("넘겨받은 notice_id: "+ notice_id);
		
		// jsp 에서 지시문(맨 위) 영역에 해당하는 코드와 동일한 코드.
		response.setContentType("text/html");	 // MIME 타입은 브라우저가 이해하는 형식으로 작성.(정해져 있다)
																	 // 이미지의 경우, image/jpg json의 경우 application/json  
		response.setCharacterEncoding("utf-8");
		request.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			// 접속
			connection = DriverManager.getConnection(url, user, password);
			if (connection==null) {
				out.print("접속 실패");
			} else {
				// out.print("접속 성공");
				// 접속이 성공되었으므로, 쿼리를 실행할 수 있다. DML(insert, update, delete)
				String sql = "delete from notice where notice_id=" +notice_id;
				// 바인드 변수 개념은 요청이 있을 경우 설명
				pstmt = connection.prepareStatement(sql);	
				// 실행
				int result = pstmt.executeUpdate();	// DML 수행 후, 영향을 받은 레코드 수
				
				out.print("<script>");
				if (result > 0) {
					out.print("alert('게시글 삭제 성공: " + result + "건 삭제됨');");
					out.print("location.href='/notice/list.jsp';");	// 목록으로 이동
				} else {
					out.print("alert('게시글 삭제 실패 (해당 ID 없음)');");
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
