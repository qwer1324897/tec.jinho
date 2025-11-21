package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* 클래스 중 오직 javaEE 의 서버에서만 해석 및 실행되어질 수 있는 클래스를 가리켜 서블릿(Servlet) 이라고 한다.
 * 현재 클래스를 서블릿으로 만들려면, HttpServlet을 상속받으면 된다.
 *  */

public class MyServlet extends HttpServlet {	// 자바에서 상속관계는 is a 관계로서, 같은 자료형으로 간주.
						    // extends는 is a 라는 뜻이다.
	
	// 이 서블릿이 컨테니어에 의해 최초로 인스턴스가 생성될 때 초기화를 위해 무조건 호출되는 메서드
	// 주의) 생성자가 아님. 그냥 일반 메서드인데 생성자 호출 직후에 초기화를 위해
	// 이른 시점에 호출되는 것 뿐이다.
	// 서블릿의 생명주기 3가지 메서드 중 첫 번째 메서드다. (init(), service(), destory())
	// 서블릿의 생성은 컨테이너(고양이 서버-tomcat)가 담당하며, 이 서블릿의 초기화 정보를 넘겨줌
	public void init(ServletConfig config) throws ServletException {
		System.out.println("본인 방금 태어나서 초기화 됐음.");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 현재 클래스를 웹브라우저로 요청하는 클라이언트에게 메시지 출력
		PrintWriter out = resp.getWriter();		// 문자 기반의 출력 스트림 얻기
		// 개발자가 이 출력스트림에 문자열을 저장해두면, 고양이 서버가 알아서 웹 브라우저에 출력해버린다.
		out.println("I'm Jay");
		
		// 서블릿 인스턴스가 소멸될 때 호출되는 메서드
		destroy();
	}
}



