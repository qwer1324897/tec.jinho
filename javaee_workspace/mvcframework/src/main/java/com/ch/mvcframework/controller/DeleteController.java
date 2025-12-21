package com.ch.mvcframework.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.mvcframework.repository.BoardDAO;

// 삭제 요청을 처리하는 하위 컨트롤러
// 하위 컨트롤러 이므로 컨트롤러 업무 단계 중 3단계(일 시키기),4단계(결과가 있다면 저장) 수행
// 이 때, 4단계는 경우에 따라 반드시 수행을 하는 것은 아니다.
// 주로 select 문 수행 시(View로 가져가야 하므로) 4단계를 수행하는데, 
// DML 처럼 View로 가져갈 것이 없을 경우, 포워딩하지 않고(isForward=false) 재접속 요청. 예) 삭제 처리시에는 클라이언트가 그냥 list.do로 재접속하여 갱신된 게시물을 보면 됨. 뭘 저장하고 포워딩하고 할 필요가 없음

public class DeleteController implements Controller{
	
	BoardDAO boardDAO = new BoardDAO();

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 3단계: 일 시키기
		String board_id = request.getParameter("board_id");
		
		int result = boardDAO.delete(Integer.parseInt(board_id));
		
		// 4단계는 delete 라서 없음. 포워딩하지 않고 바로 접속끊어서 do 로 재접속 유도
	}

	@Override
	public String getViewName() {
		return "/board/delete/result";
	}

	@Override
	public boolean isForward() {
		return false;
	}

	
}
