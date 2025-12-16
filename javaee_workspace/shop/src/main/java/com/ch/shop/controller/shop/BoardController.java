package com.ch.shop.controller.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ch.shop.dto.Board;

// 기존에 자체 제작한 MVC 프레임웤에선 모든 요청마다 일대일 대응하는 컨트롤러를 매핑했었지만, 
// 스프링 MVC 는 가령 게시판 1개에 대한 목록, 쓰기, 상세보기, 수정, 삭제 등에 대해 하나의 컨트롤러로 처리가 가능하다.
// 왜 Why? 스프링 MVC 는 모든 요청마다 1:1 대응하는 클래스 기반이 아닌, 메서드 기반이기 때문.

@Controller
public class BoardController {
	
	// 글쓰기 폼 요청 처리
	// (현재 jsp가 WEB-INF 밑의 위치에 놓았는데, 보안상의 이유로 컨트롤러만 접근 가능하게 하려고 일부로 이렇게 놓았다.)
	// 근데 문제는 이렇게 하면 브라우저에서 직접 jsp에 접근이 불가능하므로, 이 컨트롤러의 메서드에서 /board/writer.jsp 를 매핑한다.
	@RequestMapping("/board/registform")
	public ModelAndView registForm() {
		// 3단계: 글쓰기 폼을 보여주는 거니까 DB와 연관이 없으므로 3단계에서 할 게 없다
		// 4단계: 마찬가지로 없다. 단순 글쓰기 폼 요청인데 할게 없음 진짜로 그냥 만들어놓은 jsp만 주면 된다.
		// 단, 이 때 "/WEB-INF/views/board/write.jsp"    이렇게 직접 모든 키값을 주는 게 아니라, 
		// "board/write" 이렇게 접두어와 접미사 부분을 빼고 핵심부분만(개발자가 정한대로) 전달한다. 왜 why? 완전한 경로를 반환하면, 파일명이 바뀔 때 유지보수성이 떨어지므로
		// 따라서 개발자는 전체 파일 경로 중, 변하지 않는다고 생각하는 부분을 제외한 접두어, 접미어를 제외하고 핵심 알맹이라고 생각하는 부분을 전달.
		// 이 때, 하위 컨트롤러가 DispatcherServlet 에게 정보를 반환할 때는 String("") 형태도 가능하지만, ModelAndView 라는 객체를 이용할 수 있다.
		// ModelAndView 에 데이터를 담을 때에는 Model 객체에 자동으로 담기고, 접두어 접미어를 제외한 핵심 알맹이 문자열을 넣어둘 때는 View 객체에 담기는데,
		// ModelAndView 는 이 두 객체를 합쳐놓은 것.
		ModelAndView mav = new ModelAndView();
		mav.setViewName("board/write");	// 이렇게 알맹이라고 생각되는 부분만 View 로 세팅해서 전달.
		return mav;		// 
	}

	// 글 목록 페이지 요청 처리
	@RequestMapping("/board/list")
	public ModelAndView getList() {
		// 3단계 수행(DB에서 필요한 정보 처리)
		
		// 4단계: 결과 저장
		
		
		return null;
	}
	
	// 글쓰기 요청 처리
	// 메서드의 매개변수에 VO(Value Object)로 받을 경우 스프링에서 자체적으로 자동 매핑에 의해 파라미터값들을 채워 넣는다.
	// 단, 파라미터명과 VO의 변수명이 반드시 일치해야 한다.
	// DTO와 VO는 비슷하기는 하지만 DTO 는 테이블을 반영한 객체이다 보니 클라이언트에 노출되지 않도록 하는 것이 좋기 때문에, 
	// 단순히 클라이언트의 파라미터를 받는 것이 목적이라면, DTO 보다는 VO를 사용한다.
	@RequestMapping("/board/regist")
	public ModelAndView regist(Board board) {
		ModelAndView mav = new ModelAndView();
		System.out.println("제목은: " + board.getTitle());
		System.out.println("작성자는: " + board.getWriter());
		System.out.println("내용은: " + board.getContent());
		
		
		
		return mav;
	}
	
	// 글 상세보기 요청 처리
	
	
	// 글 수정 요청 처리
	
	
	// 글 삭제 요청 처리
}
