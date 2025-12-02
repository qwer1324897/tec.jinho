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
	
	int totalRecord = list.size();	// 총 게시물 수 대입
	int pageSize = 10;	// 모든 게시글을 한 페이지에 전부 출력하는 게 아니라, 특정 숫자에 맞게 나눠서 출력
	// pageSize를 적용하면 26건의 경우, 나머지 16건은 볼 수 없기 때문에, 나머지 페이지를 보여줘야 할 총 페이지 수를 구한다.
	int totalPage = (int)Math.ceil((float)totalRecord/pageSize);
	int blockSize = 10;	// totalPage 만큼 반복문을 돌리면, 게시글이 많을 경우 페이지 수도 너무 많아지므로 한 번에 출력될 페이지를 제한한다.
	int currentPage = 1;
	if (request.getParameter("currentPage")!=null){
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}
	int firstPage = currentPage - (currentPage-1)%blockSize;
	int lastPage = firstPage + (blockSize - 1);
	int curPos = (currentPage-1)*pageSize; // 페이지당 가져올 List 의 시작 인덱스. 현재 페이지와 비례하여 10(페이지 사이즈)씩 커진다
	int num = totalRecord-curPos;
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
			for(int i = 0; i<pageSize; i++) {
				if(num<1) break;
				// 게시물 번호가 1보다 작아지면, 더 이상 데이터가 없는데 List에서 접근하려고 하니 out of bounds가 뜬다.
				// 따라서 1보다 작아질 때 break; 로 제한.
				Board board = (Board)list.get(curPos++);
		%>
		<tr>
			<td><%=num--%></td>
			<td><a href="/board/detail.jsp?board_id=<%=board.getBoard_id() %>"><%=board.getTitle()%></a></td>
			<td><%=board.getWriter() %></td>
			<td><%=board.getRegdate() %></td>
			<td><%=board.getHit() %></td>
		</tr>
		<%} %>
		<tr>
			<td>
		        <button onclick="location.href='write.jsp'">글 등록</button>
			</td>
			<td colspan="4">
				<!-- <a href="/board/list.jsp?currentPage=<%=firstPage-1%>">◀</a> -->
				<%for(int i=firstPage; i<=lastPage; i++) {%>
				<%if(i>totalPage) break;%>
				<a <%if(currentPage==i){ %>class="numStyle" <%} %> href="/board/list.jsp?currentPage=<%=i%>">[<%=i%>]</a>
				<%} %>
				<!-- <a href="/board/list.jsp?currentPage=<%=lastPage+1%>">▶</a> -->
			</td>
		</tr>
	</table>
</body>
</html>
