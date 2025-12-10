package com.ch.mvcframework.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.mvcframework.dto.Board;
import com.ch.mvcframework.repository.BoardDAO;

// 글쓰기 요청을 처리하는 하위 컨트롤러

/*
 * 1. 요청을 받는다 (DispatcherServlet)
 * 2. 요청을 분석한다(DispatcherServlet)
 * 3. 알맞는 로직 객체에 일 시킨다(하위 컨트롤러)
 * 4. 결과페이지에 가져갈 것이 있을 경우 결과를 저장(메모리 낭비때문에 session이 아닌 request에 저장.)
 * 5. 컨트롤러는 디자인에 관여하면 안되므로, 알맞는 View 페이지를 보여준다.
 */

// 하위 컨트롤러는 3,4 단계를 수행하므로
public class RegistController implements Controller{
	BoardDAO boardDAO = new BoardDAO();
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 3단계 수행: 로직객체에 일 시키기.
		
		// request.setCharacterEncoding("utf-8"); 한글깨짐 방지 코드를 DispatcherController request에 이미 작성했으므로 하위 컨트롤러엔 안 써도 됨.
		
		String title = request.getParameter("title");
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");
		
		Board board = new Board();
		board.setTitle(title);
		board.setWriter(writer);
		board.setContent(content);
		
		int result = boardDAO.insert(board);		// 등록시키기
		// 등록 후 성공 시 게시물 목록을 보여줘야 하는데
		
		// response.sendRedirect("/board/list.jsp");
	}
	
	// DispatcherServlet 이 보여줘야할 View 페이지 정보를 반환
	public String getViewName() {
		return "/board/regist/result";
	}

	@Override
	public boolean isForward() {
		return false;	// 포워딩을 하지 않고 브라우저로 redirect(재접속) 하라는 뜻.
	}

}
