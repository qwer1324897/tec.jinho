package com.ch.gallery.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
// 클라이언트의 업로드를 처리할 서블릿
public class UploadServlet extends HttpServlet{
	
	// 클라이언트의 post 요청을 처리 할 메서드
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();		// 응답 객체가 보유한 스트림 얻기
		
		// 업로드를 처리할 cos 컴포넌트를 사용해보자
		// MultipartRequest 객체는 일반 클래스이므로, 개발자가 new 연산자를 이용하여 인스턴스를 직접 생성할 수 있다.
		// 따라서 이 객체가 지원하는 생성자를 조사해서 사용하면 된다.
		
		// MultipartRequest 는 생성자에서 업로드 처리를 하는 객체이다.
		MultipartRequest multi = new MultipartRequest(req, "C:\\upload");
		out.print("업로드 성공");
	}
}