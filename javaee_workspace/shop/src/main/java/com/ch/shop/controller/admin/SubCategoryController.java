package com.ch.shop.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.ch.shop.model.subcategory.SubCategoryService;

import lombok.extern.slf4j.Slf4j;

// 쇼핑몰의 하위 카테고리에 대한 요청을 처리하는 하위 컨트롤러
@Controller
@Slf4j
public class SubCategoryController {
	
	@Autowired
	private SubCategoryService subCategoryService;
	
	// 목록 요청 처리
	// 주의) 클라이언트가 비동기 요청을 시도할 경우, 서버는 절대 Html 문서를 원하는 것이 아니므로
	// 데이터를 보내줘야 한다. 개발 프로그래밍에서 표준적으로 많이 사용하는 형식이 JSON 문자열이므로 JSON 사용
	@GetMapping("/admin/subcategory/list")
	public void getList(int topcategory_id) {
		
		List subList = subCategoryService.getList(topcategory_id);
		
		log.debug("하위 카테고리는 " + subList);
		
		// 자바 객체를 JSON 문자열로 바꿔주는 API 인 Jackson 을 이용하여 자바 객체를 JSON 형태로 전송.
		
	}
	
}


