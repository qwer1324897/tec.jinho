package com.ch.mvcframework.repository;

import org.apache.ibatis.session.SqlSession;

import com.ch.mvcframework.dto.Dept;
import com.ch.mvcframework.exception.DeptException;
import com.ch.mvcframework.exception.EmpException;
import com.ch.mvcframework.mybatis.MyBatisConfig;

public class DeptDAO {
	MyBatisConfig myBatisConfig = MyBatisConfig.getInstance();
	
	// 1건 등록
	public void insert(SqlSession sqlSession ,Dept dept) throws DeptException{
		try {
			sqlSession.insert("Dept.insert", dept);
		} catch (Exception e) {
			throw new DeptException("부서 등록 실패", e);
		}
	}
}