package com.ch.model1.board;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ch.model1.dto.Board;
import com.ch.model1.repository.BoardDAO;

// 글쓰기 요청을 처리하는 서블릿
public class RegistServlet extends HttpServlet{
	
	// RegistServlet has a BoardDAO
	// 자바의 객체와 객체 사이의 관계를 명시할 수 있는데, 2가지 유형으로 나뉜다.
	// 자바에서 특정 객체가 다른 객체를 보유한 관계를 has a 관계라 한다
	// 자바에서 특정 객체가 다른 객체를 상속받는 관계를 is a 관게라 한다.
	BoardDAO boardDAO = new BoardDAO();
	// 서블릿의 생명주기에서 인스턴스는 최초의 요청에 의해 1 번만 생성되므로,
	// 서블릿의 멤버변수로 선언한 객체도 1 번 생성됨
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// jsp 의 page 지시 영역과 동일한 코드
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		// 넘겨받은 파라미터를 이용하여 DB에 직접 넣는 것이 아니라, 전담 객체에게 시켜야 함
		// 별도의 데이터베이스 처리 객체는 DB 코드의 재사용을 위해 따로 정의해야 한다.
		// 특히, 다른 로직이 포함되어선 안 되며 오직 DB에 관련된 CRUD 만을 담당하는 객체인 DAO(Data Access Object) 만 사용 
		
		String title = request.getParameter("title");
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");
		
		// 파라미터를 db에 넣기
		// insert 메서드를 호출하기 전에 낱개로 존재하는 파라미터들을 DTO 에 모아서 전달한다.
		Board board = new Board(); 	// DTO 생성. 아직은 비어있는 상태다. set으로 넣어야 채워지는 것.
		board.setTitle(title);
		board.setWriter(writer);
		board.setContent(content);
		
		int result = boardDAO.insert(board);
	
		PrintWriter out = response.getWriter();
		
		out.print("<script>");
		if (result < 1) {
			out.print("alert('등록실패');");
			out.print("history.back();");
		} else {
			out.print("alert('등록성공');");
			out.print("location.href='/board/list.jsp';");
		}
		out.print("</script>");
	}
}
