<%@ page contentType="text/html; charset=UTF-8"%>
<%
// 하나의 페이지에 많은 양의 데이터가 출력되는 방식은 스크롤이 발생하므로 한 화면에 보여질 레코드 수의 제한이 생기고,
	// 나머지 데이터에 대해선 여러 페이지 링크를 지원해주려면, 총 게시물 수에 대해 산수계산이 요구된다.
	
	// 기본 전제 조건 - 총 레코드 수가 있어야 한다.
	int totalRecord = 26;	// 임의의 총 레코드 수
	int pageSize = 10;	// 임의로 정한 페이지당 보여질 레코드 수
	int blockSize = 10;	// 블락당 보여질 페이지 수(10개씩 끊겨서 보여진다)
	int totalPage;
	if(totalRecord%pageSize!=0) {
		totalPage = totalRecord / pageSize + 1;
	} else {
		totalPage = totalRecord / pageSize;
	}
	int currentPage = 1;
	if(request.getParameter("currentPage")!=null){
		currentPage=Integer.parseInt(request.getParameter("currentPage"));	// 현재 유저가 보고 있는 페이지
	}
	int firstPage = currentPage - (currentPage-1)%blockSize;   // 블럭당 반복문의 시작 값
	int lastPage = firstPage+(blockSize-1);   // 블럭당 반복문의 끝 값
	int num = totalRecord - ((currentPage-1)*pageSize);	
	// 페이지 당 시작 번호 예) 1 page 일 때는 26부터 차감, 2 page 일 때는 16부터 차감, 3 page 일 때는 6부터 차감.
	
%>
<%="totalRecord: "+totalRecord+"<br>" %>
<%="pageSize: "+pageSize+"<br>" %>
<%="totalPage: "+totalPage+"<br>" %>
<%="현재 당신이 보고 있는 currengPage: " + currentPage+ "<br>" %>   
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
a{text-decoration:none;}
.numStyle {
	font-size: 30px;
	color: red;
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
		<%for(int i = 1; i<=pageSize; i++) { %>
		<%if(num<1) break; %>
		<tr>
			<td><%=num--%></td>
			<td>오늘의 점심은 뭔가요</td>
			<td>오늘 제가 준비한 점심은</td>
			<td>프랑스산 압력밥솥에 1시간동안 준비한</td>
			<td>벼의 알곡입니다.</td>
		</tr>
		<%} %>
		<tr>
			<td colspan="5">
				<a href="/paging/list.jsp?currentPage=<%=firstPage-1%>">◀</a>
				<%for(int i=firstPage; i<=lastPage; i++) {%>
				<%if(i>totalPage) break;%>
				<a <%if(currentPage==i){ %>class="numStyle" <%} %> href="/paging/list.jsp?currentPage=<%=i%>">[<%=i%>]</a>
				<%} %>
				<a href="/paging/list.jsp?currentPage=<%=lastPage+1%>">▶</a>
			</td>
		</tr>
	</table>
	        <button onclick="location.href='write.jsp'">글 등록</button>
</body>
</html>
