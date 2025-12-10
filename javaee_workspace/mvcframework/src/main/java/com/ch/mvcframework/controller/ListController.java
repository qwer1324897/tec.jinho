package com.ch.mvcframework.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.mvcframework.repository.BoardDAO;

// 
public class ListController implements Controller{
	BoardDAO boardDAO = new BoardDAO();
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 3단계. 알맞는 로직 객체에게 일 시키기
		List list = boardDAO.selectAll();
		request.setAttribute("list", list);
		
	}

	// 현재 컨트롤러에서는 디자인 관련한 응답을 해선 안되며 클라이언트에게 특정 페이지로 재접속하라는 응답 정보 조차 보내면 안 된다.
	// 클라이언트와의 응답 정보에 대한 처리는 전면부에 나선 DispatcherServlet 이 담당해야 하기 때문.
	// 따라서 하위 컨트롤러에서는 DispatcherServlet 이 보여줘야할 View 페이지에 대한 정보만 반환하면 된다.
	// 또한 View 페이지에 대한 정보 반환 시 .jsp 파일은, 파일명이 변경되거나 할 때 영향을 받지 않기 위해 직접 명시해선 안 된다.(하드코딩 금지)
	// 유지보수성을 높이기 위해서 자바 클래스 내의 주소들은 기본적으로 하드코딩 하지 않는다.
	@Override
	public String getViewName() {
		return "/board/list/result";
	}

	// jsp 까지 살려서 가져갈 데이터가 있다면 포워딩 해야 한다.
	@Override
	public boolean isForward() {
		return true;
	}
	
}
