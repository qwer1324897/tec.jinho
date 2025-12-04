package com.ch.model1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

// 전국 100대 산 관광정보 오픈 api 의 응답결과 중 가장 안 쪽에 들어 있는 item 의 정보를 담기 위한 DTO

// Lombok 을 이용하여 게터세터 생성

@Data
@JsonIgnoreProperties(ignoreUnknown = true)		// JSON 문자열과 자바 객체간의 매핑 시, 자바 객체가 보유하지 않은 속성은 그냥 무시해. 라는 뜻.
																			// 지금 공공기관에서 받은 api 데이터 전체를 넣은 게 아니라 연습용으로 일부만 dto에 넣었기 때문.
																			// 이걸 안 넣으면 모든 데이터랑 dto를 비교하려고 해서 dto에 모든 데이터가 없어서 오류가 난다.
public class Item {
	private String placeNm;
	private double lat;	// 위도
	private double lot;	// 경도
	private String frtrlNm;	// 숲길명
}
