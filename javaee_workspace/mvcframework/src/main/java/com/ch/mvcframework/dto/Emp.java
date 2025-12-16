package com.ch.mvcframework.dto;

import lombok.Data;

@Data
public class Emp {
	private int empno;
	private String ename;
	private int sal;
	// 한 명의 사원은 특정 부서에 소속이 되어야 한다
	private Dept dept;
}
