<%@page import="com.ch.mybatisapp.dto.News"%>
<%@page import="org.apache.ibatis.session.SqlSession"%>
<%@page import="com.ch.mybatisapp.mybatis.MyBatisConfig"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%
	MyBatisConfig myBatisConfig = MyBatisConfig.getInstance();
	
	SqlSession sqlSession = myBatisConfig.getSqlSession();
	
	// Mybatis 는 개발자가 자바 소스 안에 쿼리문을 작성하도록 하지 않는다.
	// 따라서 개발자는 쿼리문을 xml 에 작성함으로서 JDBC같은 잡다한 코드를 작성하지 않고 오직 쿼리만 집중할 수 있도록 지원.
	News news = new News();
	news.setTitle("Mybatis  연습");
	news.setWriter("qwer");
	news.setContent("연습내용");
	
	int result = sqlSession.insert("com.ch.mybatisapp.dto.News.insert", news);
	
	// mybatis의 SqlSession 은 DML 수행 시 트랜잭션을 commit 해야 함
	sqlSession.commit();
	
	if(result < 1) {
		out.print("등록 실패");
	} else {
		out.print("등록 성공");
	}
	
	myBatisConfig.release(sqlSession);
%>