package src;

/*
 * Java EE 기반으로 웹애플리케이션에서 실행될 수 있는 특수한 클래스를 사용해야 하는데,
 * 이렇게 서버에서만 해석 및 실행되는 클래스를 서블릿(Servlet)이라 한다.
 * 
 * 또한 JavaEE 기반의 웹애플리케이션의 구성 디렉토리는 JavaEE 기반 스펙으로 정해져 있기 때문에
 * 반드시 정해진 디렉토에 .class .jar등을 위치시켜야 한다.
 * 
 * /Web-INF : 웹브라우저를 통해서는 절대로 접근하지 못하는 보안된 디렉토리
 *  - classes : 컴파일 된 클래스들
 *  - lib : .jar 들이 위치
 */

 // 아래의 클래스가 JavaEE 서버에서 실행되려면 반드시 서블릿 클래스를 상속받아야 한다.

import javax.servlet.http.HttpServlet;

class MyServlet extends HttpServlet {
    String name = "puppy";
}
