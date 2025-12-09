<%@page import="com.ch.model1.dto.News"%>
<%@page import="com.ch.model1.repository.NewsDAO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%!
	NewsDAO newsDAO = new NewsDAO();
%>
<%
	// list.jsp 로부터 전송되어온 파라미터인 board_id 의 값을 이용하여 DAO 를 이용한다.(select 메서드 호출)
	// select * from board where board_id=n
	String news_id = request.getParameter("news_id");
	News news = newsDAO.select(Integer.parseInt(news_id));
%>
<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
body {font-family: Arial, Helvetica, sans-serif;}
* {box-sizing: border-box;}

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

input[type=submit] {
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
<!-- include libraries(jQuery, bootstrap) -->
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<!-- include summernote css/js -->
<link href="https://cdn.jsdelivr.net/npm/summernote@0.9.0/dist/summernote.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.9.0/dist/summernote.min.js"></script>
<script>

	// 댓글의 목록을 출력할 함수
	function printList(commentList) {
		let tag="<table width = '100%' border = '1px'>";
		tag+="<thead>"
			tag+="<tr>"
				tag+= "<th>No</th>"
				tag+= "<th>댓글내용</th>"
				tag+= "<th>작성자</th>"
				tag+= "<th>작성일</th>"
			tag+="</tr>"
		tag+="</thead>"
		tag+="<tbody>"
		
		let num = commentList.length;	// 게시물 수를 담아놓고, 아래 반복문에서 -- 처리할 예정
		
		for(let i = 0; i <commentList.length; i++) {
			let obj = commentList[i];	// i 번째에서 게시물 1건 꺼내기 (Comment DTO > News DTO)
			tag += "<tr>";
			tag += "<td>" + (num--) + "</td>";
			tag += "<td>" + obj.msg + "</td>";
			tag += "<td>" + obj.reader + "</td>";
			tag += "<td>" + obj.writedate + "</td>";
			tag += "</tr>";
		}
		
		
		
		tag+="</tbody>"
		tag+="</table>"
		
		$(".commentList").html(tag);
	}

	// 댓글의 목록을 비동기로 가져오기
	// 상세페이지 들어왔을 때 호출, 실시간 댓글을 등록할 때도 호출
	function getList() {
		let xhttp = new XMLHttpRequest();
		
		xhttp.onload= function(){
			// 서버에서 네트워크를 타고 전송되어온 데이터는 무조건 문자열이고, 현재 문자열 상태에서는 직접 사용할 수가 없기 때문에
			// 프로그래밍 언어에서 사용하기 편한 형태인 객체로 변환하자. JSON 내장 객체를 이용.
			let commentList = JSON.parse(this.responseText);
			console.log("변환된 객체 ", commentList);
			printList(commentList);
		}
		
		xhttp.open("GET", "/news/comment_list.jsp?news_id=<%=news_id%>");
		
		xhttp.send();
	}
	
	function registComment() {
		let xhttp = new XMLHttpRequest();
		
		xhttp.open("POST", "/news/comment_regist.jsp");
		
		let msg = $("input[name='msg']").val();
		let reader = $("input[name='reader']").val();
		let news_id = $("input[name='news_id']").val();
		
		// 서버로부터 응답 정보가 도착했을 때 우측의 익명 함수를 호출하게 되는 ajax의 XMLHttpRequest의 속성
		xhttp.onload = function(){
			// alert(this.responseText);
			// 여기서 전달된 형태의 문자열이 json 표기법을 준수했을 경우, JSON.parse()에 의해 자동으로 객체 리터럴로 표현할 수 있다.
			let obj = JSON.parse(this.responseText);
			// alert(obj.resultMsg);
			getList(); 	// 등록된 결과물 마져 비동기로 요청하자(새로고침 없이 하기 위해)
		}
		
		// 비동기로 POST 요청을 시도하기 위해선 기존의 브라우저가 자동으로 해주던 x-www-form-urlencoded 를 직접 지정해야 한다.
		xhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		
		xhttp.send("msg="+msg+"&reader="+reader + "&news_id=" + news_id); 		// 서버에 요청 시작
	}

$(function(){
	// 서머노트 연동
	$("#summernote").summernote({
		placeholder:"내용을 입력하세요",
		height:250
	});
	
	$("#summernote").summernote("code", "<%=news.getContent()%>");
	
	getList();
	
	// 글수정 버튼에 이벤트 연결
	$("#bt_edit").click(function(){
		if(confirm("수정하시겠습니까?")) {
			$("form").attr({
				action:"/news/edit",
				method:"POST"
			})	;
			$("form").submit();
		}
		
	});
	
	// 글삭제 버튼에 이벤트 연결
	$("#bt_del").click(function(){
		if(confirm("삭제하시겠습니까?")) {
			$("form").attr({
				action:"/news/delete",
				method:"POST"
			})	;
			$("form").submit();
		}
	});
	
	// 글목록 버튼에 이벤트 연결
	$("#bt_list").click(function(){
		location.href="/news/list.jsp";
	});
	
});
	
</script>
</head>
<body>
<h2>상세보기</h2>
<div class="container">
  <form>
 	
 	<!-- 디자인적으로 보여지지 않고, 오직 개발자 필요에 의해 넘기는 파라미터일 경우 숨김 파라미터가 되어야 함 -->
 	<input type="hidden" name="news_id" value="<%=news.getNews_id()%>">
  
    <label for="fname">제목</label>
    <input type="text" id="fname" name="title" value="<%=news.getTitle()%>">

    <label for="lname">작성자</label>
    <input type="text" id="Iname" name="writer" placeholder = "<%=news.getWriter()%>">

    <label for="subject">내용</label>
    <textarea id="summernote" name="content" placeholder="Write something.." style="height:200px"><%=news.getContent()%></textarea>

    <input type="button" value="수정" id="bt_edit">
    <input type="button" value="삭제" id="bt_del">
    <input type="button" value="목록" id="bt_list">
  </form>
  
	<div>
		<input type="hidden" name = "news_id" value ="<%=news.getNews_id()%>">
		<!-- hidden 은 개발자만 쓰기 위해. 결재정보같이 유저가 수정해선 안 되는 경우 사용. -->
		
		<input type="text" style="width:65%; background:azure;" name = "msg" placeholder = "댓글 내용">
		<input type="text" style="width:20%; background:aliceblue;" name = "reader" placeholder = "작성자">
		<input type="button" value="댓글등록" onClick = "registComment()">
	</div>  
  
  	<div class = "commentList">
  	
  	
  	</div>
  
</div>

</body>
</html>
