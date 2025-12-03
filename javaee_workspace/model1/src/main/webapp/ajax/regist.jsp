<%@page import="com.ch.model1.dto.Member2"%>
<%@page import="com.ch.model1.repository.Member2DAO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%! 	Member2DAO dao = new Member2DAO(); %>
<%
	// 스크립틀릿 영역. jsp가 서블릿으로 변환될 때 service 메서드(request, response)영역.
	// 넘어온 파라미터를 받아서 mysql의 member2 테이블에 insert 하자.
	
	// jsp 에서는 개발자가 요청객체, 응답객체의 변수명을 바꿀 수 없다. 이미 내장객체로 결정되어 있기 때문에.(built-in object)
	request.setCharacterEncoding("utf-8");

	String id = request.getParameter("id");
	String name = request.getParameter("name");
	String email = request.getParameter("email");
	
	// PrintWriter 조차도 이미 내장객체로 지원되므로(명칭은 out으로 지정되어 있음) 그냥 바로 갖다 쓰면 된다.
	out.print("id= " + id + "<br>");
	out.print("name= " + name + "<br>");
	out.print("email= " + email + "<br>");
	
	Member2 dto = new Member2();
	dto.setId(id);
	dto.setName(name);
	dto.setEmail(email);
	
	int result = dao.insert(dto);
%>
<script>
<%if(result<1) {%>
	out.print("등록 실패입니다")
<%}else {%>
	location.href="/ajax/main.jsp";
<%}%>
</script>

<html>
</html>








