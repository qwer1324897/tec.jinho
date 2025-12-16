package com.ch.mvcframework.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.mvcframework.dto.Board;
import com.ch.mvcframework.repository.BoardDAO;

// 수정 요청을 처리하는 하위 컨트롤러
public class UpdateController implements Controller{

	BoardDAO boardDAO = new BoardDAO();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String board_id = request.getParameter("board_id");
		String title = request.getParameter("title");
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");
		
		System.out.println("board_id = " + board_id);
		System.out.println("title = " + title);
		System.out.println("writer = " + writer);
		System.out.println("content = " + content);
		
		// 3단계: 모델에 일 시키기
		Board board = new Board();
		board.setBoard_id(Integer.parseInt(board_id));
		board.setTitle(title);
		board.setWriter(writer);
		board.setContent(content);
		
		boardDAO.update(board);
		// 이 시점에서 실제 DB에서 수정은 끝났음. 그러나 클라이언트 입장에선 아직 수정된 내용을 보지 못했다.
		// 따라서 컨트롤러 중 상세보기 요청을 처리하는 DetailController 에게 재접속을 하게 만들어야 한다.
		// 이 자리에 select로 포워딩하는 식으로 코드를 짜도 전혀 문제없이 잘 돌아간다.
		// 하지만 MVC 원칙에 따라 이 UpdateController 는 update 만을 담당해야 한다. 여기서 select로 가져와서 저장하고 포워딩 하는 건 DetailController 로 가서 해야 한다.
		// 따라서 여기서 포워딩해서 코드를 짜는게 아니라(가능하지만 MVC 원칙에 따라), 여기서는 포워딩하지말고 DetailController로 이동시켜서 나머지 로직을 처리해야 하지만...
		// 그렇게 하면 너무 복잡해지므로 일단은 여기서 포워딩해서 진행.
	}

	@Override
	public String getViewName() {
		return "/board/update/result";
	}

	@Override
	public boolean isForward() {
		return true;
	}

}
