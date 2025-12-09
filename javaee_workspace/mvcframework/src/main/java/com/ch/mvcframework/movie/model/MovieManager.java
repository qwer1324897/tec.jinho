package com.ch.mvcframework.movie.model;

/*
 * Java SE 이든 Java EE 든 Java ME 모두 상관없이 재사용이 가능한 중립적 코드를 정의하기 위함
 */
public class MovieManager {

	// 아래는 웹, 스탠다드 등 모든 플랫폼에서 재사용 가능한 자바 객체. > Model 영역을 정의.
	public String getAdvice(String movie) {
		String msg = "선택된 영화가 없음";
		
		if(movie !=null) {		// 파라미터가 있을 때만
			if(movie.equals("귀멸의 칼날")) {
				msg = "귀멸의 칼날 원작 애니메이션 극장 시리즈 무한성편";
			} else if (movie.equals("체인소 맨")){
				msg = "체인소 맨 원작 애니메이션 극장 시리즈 레제편";
			}else if (movie.equals("나우유씨미2")){
				msg = "나우유씨미 시리즈 2편";
			}else if (movie.equals("주토피아2")){
				msg = "주토피아 시리즈 2편";
			}
		}
		return msg;
	}
}
