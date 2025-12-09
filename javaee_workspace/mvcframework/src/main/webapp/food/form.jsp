<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script>
	function request() {
		document.querySelector("form").action="/food.do";
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
		<select name="food">
			<option value="제육볶음">제육볶음</option>
			<option value="라멘">라멘</option>
			<option value="햄버거">햄버거</option>
			<option value="스시">스시</option>
		</select>
		<button type= "button">피드백 요청</button>
	</form>
	
</body>
</html>