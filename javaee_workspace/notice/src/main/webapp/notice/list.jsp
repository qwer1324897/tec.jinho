<%@page import="java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>

<%! // ! 붙이면 선언부이다. 선언부 - jsp가 서블릿으로 변환되어질 때, 멤버변수와 멤버메서드가 정의되는 영역
	String url = "jdbc:mysql://localhost:3306/java";
	String user = "servlet";
	String password = "1234";

	Connection connection; // 접속 정보를 가진 객체
	PreparedStatement pstmt; // 쿼리 수행 객체
	ResultSet rs;		// select 수행 후, 표를 담아 제어할 수 있는 객체
%>
<%
// jsp는 서블릿이므로, 이 영역(스크립틀릿)에서 개발자가 코드를 작성하면, 이 jsp가 Tomcat에 의해 서블릿으로 변환될 때
// 생명주기(init, service, destroy) 중 service() 메서드 영역에 코드를 작성한 것으로 처리
// 따라서 클라이언트의 요청을 처리하는 메서드인 service() 메서드에서 mysql의 데이터를 가져와 화면에 출력!!
// 주의) 서블릿으로도 가능하지만, 수많은 코드 라인마다 out.print() 출력해야 하므로, 디자인 작업 시 효율성이 떨어진다.

Class.forName("com.mysql.cj.jdbc.Driver"); // mysql 드라이버

connection = DriverManager.getConnection(url, user, password); // 접속하기

String sql = "select * from notice";

// TYPE_SCROLL_INSENSIVE : 스크롤이 가능한 옵션
// CONCUR_READ_ONLY : 오직 읽기 전용으로만
pstmt = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);	// 쿼리수행 객체 생성

rs = pstmt.executeQuery();	// DML이 아닌 select 이기 때문에, 메서드 executeQuery()를 써야한다.


%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
body {
	background-image:
		url('https://images.unsplash.com/photo-1475274047050-1d0c0975c63e?fm=jpg&q=60&w=3000');
	background-size: cover;
	background-attachment: fixed;
	margin: 0;
	height: 100vh;
	font-family: 'Malgun Gothic', sans-serif;
	color: white;
	display: flex;
	flex-direction: column;
	align-items: center;
	padding-top: 50px;
}

h2 {
	text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.7);
	margin-bottom: 20px;
}

table {
	width: 600px; /* 테이블 너비 지정 */
	border-collapse: collapse;
	border-radius: 10px;
	box-shadow: 0 0 20px rgba(0, 0, 0, 0.5);
	background: linear-gradient(rgba(0, 0, 0, 0.4), rgba(0, 0, 0, 0.4)),
		url('https://i.postimg.cc/D0zJ4XPB/space-Image.png');
	background-size: cover;
	background-position: center;
}

th {
	padding: 15px;
	text-align: center;
	background-color: rgba(86, 102, 243, 0.8);
	color: white;
	font-weight: bold;
	font-size: 1.1em;
	border-top-left-radius: 10px;
	border-top-right-radius: 10px;
}

td {
	padding: 15px;
	text-align: center;
	border-bottom: 1px solid rgba(255, 255, 255, 0.2);
}

tr:hover {
	background-color: rgba(255, 255, 255, 0.1);
}
a{
	font-weight: bold;
	text-decoration:none;
	color: white;
}
</style>
</head>
<body>
	<table>
		<tr>
			<th>제목</th>
			<th>작성자</th>
			<th>등록일</th>
			<th>조회수</th>
		</tr>
		<%
			rs.last();	// 커서를 ResultSet의 제일 마지막 행으로 이동
			out.print("현재 테이블의 총 레코드 수: " + rs.getRow());
			
			// rs의 기본 속성은 RsultSet.TYPE_FORWARD_ONLY 로 되어 있음
			// TYPE_FORWARD_ONLY 상수로 지정되면, 커서가 오직 전방향으로 한 칸씩만 이동 가능하다.
			// PrepareStatement 생성 시 상수를 지정해야 한다
			
			rs.beforeFirst();  // 마지막 행으로 이동한 rs의 커서를 다시 원상복귀시킨다.
			
		%>
		<%
		while(rs.next()) {	 // 메서드가 true를 반환하는 동안만 레코드 수만큼 next()
		%>
		<tr>
			<td><a href="/notice/detail.jsp?notice_id=<%=rs.getInt("notice_id")%>"><%=rs.getString("title") %></a></td>
			<!-- 맨 앞에 = 을 쓰고 세미콜론을 없애면 out.print와 같다 -->
			<td><%=rs.getString("writer") %></td>
			<td><%=rs.getString("regdate") %></td>
			<td><%=rs.getInt("hit") %></td>
		</tr>
		<%}%>
		<tr>
			<td colspan="4">
				<button onClick="location.href='/notice/regist.jsp'">글 등록</button>
			</td>
		</tr>
	</table>
</body>
</html>
<%
	rs.close();
	pstmt.close();
	connection.close();
%>


