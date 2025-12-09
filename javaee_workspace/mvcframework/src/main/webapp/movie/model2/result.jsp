<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h3>
		<span style = "color:cadetblue">
			<%
				 // jsp 의 내장객체(request.response,session,out 등)
				 String msg =(String)request.getAttribute("msg");
				 String movie = (String)request.getAttribute("movie");
				
			%>
			<h3><%=movie%><br></h3>
			<%=msg%>
		</span>
	</h3>
</body>
</html>