<%@page import="com.ch.model1.dto.Board"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="com.ch.model1.repository.BoardDAO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%!
	// 선언부 : jsp가 서블릿으로 변경될 때 멤버영역
	BoardDAO boardDAO = new BoardDAO();
	
%>
<%
	// 이 영역은 jsp 파일이 서블릿으로 변환될 때 service() 영역임과 동시에 스크립틀릿이기 때문에
	// 얼마든지 DB 연동 로직을 작성할 수 있다.
	// 하지만 그렇게 스파게티 코드로 개발하는 사람은 없다.
	List<Board> list = boardDAO.selectAll();	// 모든 레코드 가져오기
	out.print("등록된 게시물 수: " + list.size());
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
			// rs 에 들어있는 레코드들을 한 칸씩 이동하면서 꺼내어 출력하자.
			// rs.next()가 true 인 동안(레코드가 존재하는 만큼)
			// java List 는 배열이므로 for문을 사용 
			for(int i = 0; i<list.size(); i++) {
				Board board = list.get(i);
		%>
		
		<tr>
			<td><%=board.getBoard_id()%></td>
			<td><%=board.getTitle()%></td>
			<td><%=board.getWriter() %></td>
			<td><%=board.getRegdate() %></td>
			<td><%=board.getHit() %></td>
		</tr>
		<% 
			} 
		%>
	</table>

</body>
</html>
