package com.ch.mvcframework.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.mvframework.food.model.FoodManager;

// 음식에 대한 판단 요청을 처리하는 컨트롤러
// MVC - 개발 이론, 방법론
// Model2 - MVC 이론에 맞춰 Java EE 기술로 구현한 모델
// 따라서 M은 java 순수 클래스, V는 jsp, html 
// C는 1. 웹서버에서 실행될 수 있어야 하고, 2. 클라이언트의 요청을 받을 수 있어야 한다. 즉, 서블릿 말곤 없다.

// 모델2이 컨트롤러 요건(위 1, 2번)에 맞는 서블릿으로 진행.

public class FoodController implements Controller{

	FoodManager foodManager = new FoodManager();
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 클라이언트의 요청 파라미터 받기
		request.setCharacterEncoding("utf-8");
		// response.setContentType("utf-8");  의 경우, 더 이상 디자인을 담당하지 않기에 필요 없다.
		
		String food = request.getParameter("food");
		
		String msg = foodManager.getAdvice(food);		// 모델 객체에 일 시키기
		request.setAttribute("food", food);
		request.setAttribute("msg", msg);		// 요청에 대한 응답이 완료되기 전까지는 서버에서 살아있음.
		
		
		RequestDispatcher dis = request.getRequestDispatcher("/food/result.jsp");	// 포워딩 하고 싶은 url
		dis.forward(request, response);		// 포워딩 발생
		
		
		
	}

	@Override
	public String getViewName() {
		return null;
	}

	@Override
	public boolean isForward() {
		return false;
	}
}