package com.ch.model1.board;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.model1.dto.Board;
import com.ch.model1.repository.BoardDAO;

// 수정 요청을 처리하는 서블릿
public class EditServlet extends HttpServlet{
	
	BoardDAO boardDAO = new BoardDAO();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String board_id = request.getParameter("board_id");
		String title = request.getParameter("title");
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");
		
//		System.out.println(board_id);
//		System.out.println(title);
//		System.out.println(writer);
//		System.out.println(content);
		
		Board board = new Board();
		board.setBoard_id(Integer.parseInt(board_id));
		board.setTitle(title);
		board.setWriter(writer);
		board.setContent(content);
		
		int result = boardDAO.update(board);
		
		PrintWriter out = response.getWriter();
		
		out.print("<script>");
		if (result < 1) {
			out.print("alert('수정 실패');");
			out.print("history.back();");
		} else {
			out.print("alert('수정 성공');");
			out.print("location.href='/board/list.jsp?board_id="+board_id+"';");
		}
		out.print("</script>");
		
	}
}
