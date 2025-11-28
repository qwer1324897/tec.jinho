<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<%!Connection connection;
	PreparedStatement pstmt;
	ResultSet rs;

	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "servlet";
	String pwd = "1234";%>
<%
Class.forName("oracle.jdbc.driver.OracleDriver");
connection = DriverManager.getConnection(url, user, pwd);

String sql = "select * from gallery order by gallery_id desc";
pstmt = connection.prepareStatement(sql);
rs = pstmt.executeQuery();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Gallery List</title>
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
	width: 600px;
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
	position: relative; /* 미리보기 이미지 기준점 */
	padding: 0; /* 링크가 <td> 전체를 덮도록 내부 패딩 제거 */
	text-align: center;
	border-bottom: 1px solid rgba(255, 255, 255, 0.2);
}
tr:hover {
	background-color: rgba(255, 255, 255, 0.1);
}
.preview-box {
	position: relative;
}
.cell-link {
	color: inherit;
	text-decoration: none;
	display: block;
	width: 100%;
	height: 100%;
	padding: 15px;
	box-sizing: border-box;
}
.cell-link:hover {
	color: #FFD700;
}
.preview-img {
	display: none;
	position: absolute;
	top: -50px;
	left: 120%;
	width: 200px;
	height: auto;
	border: 2px solid white;
	border-radius: 8px;
	box-shadow: 0 5px 15px rgba(0, 0, 0, 0.5);
	z-index: 100;
	background-color: black;
}
/* 마우스 올렸을 때 이미지 보이기 */
.preview-box:hover .preview-img {
	display: block;
}
</style>
</head>
<body>
	<h2>서버 갤러리 목록</h2>
	<table>
		<thead>
			<tr>
				<th width="15%">파일 ID</th>
				<th width="50%">제목</th>
				<th width="35%">파일명</th>
			</tr>
		</thead>
		<tbody>
			<%
			if (rs != null) {
				while (rs.next()) {
					String filename = rs.getString("filename");
					String title = rs.getString("title");
			%>
			<tr>
				<td><%=rs.getInt("gallery_id")%></td>

				<td class="preview-box"><a href="/pracView?name=<%=filename%>"
					target="_blank" class="cell-link"> <%=title%> <img
						src="/pracView?name=<%=filename%>" class="preview-img">
				</a></td>

				<td class="preview-box"><a href="/pracView?name=<%=filename%>"
					target="_blank" class="cell-link"> <%=filename%> <img
						src="/pracView?name=<%=filename%>" class="preview-img">
				</a></td>
			</tr>
			<%
			}
			} else {
			%>
			<tr>
				<td colspan="3" style="padding: 20px; color: #FF6347;">데이터를
					불러오는 데 실패했습니다. DB 연결을 확인하세요.</td>
			</tr>
			<%
			}
			%>
		</tbody>
	</table>
</body>
</html>

<%
if (rs != null)
	rs.close();
if (pstmt != null)
	pstmt.close();
if (connection != null)
	connection.close();
%>