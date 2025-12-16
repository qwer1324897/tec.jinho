package com.ch.mvcframework.exception;

// Emp(사원)와 관련된 DB 처리 시 예외를 표현한 객체
// Java EE 에서는 개발자가 자신만의 예외를 커스텀하여 활용하는 프로그램 작성법을 알아야 함
// 예) DuplicatedMemberException, MemberRegistException 등

public class DeptException extends RuntimeException{
	
	// 자바에서 부모의 생성자는 상속받지 못하므로 RuntimeException 의 생성자 중 필요에 따라 적절한 생성자를 호출하여 사용
	
	// 에러 메세지만 생성
	public DeptException(String msg) {
		super(msg);
	}
	
	// 에러 메세지 + 에러 원인 생성
	public DeptException(String msg, Throwable e ) {
		super(msg, e);
	}
	
	// 에러 원인만 생성
	public DeptException(Throwable e ) {
		super(e);
	}	
}
