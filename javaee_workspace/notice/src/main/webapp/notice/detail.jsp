<%@ page contentType="text/html; charset=UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%!
String url = "jdbc:mysql://localhost:3306/java";
String user = "servlet";
String password = "1234";

Connection connection; // 접속 정보를 가진 객체
PreparedStatement pstmt; // 쿼리 수행 객체
ResultSet rs;		// select 수행 후, 표를 담아 제어할 수 있는 객체
%>
<%
	// 위의 페이지 지시 영역은, 현재 jsp가 Tomcat 에 의해 서블릿으로 코딩되어질 때
	// text/html 부분은 response.setContentType();
	// charset = UTF-8 response.setCharacterEncoding("UTF-8"); 으로 코딩되어진다.
	
	// select * from notice where notice_id=2  쿼리를 수행하여 레코드를 화면에 보여주기
	// Http 통신에서 주고 받는 파라미터는 모두 문자열로 인식된다.
	String notice_id = request.getParameter("notice_id");		// request : 서블릿의 service(요청객체, 응답객체) 중 HttpServletRequest 인터페이스를 가리키는 내장 객체
																							// 개발자가 정한 변수명이 아니라 jsp 문법에서 정해진 이름이다.
	out.print("select*from notice where notice_id=" + notice_id);
	
	Class.forName("com.mysql.cj.jdbc.Driver");
	connection = DriverManager.getConnection(url, user, password);
	
	String sql = "select * from notice where notice_id=" + notice_id;
	pstmt = connection.prepareStatement(sql);
	
	rs = pstmt.executeQuery();
	
	rs.next();	// 커서는 언제나 before first 위치에 있기 때문에, 한 칸 이동해야 한다.
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
body {
	font-family: Arial, Helvetica, sans-serif;
}
* {
	box-sizing: border-box;
}
input[type=text], select, textarea {
	width: 100%;
	padding: 12px;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
	margin-top: 6px;
	margin-bottom: 16px;
	resize: vertical;
}
input[type=button] {
	background-color: Red;
	color: white;
	padding: 12px 20px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}
input[type=button]:hover {
	background-color: #45a049;
}
.container {
	border-radius: 5px;
	background-color: #f2f2f2;
	padding: 20px;
}
</style>
<script>
	function del() {
		// let response = confirm("삭제하시겠습니까?");
		// console.log("유저의 response는", response);
		
		if(confirm("삭제하시겠습니까?")) {
			location.href='/notice/delete?notice_id=<%=rs.getInt("notice_id") %>';
		}
	}
	function edit() {
		if (confirm("수정하시겠습니까?")) {
			// 작성된 폼 양식을 서버로 전송한다
			let form1 = document.querySelector(".form1");
			form1.action = "/notice/edit";	  // 서버의 url
			form1.method = "post";
			form1.submit();
		}
	}
</script>
</head>
<body>
	<div class="container">
		<form class="form1">
			<input type="hidden" name="notice_id" value="<%=rs.getString("notice_id")%>">
			<!-- 파라미터 중, 클라이언트에게 노출될 필요 없는 요소들은 hidden 으로 타입을 지정해서 개발자만 다룬다. --> 
			
			<input type="text" name="title" value="<%=rs.getString("title")%>">
            <input type="text" id="ㅣname" name="writer" value="<%=rs.getString("writer")%>">
			<textarea id="subject" name="content" placeholder="내용 입력" style="height: 200px"><%=rs.getString("content")%></textarea>
            
            <!-- js 에서 링크를 표현한 내장객체 - location 사용 -->
            <input type="button" value="수정" onclick = "edit()">
            <input type="button" value="삭제" onclick = "del()">
            <input type="button" value="목록" onclick = "location.href='/notice/list.jsp'">
		</form>
	</div>
</body>
</html>
<%
	rs.close();
	pstmt.close();
	connection.close();
%>
