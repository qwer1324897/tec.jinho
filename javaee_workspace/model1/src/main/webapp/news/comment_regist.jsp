<%@page import="com.ch.model1.dto.News"%>
<%@page import="com.ch.model1.dto.Comment"%>
<%@page import="com.ch.model1.repository.CommentDAO"%>
<%@ page contentType="application/json; charset=UTF-8"%>
<%!
	CommentDAO commentDAO = new CommentDAO();
%>

<%
	// 클라이언트가 비동기로 요청을 시도하므로, 파라미터를 받고, DB에 넣은 후
	// 응답 정보는 Html이 아닌 json으로

	request.setCharacterEncoding("utf-8");
	
	String news_id = request.getParameter("news_id");
	String reader = request.getParameter("reader");
	String msg = request.getParameter("msg");

	System.out.println("news_id " + news_id);
	System.out.println("작성자는 " + reader);
	System.out.println("메세지는 " + msg);
	
	// 파라미터를 하나의 DTO로 모으고
	Comment comment = new Comment();
	News news = new News();
	news.setNews_id(Integer.parseInt(news_id) );
	comment.setNews(news);
	comment.setReader(reader);
	comment.setMsg(msg);
	
	int result = commentDAO.insert(comment);	
	System.out.println("등록결과 " + result);
	
	// 결과 처리
	// 클라이언트측에서 비동기로 요청을 시도할 경우 서버측에서 완전한 html로 응답을 하면 의도와 달리 동기방식을 염두한 응답 정보이므로
	// 서버측에선 순수한 데이터 형태로 응답 정보를 보내야 한다. 이 때 압도적으로 많이 사용되는 데이터 형태는 Json이다. (단순 문자열이라 호완성이 압도적)
	if(result < 1) {
		out.print("{\"resultMsg\":\"등록실패\"}");
	} else {
		out.print("{\"resultMsg\":\"등록성공\"}");
	}
	
%>


