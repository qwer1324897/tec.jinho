package com.ch.memberapp.member;

// 한 명의 회원을 표현한 객체. 설계 분야에서는 이러한 용도의 객체를 DTO(Data Trasfer Object)라 한다.
// 로직이 아닌, data 저장만을 위한 용도.

// 자바에서는 아래와 같이 클래스를 정의하면서 멤버변수를 그대로 노출시키지 않는다.
// Encasulation(은닉화). 객체안의 데이터를 보호하고, 그 데이터를 제어하는 방법에 대해서는 메서드를 통해 객체를 제어하는 클래스 정의 기법
// public < protected(같은 패키지, 상속관계) < default(같은 패키지 안에서만) < private(아무도 접근 못함)
public class Member {
	private int member_id;
	private String id;
	private String pwd;
	private String name;
	private String regdate;
	
	public int getMember_id() {
		return member_id;
	}
	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}	
}