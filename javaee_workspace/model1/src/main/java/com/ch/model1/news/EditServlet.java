package com.ch.model1.news;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.model1.dto.News;
import com.ch.model1.repository.NewsDAO;

// 수정 요청을 처리하는 서블릿
public class EditServlet extends HttpServlet{
	
	NewsDAO newsDAO = new NewsDAO();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String news_id = request.getParameter("news_id");
		String title = request.getParameter("title");
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");
		
//		System.out.println(board_id);
//		System.out.println(title);
//		System.out.println(writer);
//		System.out.println(content);
		
		News news = new News();
		news.setNews_id(Integer.parseInt(news_id));
		news.setTitle(title);
		news.setWriter(writer);
		news.setContent(content);
		
		int result = newsDAO.update(news);
		
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer();
		
		sb.append("<script>");
		if (result < 1) {
			sb.append("alert('수정 실패');");
			sb.append("history.back();");
		} else {
			sb.append("alert('수정 성공');");
			sb.append("location.href='/news/list.jsp?news_id="+news_id+"';");
		}
		sb.append("</script>");
		out.print(sb.toString());
		
	}
}
