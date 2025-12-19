package com.ch.shop.controller.admin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ch.shop.dto.Color;
import com.ch.shop.dto.Product;
import com.ch.shop.dto.Size;
import com.ch.shop.model.topcategory.TopCategoryService;

import lombok.extern.slf4j.Slf4j;

// 쇼핑몰의 관리자에서 상품과 관련된 요청을 처리하는 하위 컨트롤러
@Controller
@Slf4j
public class ProductController {
	
	// 서비스 보유(느슨하게 보유)
	@Autowired
	private TopCategoryService topCategoryService;
	
	// 상품 등록 폼 요청 처리
	@GetMapping("/product/registform")
	public String getRegistForm(Model model) {

		// 3단계: 상품페이지에 출력할 상위카테고리 가져오기
		//List topList = topCategoryService.getList();
		
		// 4단계: 결과 저장	(스프링에서는 Model 객체를 사용하면 간접적으로 Request 저장이 됨)
		// jsp까지 topList 를 살려서 가야하므로, 포워딩 처리해야 한다. 스프링 개발자가 redirect 를 명시하지 않으면 디폴트가 포워딩
		//model.addAttribute("topList", topList);	// << 이게 request.setAttribute("topList", topList); 와 동일.
		
		return "admin/product/regist";
	}
	
	// 상품 등록 요청 처리(상품 자체)
	// 클라이언트가 전송한 데이터의 content-Type 이 mutipart/form-data 즉, 바이너리까지 포함될 경우
	// 기존의 HttpServletRequest 로 바로 받지 못한다. 따라서 개발자가 스트림을 직접 제어하거나 기존에 개발된 파일 업로드 컴포넌트를 이용해야 하는데
	// 자바 분야에서는 Aparch 에서 개발한 common fileupload 라이브러리를 많이 사용한다.
	// 따라서 스프링 프레임웤도 apache commons fileupload 를 내부적으로 사용한다.
	
	@PostMapping("/product/regist")	// admin은 빼고. 서블릿 매핑을 admin~ 으로 했기 때문.
	@ResponseBody 	// 이렇게 해야 JSON으로 변환
	public String regist(Product product, int[] color, int[] size) {
		// 매개변수로 지정된 객체와 html 문서의 폼에 지정된 파라미터명이 일치한다면, 자동 매핑이 이루어짐.
		// 파라미터 중 DTO 와 일치하지 않아 자동 매핑이 되지 않을 경우 개발자가 수동으로 직접 나서서 해결.
		
		log.debug("선택하신 하위 카테고리는 " + product.getSubCategory().getSubcategory_id());
		log.debug("상품명은 " + product.getProduct_name());
		log.debug("브랜드는 " + product.getBrand());
		log.debug("가격은 " + product.getPrice());
		log.debug("할인가는 " + product.getDiscount());
		
		// 색상에 대한 수동 처리
		List colorList = new ArrayList();
		for(int c : color) {
			log.debug("넘겨받은 색상은 " + c);
			Color dto = new Color();
			dto.setColor_id(c);
			colorList.add(dto);
		}
		
		// 사이즈에 대한 수동 처리
		List sizeList = new ArrayList();
		for(int s : size) {
			log.debug("넘겨받은 사이즈는 " + s);
			Size dto = new Size();
			dto.setSize_id(s);
			sizeList.add(dto);
		}
		product.setColorList(colorList);
		product.setSizeList(sizeList);
		
		log.debug("colorList 는" + product.getColorList());
		log.debug("sizeList 는" + product.getSizeList());
		
		log.debug("간단소개: " + product.getIntroduce());
		log.debug("상세설명: " + product.getDetail());
		
		// 이미지가 자동으로 채워졌는지 확인
		MultipartFile[] photo = product.getPhoto();
		for(MultipartFile p : photo) {
			log.debug("업로드 된 파일명은 " + p.getOriginalFilename());
			
			// 메모리의 임시파일을 실제 원하는 하드경로에 저장하기(하드코딩 방법)
			try {
				p.transferTo(new File("C:/shopdata/product/" + p.getOriginalFilename()));
				log.debug("저장성공!");
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		

		return "ok";
	}
	
	
}
