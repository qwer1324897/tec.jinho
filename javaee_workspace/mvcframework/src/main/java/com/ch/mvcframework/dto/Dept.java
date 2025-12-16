package com.ch.mvcframework.dto;

import java.util.List;

import lombok.Data;

@Data
public class Dept {
	private int deptno;
	private String dname;
	private String loc;
	// 여러명의 사원은 하나의 부서에 소속
	private List<Emp> list;
}
