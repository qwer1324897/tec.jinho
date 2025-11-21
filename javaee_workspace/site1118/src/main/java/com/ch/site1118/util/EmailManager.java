package com.ch.site1118.util;


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

// 이메일을 발송해주는 객체 정의
// javase 기반에서 이미 메일 발송 api가 jar 형태의 라이브러리로 지원된다.
// activation.1.1.1.jar, javax.mail.1.5.0.jar

public class EmailManager {
	String host="smtp.gmail.com";	// 사용하고자 하는 메일 서버 주소
	String user="rlaxogh229@gmail.com";	// 메일 서버의 사용자 계정
	String password="kfbq fvnn kcbi mjle";	// 앱 비밀번호
	Properties props = new Properties();	// 부모인 java.util.map 의 자식이다. key-value 쌍을 갖는 데이터 형식.
	
	// 메일 발송 메서드
	// to 매개변수 - 메일 받을 회원가입 하는 클라이언트
	public void send(String to) {
		// props 객체에 필요한 모든 설정 정보의 쌍을 대입한다.
		// key 값은 이미 정해져 있는 값이다.
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", 465);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");
		
		// Session 생성 javax.mail
		Session session = Session.getDefaultInstance(props, new Authenticator() {
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(user, password);
				};
		}); 
			
		// 제목, 내용 등의 메일 작성
		MimeMessage message = new MimeMessage(session);
		
		try {
			message.setFrom(new InternetAddress(user));   // 메일 발송자
			message.addRecipient(Message.RecipientType.TO , new InternetAddress(to));// 메일 받을 사람
			message.setSubject("회원가입을 축하드립니다!");
			message.setContent("<h1>회원가입 감사 메일</h1>회원가입에 감사드립니다." ,"text/html;charset=utf-8");
			
			Transport.send(message);		// 메일 발송
			
			System.out.println("이메일 발송 성공");
		} catch (Exception e) {
			System.out.println("이메일 발송 실패");
			e.printStackTrace();
		}		
	}
}






