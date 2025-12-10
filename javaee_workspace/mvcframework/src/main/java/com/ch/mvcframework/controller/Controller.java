package com.ch.mvcframework.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 일반, 추상, 인터페이스 Java의 3가지 객체 중, MovieController와 FoodController 같은 컨트롤러들을 하나의 부모 컨트롤러에 상속시킨다.
 그렇게 해야 클라이언트쪽에서 어떤 요청이 들어오던 하나의 부모 컨트롤러로 핸들링 할 수 있기 때문.
 이 때, 추상 객체의 경우 자식에게 추상 메서드를 강제할 수 있는 장점은 있으나, 자식 클래스가 이미 누군가를 상속받았을 경우 다중상속이 불가능하므로 사용되지 않는다.
 따라서 인터페이스를 사용한다. 인터페이스는 클래스가 아닌 오직 추상 메서드와 상수만을 보유할 수 있기 때문에 다중상속 문제를 피할 수 있기 때문.
 */
public interface Controller {
	// 앞으로 이 인터페이스를 구현하는 모든 자식 객체가 반드시 아래의 메서드명을 구현한 것을 강제할 수 있으므로
	// 메서드 명을 통일할 수 있다는 장점이 있음
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	// 인터페이스는 {} 스코프로 몸체를 구현하면 안 된다. 자식마다 구현 내용이 다른데, 스코프를 만들어서 내용을 채울 이유도 없고 채울 수도 없다(문법오류).
	
	// 모든 하위 컨트롤러가 구현해야 하는 메서드 추가
	public String getViewName() ;	
	// 몸체를 만들지 않고 이렇게 추상 메서드로 자식 컨트롤러들이 역할에 맡게 완성하도록 만든다
	
	// 하위 컨트롤러가 jsp 까지 데이터를 살려서 유지할 일이 있을 경우 요청에 대한 응답을 하면 안 되며
	// 반드시 포워딩으로 처리가 되어야 한다.
	// 따라서 하위 컨트롤러는 DispatcherServlet 에게 해당 요청이 포워딩 대상인지 true인지 false인지 논리값을 반환하는 메서드를 제공해줘야 한다.
	public boolean isForward();
	 
}
