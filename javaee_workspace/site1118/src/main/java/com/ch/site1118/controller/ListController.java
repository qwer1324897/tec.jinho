package com.ch.site1118.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * JavaEE 기술이 서블릿 기반이므로, 디자인 결과물까지 out.print() 문자열로 처리해야 함.
 * 따라서 웹페이지의 양이 많아지거나, 디자인 코드량이 많아지만 유지보수성이 현저히 떨어진다.
 * 즉, 디자인 표현에 매우 취약하다.
 * */

public class ListController extends HttpServlet{
	
	// 웹브라우저로 요청이 들어올 때, 클라이언트가 Get 방식으로 들어올 경우 이 메서드가 동작.
	// HTTP 통신에 의해, 클라이언트는 서버에 요청을 시도할 때 그 목적에 맞는 메서드를 선택하게 되어 있다.
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		// 클라이언트인 브라우저가 보게 될 컨텐츠를 작성해보기
		// 스트림을 얻기 전에, 한글 등의 다국어가 깨지지 않도록 하려면, 원하는 인코딩을 지정해야 한다.
		response.setContentType("text/html");  // 클라이언트에게 응답할 데이터의 유형을 명시
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		// String은 immutable. 불변이므로, 수정이 안 된다. 따라서 수정 가능한 문자열 처리를 위한 객체인
		// StringBuffer(동기화 지원 X), StringBuiler(동기화 지원 O) 를 사용해야 한다.
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("<table border = \"1px\">");

		for (int i = 5; i >= 1; i--) {
		    sb.append("<tr>"); 
		    for (int n = 1; n <= 3; n++) {
		        sb.append("<td>").append(i).append("0").append(n).append("호</td>");
		    }
		    sb.append("</tr>");
		}
		sb.append("</table>");
		
		out.print(sb.toString()); // 최종적으로 out.print()를 사용할 때 toString()으로 변환하여 출력합니다.
	}
}
