<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="com.ch.model1.dto.Comment"%>
<%@page import="java.util.List"%>
<%@page import="com.ch.model1.repository.CommentDAO"%>
<%@ page contentType="application/json; charset=UTF-8"%>
<%!
	CommentDAO commentDAO = new CommentDAO();
%>
<%
	// 특정 뉴스기사에 딸려있는 코멘트 게시물 모두 가져오기
	// select * from comment where news_id = 20;
	String news_id = request.getParameter("news_id");
	
	// 결과로 반환한 리스트를 클라이언트에게 순수한 데이터 형태로 보내자.(클라이언트는 페이지 새로고침을 원하지 않고 순수 데이터만을 원하는 비동기방식)
	List<Comment> commentList = commentDAO.selectByNewsId(Integer.parseInt(news_id));
	// out.print(commentList);
	// 이렇게 그냥 List 의 레퍼런스 변수 자체를 출력해버리면, 클라이언트가 원하는 파싱되는 JSON 형태가 아니므로, 파싱이 불가능.
	// 때문에 클라이언트가 해석 가능한 형태의 문자열인 JSON으로 전송해줘야 한다.
	// 그렇다고 개발자가 직접 list 안에 있는 DTO들을 꺼내어 json 문자열로 변환하는 작업은 너무 비효율적.
	// 따라서 외부 라이브러리를 적극 활용해야 한다.(스프링은 내장되어 있음)
	// Jackson 라이브러리를 활용하면 객체와 JSON 문자열간 변환을 자동으로 처리해준다.(자바스크립트에서도 비슷한 JSON 내장 객체가 있다.)
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	// 네트워크 상에서는 데이터를 전송하려면 반드시 문자열로 변환해야 하므로, 객체를 문자열로 바꿔야 한다
	String result = objectMapper.writeValueAsString(commentList);
	out.print(result);
%>