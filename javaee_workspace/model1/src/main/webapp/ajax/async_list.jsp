<%@page import="com.ch.model1.dto.Member2"%>
<%@page import="java.util.List"%>
<%@page import="com.ch.model1.repository.Member2DAO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%!
	Member2DAO dao = new Member2DAO();
%>
<%
	// 비동기 방식의 경우, 클라이언트의 요청이 들어오면 서버는 HTML이 아니라 데이터만 보내야 한다. html 을 보내면 새로고침 현상 발생
	// 목록 가져오기
	List<Member2> list = dao.selectAll();

	// 클라이언트가 이해할 수 있는 데이터형식으로 응답, 여기서는 클라이언트가 웹브라우저 이므로
	// JSON 으로 응답하겠다(JSON은 중립적 문자열이기 때문에 스마트폰같은 각종 디바이스에서도 이해할 수 있는 형식의 데이터이다.)
		// 아래의 json 문자열은 말 그대 문자열이므로, jva 는 그냥 String 으로 처리한다.
	StringBuffer data = new StringBuffer();
	
	data.append("[");
	for(int i =0; i<list.size(); i++) {
		Member2 object = list.get(i);
		data.append("{");
		data.append(" \"member2_id\" : "+ object.getMember2_id() +", ");
		data.append(" \"id\" : \""+ object.getId() +"\", ");
		data.append(" \"name\" : \""+ object.getName() +"\", ");
		data.append(" \"email\" : \""+ object.getEmail() +"\" ");
		data.append("}");
		if(i < list.size()-1) {
		data.append(",");	// 쉼표는 list의 총 길이 - 1 보다 작은 경우
		};
	}
	data.append("]");
	
	out.print(data.toString());	 // 클라이언트인 웹브라우저에게 보내기.

%>