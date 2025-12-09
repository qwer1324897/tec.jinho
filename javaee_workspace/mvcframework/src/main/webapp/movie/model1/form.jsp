<%@page import="com.ch.mvcframework.movie.model.MovieManager"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%! MovieManager movieManager = new MovieManager(); %>
<%
	// 클라이언트가 전송한 파라미터를 받아 영화에 대한 피드백 메세지 만들기
	request.setCharacterEncoding("utf-8");
	String movie = request.getParameter("movie");
	out.print(movie);
	
	// 각 영화에 대한 메시지를 출력해주는 코드가 별도의 모델 객체로 분리됨
	// 분리 이유: 웹 뿐만이 아니라 다른 플랫폼에서도 재사용하기 위해. 재사용 = 시간감축 = 돈 감축
	String msg = movieManager.getAdvice(movie);
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script>
	function request() {
		document.querySelector("form").action="/movie/model1/form.jsp";
		document.querySelector("form").method="post";
		document.querySelector("form").submit();
	}
	
	addEventListener("load", function() {
		document.querySelector("button").addEventListener("click", ()=>{
			request();
		});
	});
	
</script>
<body>

	<form action="">
		<select name="movie">
			<option value="귀멸의 칼날">귀멸의칼날</option>
			<option value="체인소 맨">체인소맨</option>
			<option value="나우유씨미2">나우유씨미2</option>
			<option value="주토피아2">주토피아2</option>
		</select>
		<button>피드백 요청</button>
	</form>
	
	<!-- 유지보수성을 고려할 필요가 없을 정도로 간단한 규모라면,
			굳이 유지보수성을 염두해 둔 java 클래스까지 도입할 필요가 없다. 단순 스크립트만으로 해결.
			통칭 스크립트 위주 개발(막개발)이라 한다. 아주 간단한 분야에 유용함. -->
	<h3>
		선택한 결과 <br>
		<span style = "color:cadetblue">
			<%=msg%>
		</span>
	</h3>
</body>
</html>