package com.ch.model1.dto;

import java.util.List;

import lombok.Data;

// News 테이블의 정보를 담기위한 DTO

@Data
public class News {
	private int news_id;
	private String title;
	private String writer;
	private String content;
	private String regdate;
	private int hit;
	private int cnt;		// mybatis 같은 프레임웤을 안 썼기 때문에 그냥 하드코딩
	// 하나의 뉴스 기사는 다수의 자식을 보유할 수 있다.
	private List<Comment> commentList;
}
