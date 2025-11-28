<%@ page contentType="text/html; charset=UTF-8"%>
<%
	// 위의 페이지 지시 영역은, 현재 jsp가 Tomcat 에 의해 서블릿으로 코딩되어질 때
	// text/html 부분은 response.setContentType();
	// charset = UTF-8 response.setCharacterEncoding("UTF-8"); 으로 코딩되어진다.
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
body {
	font-family: Arial, Helvetica, sans-serif;
}
* {
	box-sizing: border-box;
}
input[type=text], select, textarea {
	width: 100%;
	padding: 12px;
	border: 1px solid #ccc;
	border-radius: 4px;
	box-sizing: border-box;
	margin-top: 6px;
	margin-bottom: 16px;
	resize: vertical;
}
input[type=button] {
	background-color: #04AA6D;
	color: white;
	padding: 12px 20px;
	border: none;
	border-radius: 4px;
	cursor: pointer;
}
input[type=button]:hover {
	background-color: #45a049;
}
.container {
	border-radius: 5px;
	background-color: #f2f2f2;
	padding: 20px;
}
</style>
<script>
    // 사용자가 입력한 폼의 내용을 서버로 전송하자.
	// JavaScript 언어는 DB에 직접적으로 통신이 불가능하다.
	// JS는 클라이언트(Front 영역) 이기 때문에 원본 소스가 그냥 노출되어 있다.
	function regist() {
		// JS는 DB와의 통신 자체가 막혀있기 때문에, 직접 DB에 쿼리문을 날리는 것이 아니라,
		// Tomcat 과 같은 웰컨테이너(서버)에게 요청을 한다
		let form1 = document.querySelector(".form1");
		form1.action="/notice/regist";	// 서블릿 주소
		form1.method="post";	// HTTP 프로토콜은 머리와 몸으로 데이터를 구성하여 통신을 하는 규약을 말함
								// 이 때, 서버로 데이터가 양이 많거나, 노출되지 않으려면 Post 방식을 사용한다.
								// 반면, 서로 데이터의 양이 적거나, 노출되어도 상관없을 경우, Get 방식을 사용한다.
		form1.submit();
	}
</script>
</head>
<body>
	<div class="container">
		<form class="form1">
			<input type="text" id="fname" name="title" placeholder="제목 입력">
            <input type="text" id="ㅣname" name="writer" placeholder="작성자 입력">
			<textarea id="subject" name="content" placeholder="내용 입력" style="height: 200px"></textarea>
            <input type="submit" value="submit" onclick = "regist()">
		</form>
	</div>
</body>
</html>