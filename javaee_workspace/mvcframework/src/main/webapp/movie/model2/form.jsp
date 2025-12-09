<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script>
	function request() {
		document.querySelector("form").action="/movie.do";
		document.querySelector("form").method="get";
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
	
</body>
</html>