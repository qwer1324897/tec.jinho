package com.ch.model1.dto;

/*
 * 아래의 클래스는 로직이 아니라 오직 Member2 라는 테이블과의 CRUD에 사용하기 위한 데이터를 담기 위한 목적. = DTO
 * 자바 용어가 아니라 대부분의 app 제작 시 데이터를 담는 목적의 객체를 가리키는 범용적 용어.
 */
public class Member2 {
	
	private int member2_id;
	private String id;
	private String name;
	private String email;

	public int getMember2_id() {
		return member2_id;
	}
	public void setMember2_id(int member2_id) {
		this.member2_id = member2_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
