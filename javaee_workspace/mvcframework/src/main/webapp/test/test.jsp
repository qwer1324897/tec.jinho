<%@ page contentType="text/html; charset=UTF-8"%>
<%
	// JSP는 서블릿에서 개발자가 직접 자료형을 이용하여 사용할 객체들을 편리하게 사용하도록 내장객체를 지원함.
	// 따라서 개발자는 자료형을 명시하지 않고 이미 정해진 객체의 변수명을 사용할 수 있다.
	// out, request, response, session, application 같은 내장 객체들.
	
	// 그 중 application 내장객체는 애플리케이션의 전역적 정보를 가진 객체(서블릿에서의 자료형은 ServletContext)이면서
	// 톰캣을 켤 때부터 끌 때까지 메모리에 살아있는 객체다. 만약 이 안에 회원정보를 넣게 되면 톰캣 서버가 꺼질 때 까지 살아있다.
	// Java EE 에서 데이터를 담을 수 있는 객체 중 가장 생명력이 길다.
	
	// session 내장객체는 클라이언트의 세션쿠키가 유효할 동안 서버에서 정해놓은 일정시간에 재요청이 없을 때까지 살아 있다. 보통 로그인 인증에 많이 사용된다.
	
	// request 내장객체는 요청이 들어와서 응답이 처리될 때까지만 살아있다.
	
	application.setAttribute("born", "분당");
	session.setAttribute("id", "Jin");
	request.setAttribute("hobby", "game");
%>
<a href="/test/result.jsp">결과 페이지 재접속</a>