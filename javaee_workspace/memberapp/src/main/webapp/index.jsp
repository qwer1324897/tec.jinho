<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import = "com.ch.memberapp.member.Member" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	홈페이지 메인 <br>
	<!-- jsp 에서는 필수적으로 사용되는 JavaEE 기반의 객체들을 미리 메모리에 올려놓고, 이름을 저장해놓았는데,
		   이러한 시스템에 의해 결정되어 내장된 객체들을 JSP 내장(built-in) 객체라 한다.
		   따라서 변수명을 바꾸거나 할 수 없다.
		   지금은 회원 정보를 꺼내오기 위해 HttpSession 자료형에 들어있는 member 를 꺼내야 하는데,
		   JSP 에서는 HttpSession 자료형에 대한 내장객체로 session 이라는 내장 객체를 지원함.
	 -->
	<%
		Member member = (Member)session.getAttribute("member");
		out.print(member.getName() + "님, 반갑습니다");
	%>
</body>
</html>