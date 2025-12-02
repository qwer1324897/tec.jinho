package com.ch.model1.board;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.model1.dto.Board;
import com.ch.model1.repository.BoardDAO;

public class DeleteServlet extends HttpServlet{

	BoardDAO boardDAO = new BoardDAO();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String board_id = request.getParameter("board_id");
		
		Board board = new Board();
		board.setBoard_id(Integer.parseInt(board_id));
		
		int result = boardDAO.delete(board);
		
		PrintWriter out = response.getWriter();
		
		out.print("<script>");
		if (result > 0) {
			out.print("alert('게시글 삭제 성공: " + result + "건 삭제됨');");
			out.print("location.href='/board/list.jsp';");	// 목록으로 이동
		} else {
			out.print("alert('게시글 삭제 실패 (해당 ID 없음)');");
			out.print("history.back();");	 // 웹브라우저의 뒤로가기 버튼과 동일한 효과.
		}
		out.print("</script>");
		
	}
}