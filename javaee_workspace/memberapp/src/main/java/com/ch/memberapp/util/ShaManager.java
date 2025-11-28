package com.ch.memberapp.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// 평문의 비밀번호를 암호화 시켜 해시로 리턴하는 클래스. api 처럼 가져다 쓰자.
// Java의 암화화 처리는 javaEE, javaME 상관없이 javaSE 에서 지원한다.
public class ShaManager {
	
	// 메서드 호출 시 매개변수로 평문을 넘겨주면, 암호화 알고리즘을 사용하여 그 값을 반환하는 메서드
	// 사실 원리는 크게 중요하지 않다. 몰라도 됨.
	public static String getHash(String password) {
		// String password = "minzino";
		StringBuffer hexString = new StringBuffer();	// 최종적으로 암호화 결과를 모아놓을 객체
		
		try {
			
			MessageDigest dig = MessageDigest.getInstance("SHA-256");
			
			// 이 비밀번호 평문을 잘게 쪼개자
			// password.getBytes("utf-8");
			
			// 아래의 메서드를 수행하면, 아직 암호화되지 않은 배열로 존재하는 데이터를 암호화 시킨다.
			// 32 바이트의 문자열을 반환
			byte[] hash = dig.digest(password.getBytes("utf-8"));	// 매개변수로 바이트 배열을 넣어야 한다.
			
			
			for(int i =0; i<hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				
				if (hex.length()==1) {
					hexString.append("0");
				}
				hexString.append(hex);	 // 누적
			}
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return hexString.toString();
		
	}
}
	
//	public static void main(String[] args) {
//	String result = getHash();
//	getHash(); // static 메서드를 호출하는 main() 메서드가 같은 클래스에 존재하므로, 인스턴스 생성을 안 해도 됨.
//	}
//	



