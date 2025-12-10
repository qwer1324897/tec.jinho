package com.ch.mvcframework.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ch.mvcframework.movie.model.MovieManager;

/*
 * MVC(Model, View, Controller) 는 디자인 패턴 중 하나로서
 * 다운로드 받거나, 눈에 보이는 파일 또는 소스가 아니라, 그냥 전산분야에서 예전부터 내려오는 개발 방법 이론일 뿐이다.
 * 핵심은 디자인 영역과 로직(모델) 영역을 완전히 분리시킨다 > 유지 보수성 극대화
 * 
 * Model2 는 Java EE 분야에서 구현한 MVC 패턴을 의미한다.
 * Java EE(웹) 에서 애플리케이션을 개발할 때 디자인과 로직을 분리시키기 위해 사용되어야 할 클래스 유형은 아래와 같다.
 * M - 중립적인 모델이므로 순수 java 클래스로 작성
 * V - 웹상의 디자인을 표현해야 하므로, html 또는 jsp로 작성
 * C - 클라이언트의 요청을 받아야 하고, 오직 Java EE 서버에서만 실행될 수 있어야 하므로 Servlet 으로 작성.
 * 주의) jsp도 사실 서블릿이므로, Controller 역할을 할 순 있으나 jsp는 주로 디자인에 사용되므로
 * 	   컨트롤러로서의 역할은 서블릿으로 구현한다.
 * 
 */
public class MovieController implements Controller{
	
	MovieManager movieManager = new MovieManager();

	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String movie = request.getParameter("movie");
		
//		out.print(movie); 		이 클래스의 목적은 컨트롤러 이므로, 디자인 영역을 다뤄선 안 된다. out.print()를 쓰면 View 영역을 쓰게 되는 것.
		
		String msg = movieManager.getAdvice(movie);
		
		// HttpSession session = request.getSession();
		// 이 때 이 요청과 연관된 세션이 생성되면서, 자동으로 session ID가 발급된다.
		// 또한 응답 정보 생성 시 클라이언트에게 쿠키로 session ID 가 함께 전송됨.
		// 쿠키 - 영구 쿠키(Persistence cookie = 하드디스크), 세션 쿠키(메모리)
		
		// session.setAttribute("msg", msg);
		// 영화 정보 피드백 메세지는, 세션이 죽을 때 까지 함께 사랑있으므로
		// 이 요청이 끝나도 그 값을 유지하고 있음.(메모리에 있기 때문)
		
		// 위 주석에 있는 세션 방식은 MVC 원칙을 지키면서 가능한 방식. 하지만 메모리 낭비가 심하기 때문에 포워딩을 이용한다.
		// 현재 들어온 요청에 대해 응답을 하지 않은 상태로, 또 다른 서블릿에게 요청을 전달.
		// 이 때, 지정된 result.jsp 의 서블릿의 service() 가 호출됨.
		request.setAttribute("movie", movie);
		request.setAttribute("msg", msg);		// 결국 세션과 메모리 유지 시간만 다를 뿐, 사용 방법은 동일하다.
		// 현재 서블릿에서 응답을 처리하지 않았기 때문에 request 객체는 살아있는 채로 result.jsp 의 서블릿까지 유지된다.
		
		RequestDispatcher dis = request.getRequestDispatcher("/movie/model2/result.jsp");	// 포워딩 하고 싶은 자원의 URL
		dis.forward(request, response);
		
		// 위의 판단 결과를 여기서 출력하면 MVC 원칙에 위배. 따라서 판단 결과를 별도의 V(view, 디자인) 영역에서 보여줘야 한다.
		// response.sendRedirect("/movie/mode2/result.jsp"); 	// <script>location.href=url</script>
		// 위 코드는 응답을 하면서 브라우저로 하여금 재접속하게 되는 코드. 따라서 응답하게되므로 포워딩에 맞지 않다.
		
		
	}

	@Override
	public String getViewName() {
		return null;
	}

	@Override
	public boolean isForward() {
		return false;
	}
}
