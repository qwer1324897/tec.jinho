<%@page import="java.sql.Connection"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.sql.DataSource"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%

	// 톰캣에 JNDI로 설정해 놓은 커넥션을 사용해보기
	
	// Tomcat 설정해 놓은 자원을 이름으로 검색
	InitialContext ctx = new InitialContext();	// JNDI 검색 객체
	DataSource pool = (DataSource)ctx.lookup("java:comp/env/jndi/mysql");
	
	Connection connection = pool.getConnection();	// 풀에 들어있는 Connection 객체 꺼내기
	
	out.print("pool 로 부터 얻어온 커넥션 객체는 " + connection);

%>