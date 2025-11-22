package com.ch.gallery.util;

// 문자열 처리와 관련된 자주 사용 되는 기능들을 유틸 클래스로 모아놨음.

public class StringUtil {
	
	// 주어진 파일 경로에서 확장자만을 추출하는 메서드
	public static String getExtendFrom(String oriName) {
		
		int lastIndex = oriName.lastIndexOf(".");	// 가장 마지막 점의 위치를 반환
		String extend = oriName.substring(lastIndex+1, oriName.length());
		
		return extend;
	}
}
