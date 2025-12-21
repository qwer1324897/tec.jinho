package com.ch.mvcframework.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.mvcframework.dto.Board;
import com.ch.mvcframework.repository.BoardDAO;

// 게시물 1건 요청을 처리하는 하위 컨트롤러

public class DetailController implements Controller{
	BoardDAO boardDAO = new BoardDAO();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 3단계: 알맞는 로직 객체에 일 시키기
		String board_id = request.getParameter("board_id");
		Board board = boardDAO.select(Integer.parseInt(board_id));
		
		System.out.println("게시물이 들어있는 DTO: " + board);
		
		// board를 결과페이지인 View 까지 살려서 가져가려면 request 에 저장해서 포워딩 해야한다. (view로 가져갈 값이 있다 > 포워딩)
		request.setAttribute("board", board);
		
		// request 는 죽으면 안 되므로 응답을 해선 안되고 포워딩을 해야한다.
		
	}

	@Override
	public String getViewName() {
		
		return "/board/detail/result";	// 하드코딩하지 말고 board/detail.jsp 매핑한 key 값으로 작성.
		
	}

	@Override
	public boolean isForward() {
		
		return true;
	}

}
