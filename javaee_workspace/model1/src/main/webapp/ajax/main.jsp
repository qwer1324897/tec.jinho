<%@page import="com.ch.model1.dto.Member2"%>
<%@page import="java.util.List"%>
<%@page import="com.ch.model1.repository.Member2DAO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%!
	Member2DAO dao = new Member2DAO();
%>
<%
	List<Member2> memberList = dao.slelectAll();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    .container {
        width: 650px;
        height: 500px;
        margin: auto;
    }
    .aside {
        width: 150px;
        height: 100%;
        background-color: aliceblue;
        float: left;
    }
    .aside input{
        width: 90%;
    }
    .aside button {
        width: 40%;
    }
    .content {
        width: 500px;
        height: 100%;
        background-color: azure;
        float: right;
    }
</style>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script>
	// 지금까지는 동기 방식으로 서버에 요청을 시도하고, 그 결과로 html 을 가져와서 브라우저 화면에 출력함으로서
	// 유저가 보기엔 새로고침 현상이 발생하게 됐다. 따라서 현재 페이지는 그대로 유지하고, 백그라운드에서 크롬과 같은 웹브라우저가
	// 대신 서버와의 통신을 담당하고, 그 시간 동안 자바스크립트는 원래 하고자 했던 로직을 그대로 수행하게 한다.
	// 추후, 서버로부터 응답이 오면 크롬 같은 브라우저가 자바스크립트에 보고를 하게 된다. 이 때 서버로부터 가져온 html이 아니라 순수한 데이터를 전달한다.
	// 그러면 자바스크립트는 이 순수 데이터를 이용하여 화면에 동적으로 출력한다. => 새로고침이 발생하지 않는다.
	
	function sendAsync() {
		// 비동기 방식의 핵심이 되는 자바스크립트 객체가 바로 XMLHttpRequest 이다
		
		let xhttp = new XMLHttpRequest();	// 주의) 이 객체가 서버로 요청을 하는 게 아니라 크롬과 같은 브라우저가 요청을 시도한다.
		
		// 크롬 등의 브라우저가 서버로부터 응답을 받을 때 발생하는 이벤트를 처리하는 속성을 정의.
		// 브라우저가 서버로부터 응답을 받으면 onload 에 지정한 콜백함수를 자동으로 호출한다. (이 때 호출 주체는 js)
		xhttp.onload = function() {
			// 서버가 보내온 데이터를 담고 있는 속성인 responseText 를 사용해보자.
			
			// 서버가 보내온 데이터는 무조건 문자열이기 때문에 아래의 형식이 마치 js의 객체 리터럴로 착각 될 수 없다.
			// 결론은 객체가 아니다.
			// 객체가 아니므로, 속성이라는 것도 존재할 수 없다. 즉, 객체에 .점찍고 접근 불가.
			// {"name" : "jin", "email" : "google"}
			// 해결책. 어떤 문자열이 JSON 기법을 준수하여 작성되어 있다면 js는 내장객체인 JSON 내장 객체를 이용하여
			// 문자열을 해석해서 실제 자바스크립트 객체 리터럴로 전환해줄 수 있다.
			
			let obj = JSON.parse(xhttp.response.Text); 	// 문자열을 해석하여 JSON 구분 형식에 맞츨 경우만, 객체 리터럴로 전환해준다.
			// 정말로 obj가 js 의 인스턴스라면, 객체, 속성을 접근할 수 있어야 한다.
			// 따라서 검증해보자
			
			console.log("email 은 ", obj.email);
			
			// console.log("서버로부터 받은 응답 정보는 ", xhttp.responseText);
			
			// 서버로부터 전송되어온 문자열을 대상으로 원하는 값 추출하기
		
		// 요청할 준비
		xhttp.open("POST", "/ajax/async_regist.jsp");		// 어떤 서버의 주소에 요청을 시도하고, 어떤 HTTP 메서드로 요청을 시도할지 결정하는 메서드
		
		// HTTP 메서드가 post 인 경우 헤더값을 다음과 같이 설정해야 한다.(평소엔 브라우저가 대신 해줌)
		// 헤더에 대한 설명은 반드시 open 메서드 이후에 작성돼야 한다.
		xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");	// 서버야 이 요청은 post야
		
		
		// 브라우저에게 요청
		xhttp.send("id=" + $("input[name='id']").val() + "&name=" + $("input[name='name']").val() + "&email=" + $("input[name='email']").val());	// 요청 시작
	}

    $(()=>{     // Ã­ÂÂÃ¬ÂÂ´Ã­ÂÂ Ã­ÂÂ¨Ã¬ÂÂ. ÃªÂ¸Â°Ã¬Â¡Â´ Ã­ÂÂ¨Ã¬ÂÂ Ã¬Â ÂÃ¬ÂÂ function() Ã«Â¥Â¼ ()=> Ã«Â¡Â Ã¬Â¤ÂÃ¬ÂÂ¬Ã¬ÂÂ Ã­ÂÂÃ­ÂÂ
        // Ã«ÂÂÃªÂ¸Â°Ã«Â²ÂÃ­ÂÂ¼(sync)Ã¬ÂÂ Ã­ÂÂ´Ã«Â¦Â­ Ã¬ÂÂ´Ã«Â²Â¤Ã­ÂÂ¸ Ã¬ÂÂ°ÃªÂ²Â°
        $($("form button")[0]).click(()=>{
            // alert("Ã«ÂÂÃªÂ¸Â° Ã«Â°Â©Ã¬ÂÂ Ã¬ÂÂÃ¬Â²Â­ Ã¬ÂÂÃ«ÂÂ");
            $("form").attr({
                action:"/ajax/regist.jsp",
                method:"post"
            });
            $("form").submit();
        });

    	// 비동기 방식 요청 시도
    	
    	// 동기 - 전통적으로 순서를 지키는 실행 방식을 의미
    	// 			장점: 순서에 의해 실행되므로, 이전 단계의 실행이 완료되어야 다음 단계가 실행되므로 순서가 엉키지 않고 안정적이다.
		//    		단점: 만약 이전 단계 실행이 반복문이거나, 대기상태에 빠질 경우(서버대기 등), 후순위 로직은 지연되거나 계속 기다리게 된다.
		// 비동기 - 순서를 지키지 않는 방식
		//			장점: 순서를 지키지 않기 때문에 앞선 실행부가 대기 상태에 빠진다고 해도, 후순위 실행은 영향을 받지 않는다.
		//			당점: 서버로부터 응답 받는 데이터 형식이 HTML 이 아니므로, 새로고침 현상은 없지만 대신 페이지 디자인을
		//			  	   동적으로 처리하는데 많은 시간과 노력이 필요다하(렌더링)
   		//			참고) 이렇게 시대가 지날수록 페이지를 동적으로 처리하는 양이 너무 많아지니까 페북 개발자들이 만들어낸 자바스크립트 기반의 프레임웤이 React.js 이다.(+ Vue.js)
        $($("form button")[1]).click(()=>{
            sendAsync();
        });
    });

</script>
</head>
<body>
    <div class="container">
        <div class="aside">
            <form>
                <input type="text" placeholder="아이디" name="id">
                <input type="text" placeholder="이름" name="name">
                <input type="text" placeholder="이메일" name="email">
                <button type="button">sync</button>
                <button type="button">async</button>
            </form>
        </div>
        <div class="content">
            <table width="100%" border="1px">
                <thead>
                    <th>아이디</th>
                    <th>이름</th>
                    <th>이메일</th>
                </thead>
                <tbody>
                	<%for(int i = 0; i<memberList.size(); i++) {%>
                	<%
                		Member2 dto = memberList.get(i);
                	%>
                    <tr>
                        <td><%=dto.getId() %></td>
                        <td><%=dto.getName() %></td>
                        <td><%=dto.getEmail() %></td>
                    </tr>
                    <%} %>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>