package com.ch.model1.news;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.model1.dto.News;
import com.ch.model1.repository.NewsDAO;

public class DeleteServlet extends HttpServlet{
	
	NewsDAO newsDAO = new NewsDAO();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		String news_id = request.getParameter("news_id");
		
		News news = new News();
		news.setNews_id(Integer.parseInt(news_id));
		
		int result = newsDAO.delete(news);
		
		PrintWriter out = response.getWriter();
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("<script>");
		if (result > 0) {
			sb.append("alert('게시글 삭제 성공: " + result + "건 삭제됨');");
			sb.append("location.href='/news/list.jsp';");	// 목록으로 이동
		} else {
			sb.append("alert('게시글 삭제 실패 (해당 ID 없음)');");
			sb.append("history.back();");	 // 웹브라우저의 뒤로가기 버튼과 동일한 효과.
		}
		sb.append("</script>");
		out.print(sb.toString());
	}
}