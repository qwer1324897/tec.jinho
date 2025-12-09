<%@ page contentType="text/html; charset=UTF-8"%>
<%
	/*
	하나의 페이지로 모든 기능과 디자인을 합쳐놓은 프로그램의 장단점
	장점) 개발시간이 압도적으로 적음
	단점) 디자인과 로직이 스파게티마냥 뒤섞여있어, 디자인을 버릴 경우 로직도 함께 버려야 함.
	*/

	// 클라이언트가 전송한 파라미터를 받아 영화에 대한 피드백 메세지 만들기
	request.setCharacterEncoding("utf-8");
	String movie = request.getParameter("movie");
	out.print(movie);
	
	// 각 영화에 대한 메시지 만들기
	String msg = "선택된 영화가 없음";
	
	if(movie !=null) {		// 파라미터가 있을 때만
		if(movie.equals("귀멸의 칼날")) {
			msg = "귀멸의 칼날 원작 애니메이션 극장 시리즈 무한성편";
		} else if (movie.equals("체인소 맨")){
			msg = "체인소 맨 원작 애니메이션 극장 시리즈 레제편";
		}else if (movie.equals("나우유씨미2")){
			msg = "나우유씨미 시리즈 2편";
		}else if (movie.equals("주토피아2")){
			msg = "주토피아 시리즈 2편";
		}
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script>
	function request() {
		document.querySelector("form").action="/movie/script/form.jsp";
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