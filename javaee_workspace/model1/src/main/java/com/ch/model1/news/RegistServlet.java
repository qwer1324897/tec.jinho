package com.ch.model1.news;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.model1.dto.News;
import com.ch.model1.repository.NewsDAO;

public class RegistServlet extends HttpServlet{

	NewsDAO dao = new NewsDAO();
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		// 클라이언트가 동기방식으로 전송한 파라미터를 받아서 DAO를 이용하여 DB에 넣자.
		
		String title = request.getParameter("title");
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");
		
		News news = new News();
		news.setTitle(title);
		news.setWriter(writer);
		news.setContent(content);
		
		
		int result = dao.insert(news);
		
		// 클라이언트가 동기 방식으로 요청했기 때문에 서버는 화면전환을 염두해 두고,
		// 순수 데이터가 아니라 페이지 전환 처리가 요구됨.
		// 글 등록 후, 클라이언트의 브라우저로 하여금 다시 목록페이지를 재요청하도록 해야 한다.
		PrintWriter out = response.getWriter();
		StringBuffer sb = new StringBuffer();		// 버퍼가 더 빠르고 좋음.
		
		sb.append("<script>");
		if (result < 1) {
			sb.append("alert('등록실패');");
			sb.append("history.back();");
		} else {
			sb.append("alert('등록성공');");
			sb.append("location.href='/news/list.jsp';");
		}
		sb.append("</script>");
		
		out.print(sb.toString());
	}
}