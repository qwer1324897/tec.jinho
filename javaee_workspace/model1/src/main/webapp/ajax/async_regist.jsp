<%@page import="java.util.List"%>
<%@page import="com.ch.model1.repository.Member2DAO"%>
<%@page import="com.ch.model1.dto.Member2"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%! 	Member2DAO dao = new Member2DAO(); %>
<%
	System.out.println("클라이언트의 요청 감지");	// Tomcat 로그에 출력되지만 우리의 경우 Eclips 내부 톰캣이므로 이클립스 콘솔에 출력
	// 파라미터 받기
	request.setCharacterEncoding("utf-8");
	
	String id = request.getParameter("id");
	String name = request.getParameter("name");
	String email = request.getParameter("email");
	
	System.out.println("id= " + id);	
	System.out.println("name= " + name);	
	System.out.println("email= " + email);	
	
	// DTO에 모으기
	
	Member2 dto = new Member2();
	dto.setId(id);
	dto.setName(name);
	dto.setEmail(email);
	
	int result = dao.insert(dto);
	System.out.println(result);	
	
	// 입력 성공 후 페이지 보여주기
	// 아래와 같이 응답 정보로 페이지 접속을 하게 만드는 코드는, 비동기 요청에 대해 클라이언트의 브라우저가 지정한 URL 로 재접속을 시도하기 때문에
	// 그 재접속의 결과인 html 을 서버로부터 받게 되고 html 을 전송받은 브라우저는 해당 html 을 화면에 렌더링 하게 되버린다. => 새로고침 효과가 난다.
	// 따라서 새로고침 없는 without reloading 기능이 상실된다. 지금 비동기 방식인데 이러면 안되겠지?
	// response.sendRedirect("/ajax/async_regist.jsp");
	
	// 솔루션 - 서버에서는 화면 전체를 보내지 말고, 순수하게 목록 데이터만을 전송해주면, 클라이언트는 그 데이터를 js로 동적처리
	// 게시물 목록 가져오기
	List<Member2> list = dao.slelectAll();
	
	// 클라이언트에게 목록 데이터 보내기
	// out.print(list);
	
	/*
	클라이언트에게 응답 정보를 보낼 때, 문자열 말곤 방법이 없다. 하지만 이 문자열을 넘겨받은 클라이언트 브라우저의 자바스크립트는
	아래와 같은 문자열로 구성되어 있을 경우 원하는 데이터를 추출하기 많이 불편하다.
	문제점) 앞으로 우리는 REST API 를 다룰 것이므로, 추후 REST 서버를 구축하여 우리의 서버에 요청을 시도하는 다양한 종류의
			  클라이언트 들에게 데이터를 자공해줄 예정인데, 이 때 사용할 데이터 형식은 전세계적으로 XML 또는 JSON이 압도적이다
	해결책) 전세계 개발자들이 주로 사용하는 표준형식의 데이터를 사용하자(JSON)
			  JSON - 문자열 내의 
	*/
	
	// out.print("게시물 목록입니다.");
	
	// 아래의 json 문자열은 말 그대 문자열이므로, jva 는 그냥 String 으로 처리한다.
	StringBuffer data = new StringBuffer();
	data.append("{");
	data.append("\"name\": \"태호\",");
	data.append("\"email\": \"google\"");
	data.append("}");
																	
	out.print(data.toString());	 // 클라이언트인 웹브라우저에게 보내기.
%>





