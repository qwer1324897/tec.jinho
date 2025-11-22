<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.DriverManager"%>
<%@ page contentType="text/html; charset=UTF-8"%>  <!-- 이곳이 지시영역 -->
<%@ page import = "java.sql.Connection" %>
<%@ page import = "java.sql.PreparedStatement" %>
<%!
	// 선언부는 서블릿으로 변환될 때 자동으로 멤버영역으로 자리잡는다.
	Connection connection;
	PreparedStatement pstmt;
	ResultSet rs;
	
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "servlet"; 
	String pwd =  "1234";
%>
<%
	// page 지식 영역에서 contentType() 을 명시한 것은, 이 jsp가 서블릿으로 변환되어질 때
	// response 객체의 메서드 중 setContentType("text/html;charset=utf-8")
	
	// 오라클 연동하기
	
	// 아래 코드의 경우 java 클래스에서 작성할 때, try catch가 강제되지만
	// 현재 우리의 jsp 영역은 실행 직전 tomcat에 의해 서블릿으로 변환되어지며 특히 스크립틀릿 영역은
	// service() 메서드로 코드가 작성되고, 이 때 tomcat 이 예외처리까지 해버렸으므로, 
	// jsp 에서는 try catch 를 강요당하지 않는다.
	
	// 1단계) 드라이버 로드
	Class.forName("oracle.jdbc.driver.OracleDriver");
	
	// 2단계) 접속
	connection = DriverManager.getConnection(url, user, pwd);
			
	// 3단계) 쿼리 실행
	String sql = "select * from gallery";
	pstmt = connection.prepareStatement(sql);	// 쿼리 수행 객체 생성
	
	// 쿼리문이 select 인 경우, 그 결과표를 받는 객체가 ResultSet
	rs = pstmt.executeQuery();	// 쿼리 수행 후 그 결과를 rs로 받는다
	
%>
<%! /* 이곳이 선언부 
		핵심 - jsp는 사실상 서블릿이다.
		결론: jsp의 개발목적은 서블릿의 경우 디자인을 표현하는데 너무나 비효율적이므로, 개발자 대신 디자인 컨텐츠를
			   시스템인 웹 컨테이너가 대신 작성해 주기 위한 스크립트 언어다. */
   	int x = 7;		// 멤버변수
	
	public int getX() {	// 멤버 메서드
		return x;
	}
%>
<%
	//이 영역을 스크립틀릿이라 하며, 추후 고양이에 의해 이 jsp가 서블릿으로 변환되어질 때
	// 이 영역에 작성한 코드는 service() 안에 작성한 것과 같아진다.
	// out을 선언한 적도 없는데 잘 동작한다. jsp는 총 9가지 정도의 내장객체를 지원한다. (Built-in Object)
	// 문자 기반의 출력스트림 객체를 미리 변수명(out)까지 지정해 놓았다.
	// out.print(getX());
%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
table {
  border-collapse: collapse;
  border-spacing: 0;
  width: 100%;
  border: 1px solid #ddd;
}

th, td {
  text-align: left;
  padding: 16px;
}

tr:nth-child(even) {
  background-color: #f2f2f2;
}
</style>
</head>
<body>

<pre>
	JSP - Java Server Page (자바 기반의 서버에서 실행되는 페이지)
		오직 JavaEE 기반의 서버에서만 해석 및 실행된다.
		장점 - 서블릿과 달리 HTML 을 혼용하여 사용이 가능하다. (서블릿의 디자인 표현의 취약점 보완하기 위한 기술)
	
	JSP는 다음의 3가지 영역에 코드를 작성할 수 있다
		
		1. 지시영역 - @가 붙은 영역
			현재 jsp 페이지의 인코딩, 파일 유형, 다른 클래스 import 등을 위한 영역
			
		2. 선언부	 - ! 가 붙은 영역
			멤버 영역(멤버 변수나 메서드를 선언할 수 있는 영역)
		
		3. 스크립틀릿 - 실행영역
			실질적으로 로직을 작성하게 될 영역
</pre>

<h2>서버에 저장된 파일 목록</h2>
<p>nth-child() selector 를 사용하여 모든 짝수(또는 홀수) 테이블 행에 background-color 를 추가할 수 있다.</p>

<table>
  <tr>
    <th>First Name</th>
    <th>Last Name</th>
    <th>Points</th>
  </tr>
  	<%// rs 객체의 next() 메서드를 호출할 때 마다 커서가 한 칸씩 밑으로 전진한다.
  		 // 이 때, 커서가 위치한 행의 레코드가 존재할 경우 true를 반환하지만, 존재하지 않으면 false를 반환한다.
  		 // 따라서 모든 레코드만큼 반복문을 수행하려면 next()가 참인 동안 반복하면 된다.%>
 	<%while(rs.next()) {%>
	  <tr>
	    <td><%out.print(rs.getInt("gallery_id")); %></td>
	    <td><%out.print(rs.getString("title")); %></td>
	    <td><%out.print(rs.getString("filename")); %></td>
	  </tr>
	<%} %>
</table>
</body>
</html>
<%
	rs.close();
	pstmt.close();
	connection.close();
%>