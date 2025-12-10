<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		out.print(application.getAttribute("born"));
		out.print(session.getAttribute("id"));
		out.print(request.getAttribute("hobby"));
	
		// request의 자료형 - HttpServletRequest
		// session의 자료형 - HttpSession
		// application의 자료형 - ServletContext 
		
		String path = application.getRealPath("WEB-INF/servlet-mapping.txt");
		// 현재 웹애플리케이션의 자원의 실제 OS 상의 경로를 반환
		// 현재 OS가 리눅스면 리눅스 경로, 맥이면 맥의 경로, 윈도우면 윈도우 경로가 나온다
		out.print(path);
	%>

</body>
</html>