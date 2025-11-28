<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<style>
body {font-family: Arial, Helvetica, sans-serif;}
* {box-sizing: border-box}

/* Full-width input fields */
input[type=text], input[type=password] {
  width: 100%;
  padding: 15px;
  margin: 5px 0 22px 0;
  display: inline-block;
  border: none;
  background: #f1f1f1;
}

input[type=text]:focus, input[type=password]:focus {
  background-color: #ddd;
  outline: none;
}

hr {
  border: 1px solid #f1f1f1;
  margin-bottom: 25px;
}

/* Set a style for all buttons */
button {
  background-color: #04AA6D;
  color: white;
  padding: 14px 20px;
  margin: 8px 0;
  border: none;
  cursor: pointer;
  width: 100%;
  opacity: 0.9;
}

button:hover {
  opacity:1;
}

/* Extra styles for the cancel button */
.cancelbtn {
  padding: 14px 20px;
  background-color: #f44336;
}

/* Float cancel and signup buttons and add an equal width */
.cancelbtn, .signupbtn {
  float: left;
  width: 50%;
}

/* Add padding to container elements */
.container {
  padding: 16px;
}

/* Clear floats */
.clearfix::after {
  content: "";
  clear: both;
  display: table;
}

/* Change styles for cancel button and signup button on extra small screens */
@media screen and (max-width: 300px) {
  .cancelbtn, .signupbtn {
     width: 100%;
  }
}
</style>
<!-- 외부에 저장해 놓은 코드를 실행타임에 동적으로 들어오도록 처리하는 include 를 사용하자.(매번 치기 힘드니까) -->
<%@ include file="../inc/header_link.jsp" %> 	<!-- 미리 따로 저장해둔 코드 경로다. ctrl 클릭으로 들어가서 내용을 확인. -->
<script>
	$(function(){
		//.clearfix 라는 클래스 안에 들어있는 두 개의 버튼들 중 두번 째 버튼에 click 이벤트 연결하기
		$($(".clearfix button")[0]).click(function(){
			$("#form1").attr({
				action:"/member/login",
				method:"post"
			});
			$("#form1").submit();
		});
	});
</script>
<body>

<form action="/action_page.php" style="border:1px solid #ccc" id="form1">
  <div class="container">
    <h1>Login</h1>
    <label for="ID"><b>ID</b></label>
    <input type="text" placeholder="Enter ID" name="id" required>

    <label for="pwd"><b>Password</b></label>
    <input type="password" placeholder="Enter Password" name="pwd" required>
    
    <label>
      <input type="checkbox" checked="checked" name="remember" style="margin-bottom:15px"> Remember me
    </label>

    <div class="clearfix">
      <button type="button" class="signupbtn">Login</button>
      <button type="button" class="cancelbtn">Sign up</button>
    </div>
  </div>
</form>

</body>
</html>
