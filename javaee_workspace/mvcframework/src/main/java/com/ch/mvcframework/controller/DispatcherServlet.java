package com.ch.mvcframework.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 엔터프라이즈급의 규모가 큰 애플리케이션에서 클라이언트의 수 많은 요청마다
// 1:1 대응하는 서블릿을 선언하고 매핑한다면 코드가 너무 방대해지고 유지보수성이 오히려 떨어진다.
// 따라서 컨트롤러를 하나 정해서, 모든 요청에 대한 매핑을 몰아서 관리한다.(예. 대기업 고객센터 매커니즘)

public class DispatcherServlet extends HttpServlet{
	
	// 결국 if 문을 커맨드 패턴과 팩토리 패턴을 이용하여 대체하기 위한 준비물
	FileInputStream fileInputStream;
	Properties properties;
	
	// 아래의 init 은 서블릿이 인스턴스가 생성되어진 직후 호출되는 서블릿 초기화 목적의 메서드이다.
	// init() 메서드 안에 명시된 매개변수인 ServletConfig 는 단어에서 의미하듯 서블릿과 관련된 환경 정보를 갖고 있는 객체이다.
	public void init() {
		try {
			fileInputStream = new FileInputStream("C:\\Workspace\\tec.jinho\\javaee_workspace\\mvcframework\\src\\main\\webapp\\WEB-INF\\servlet-mapping.txt");
			properties = new Properties();
			properties.load(fileInputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// 음식, 영화, 블로그, 음악 등등의 모든 요청을 이 클래승서 받아야 한다.
	// 이 때, 요청 시 메서드 Get, Post, Put, Delete 등 모든 종류의 요청을 다 받을 수 있어야 한다.

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}
	
	// 클라이언트의 요청 방식이 다양하므로, 어떤 요청 방식으로 들어오더라도, 아래의 메서드 하나로 몰아넣는다면
	// 코드를 메서드마다 재작성할 필요가 없다.
	protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("클라이언트의 요청 감지");
		/*
		 모든 컨트롤러의 5대 업무
		 1. 요청 받기
		 2. 요청 분석하기
		 3. 알맞는 로직 객체에 일을 시킨다
		 4. 결과는 View 에서 보여줘야 하므로, View 페이지로 가져갈 결과 저장(세션이 아닌 request 에)
		 5. 결과 페이지 보여주기
		 */
		
		// 요청 분석 (음식, 영화 등 현재 클라이언트가 요청한 유형이 무엇인지 부터 파악)
		// 클라이언트가 요청 시 사용한 주서 표현식인 URI 가 곧 클라이언트가 원하는 게 무엇인지에 대한 구분값이기도 하다
		String uri = request.getRequestURI();
		System.out.println("클라이언트가 요청 시 사용한 uri는 " + uri);
		
		/*
		 아래의 코드에서 클라이언트의 모든 요청마다 1:1 대응하는 if 문으로 요청을 처리하게 되면,
		 요청의 수가 방대해질 경우 유지 보수성이 떨어진다. 따라서
		 각 요청을 조건문이 아닌 객체로 처리해야 한다. > Command Pattern 과 Factory Pattern 을 이용한다.
		 Factory Pattern: 객체의 생성 방법에 대해선 감추어 놓고, 개발자로 하여금 객체의 인스턴스를 얻어갈 수 있는 객체 정의 기법.
		 */
		
		if (uri.equals("/movie.do")) {		// 클라이언트가 영화에 대한 조언을 구함
			// 영화 전담 컨트롤러에게 요청 전달
//			MovieController controller = new MovieController();
//			controller.execute(request, response);
			String controllerPath = properties.getProperty(uri);
			System.out.println("movie 에 등장할 하위 전문 컨트롤러는 " + controllerPath);
			
		} else if (uri.equals("/food.do")) {	// 클라이언트가 음식에 대한 조언을 구함
			// 음식 전담 컨트롤러에게 요청 전달
//			FoodController controller = new FoodController();
//			controller.handle(request, response);
			String controllerPath = properties.getProperty(uri);
			System.out.println("food 에 등장할 하위 전문 컨트롤러는 " + controllerPath);
		}
		
	}	
	
}




