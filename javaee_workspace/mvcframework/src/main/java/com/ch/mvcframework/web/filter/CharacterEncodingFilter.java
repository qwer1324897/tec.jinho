package com.ch.mvcframework.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

// 이 필터의 목적은 DispatcherServlet 이 모든 요청을 처리하기 전, 공통적으로 처리할 업무 중 파라미터에 대한 인코딩 처리를 미리하기 위함.
// 여기서 처리해놓으면 모든 요청이 이 필터를 먼저 거쳐가기 때문에 하위 컨트롤러에서 일일이 인코딩 처리를 할 필요가 없기 때문.
public class CharacterEncodingFilter implements Filter{
	String encoding;
	public void init(FilterConfig config) {
		encoding = config.getInitParameter("encoding");
		System.out.println("내가 원하는 인코딩은 " + encoding);
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(encoding);
		
		// 필터에 기능(인코딩 등)을 넣고 마지막에 필터체인을 이어줘야 한다. 안 하면 그냥 필터 기능 수행하고 멈춰버림.
		chain.doFilter(request, response); 	 // 필터 기능 끝났으니 이제 진행시키쇼 라는 뜻.
	}
}
