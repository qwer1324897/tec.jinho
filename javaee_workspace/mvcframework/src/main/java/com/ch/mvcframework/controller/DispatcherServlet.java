package com.ch.mvcframework.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
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
	public void init(ServletConfig config) {
		try {
			ServletContext application = config.getServletContext();
			// 서블릿이므로 application을 바로 못 쓰므로 자료형을 명시하여 변수를 선언하고 얻어와야 한다.
			// 얻어올 땐 서블릿의 환경 정보를 가진 객체인 ServletConfig를 활용하여 현재 애플리케이션의 정보를 가진 ServletContext를 얻어온다.
			
			// 현재 웹애플리케이션이 이클립스 내부 톰캣으로 실행될 지, 아니면 실제 서버에서 실행될 지 개발자가 알 필요 없이
			// 현재 애플리케이션을 기준으로 명시하면 리눅스건 맥이건 윈도우건 상황에 맞게 알아서 경로를 반환.
			String parmValue = config.getInitParameter("contextConfigLocation");
			System.out.println(parmValue);
			String realPath = application.getRealPath(parmValue);
			
			fileInputStream = new FileInputStream(realPath);
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

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doRequest(request, response);
	}
	
	// 클라이언트의 요청 방식이 다양하므로, 어떤 요청 방식으로 들어오더라도, 아래의 메서드 하나로 몰아넣는다면
	// 코드를 메서드마다 재작성할 필요가 없다.
	public void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// request.setCharacterEncoding("utf-8");		필터에서 한글처리. 필터가 맨 먼저 마주하기 때문에 그 뒤는 안 해도 됨
		// 부모 서블릿에 이렇게 한글처리 해놓으면 하위 컨트롤러는 할 필요가 없으나, 이것 마저 추후 필터 단계로 올릴 예정
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
		
//		if (uri.equals("/movie.do")) {		// 클라이언트가 영화에 대한 조언을 구함
			// 영화 전담 컨트롤러에게 요청 전달
//			MovieController controller = new MovieController();
//			controller.execute(request, response);
			
//		} else if (uri.equals("/food.do")) {	// 클라이언트가 음식에 대한 조언을 구함
			// 음식 전담 컨트롤러에게 요청 전달
//			FoodController controller = new FoodController();
//			controller.handle(request, response);}
	
		String controllerPath = properties.getProperty(uri);
		System.out.println(uri +"에 동작할 하위 전문 컨트롤러는 " + controllerPath);
		
		// 여기까지는 하위 컨트롤러의 이름만을 추출한 상태이고 실제 동작하는 클래스 및 인스턴스는 아니다.
		
		try {
			// Class.forName(controllerPath); 	// 이제 동적으로 클래스가 로드된 것.(static(=method) 영역에)
			Class class1 = Class.forName(controllerPath);	// Class.forName 의 리턴값이 클래스 이므로, 클래스로 받는다.
																					// 이 때, class 라는 변수명은 public class 할 때 쓰이는 연산자라 사용이 불가능하므로 다른 변수명 사용.
			// class1.getConstructor().newInstance();
			// static 영역에 올라온 클래스 원본 코드를 대상으로 인스턴스 1개 생성. 이런식으로 new 연산자만이 인스턴스를 만들 수 있는 것은 아님. 
			Object object = class1.getConstructor().newInstance();
			
			// 이제 메모리에 올라온 하위 컨트롤러 객체의 메서드를 호출
			Controller controller=(Controller)object;
			// 현재 시점에 메모리에 올라온 MovieController or FoodController 인지 알 수 없기 때문에
			// 이들의 최상위 객체인 Controller 로 형변환을 한다.
			
			controller.execute(request, response);
			// 아래의 메서드 호출의 경우 분명 부모 형인 Controller 형의 변수로 메서드를 호출하고는 있으나
			// 자바의 문법 규칙상 자식이 부모의 메서드를 오버라이드 한 경우(업그레이드 한 것으로 간주하여) 자식의 메서드를 호출한다
			// 즉 자료형은 부모형이지만, 동작은 자식 자료형으로 할 경우 현실 객체의 다양성을 반영했다고 하여 다형성(= Polymorphism) 이라 한다.
			
			String viewName = controller.getViewName();
			// 이 viewName 에는 실제 jsp가 들어있는 게 아니라 검색 key만 가지고 있으므로
			// DispatcherServlet 은 다시 servlet-mapping.txt 파일을 검색, 실제 jsp 파일을 얻어 클라이언트에게 응답해야 한다.
			
			String viewPage = properties.getProperty(viewName);	// viewName 으로 jap 얻기
			System.out.println("이 요청에 의해 보여질 응답페이지는 " + viewPage);
			
			// 하위 컨트롤러가 포워딩 하라고 부탁한 경우, 포워딩을 처리한다.
			if (controller.isForward()) {
				RequestDispatcher dis = request.getRequestDispatcher(viewPage);
				dis.forward(request, response);
			} else {
			response.sendRedirect(viewPage);		// 클라이언트로 하여금 재 접속할 것을 응답 정보에 추가
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	
	// 서블릿의 생명주기 메서드 중, 서블릿이 소멸할 때 호출되는 메서드인 destroy() 재정의
	// 반드시 닫아야 할 자원등을 해제할 때 중요하게 사용
	public void destroy() {
		if(fileInputStream!=null) {
			try {
				fileInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}