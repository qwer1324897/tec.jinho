package com.ch.gallery.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//DB에 등록된 갤러리 목록을 출력하기 위한 서블릿
public class ListServlet extends HttpServlet{

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		// 출력 스트림에 클라이언트에게 보여줄 태그를 넣어둬야 한다.
		// 서블릿은 서버에서 실행되는 javaEE 기반의 클래스로서, 서블릿 없이 javaEE 개발 자체가 불가능함.
		// 하지만 서블릿은 디자인 페이지 작성 시 너무 비효율적이다
		// 즉, Servlet 은 동일한 목적의 기술인 php, asp 등에 비해 경쟁력이 떨어진다.
		// 그래서 Servlet 을 보완한 서버 측 스크립트 기술로 JSP를 지원한다.
	}

}
