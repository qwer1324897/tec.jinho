<%@page import="com.ch.model1.dto.News"%>
<%@page import="com.ch.model1.util.PagingUtil"%>
<%@page import="java.util.List"%>
<%@page import="com.ch.model1.repository.NewsDAO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%!
	// 선언부 : jsp가 서블릿으로 변경될 때 멤버영역
	NewsDAO newsDAO = new NewsDAO();
	PagingUtil pgUtil = new PagingUtil();	// 따로 만든 페이징 처리 클래스
%>
<%
	List<News> list = newsDAO.selectAll();
	pgUtil.init(list, request);	// 이 시점부터 페이징 처리 클래스가 알아서 계산
	
	out.print("총 레코드 수는 "+ pgUtil.getTotalRecord());
%>
<!DOCTYPE html>
<html>
<head>
<meta charset=utf-8>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
table {
  border-collapse: collapse;
  border-spacing: 0;
  width: 100%;
  border: 1px solid #ddd;
}

th, td {
  text-align: left;
  padding: 16px;
}

tr:nth-child(even) {
  background-color: #f2f2f2;
}
</style>
</head>
<body>
	<table>
		<tr>
			<th>No</th>
			<th>제목</th>
			<th>작성자</th>
			<th>등록일</th>
			<th>조회수</th>
		</tr>
		<%
			int curPos = pgUtil.getCurPos();	// 페이지 당 시작 리스트 내의 인덱스
			int num = pgUtil.getNum();
		%>
		<% for (int i = 1; i<=pgUtil.getPageSize(); i++) { %>
		<% if(num<1) break; 	// 게시물 번호가 1보다 작으면 반복문 돌지 않게.%>  
		<%
			News news = list.get(curPos++);
		%>
		<tr>
			<td><%=num--%></td>
			<td>
				<a href="/news/detail.jsp?news_id=<%=news.getNews_id() %>"><%=news.getTitle()%></a>
				<%if (news.getCnt()>0) { %>
					[<%=news.getCnt() %>]
					<%} %>
			</td>
			<td><%=news.getWriter() %></td>
			<td><%=news.getRegdate()%></td>
			<td><%=news.getHit() %></td>
		</tr>
		<%} %>
		
		<tr>
			<td>
		        <button onclick="location.href='write.jsp'">글 등록</button>
			</td>
			<td colspan="4">
			</td>
		</tr>
	</table>
</body>
</html>
