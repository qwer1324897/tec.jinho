package com.ch.mvcframework.repository;

import org.apache.ibatis.session.SqlSession;

import com.ch.mvcframework.dto.Emp;
import com.ch.mvcframework.exception.EmpException;
import com.ch.mvcframework.mybatis.MyBatisConfig;

public class EmpDAO {
	MyBatisConfig myBatisConfig = MyBatisConfig.getInstance();
	
	// 1명 등록
	// throws 가 명시된 메서드를 호출한 사람은 throws 에 명시된 예외를 처리해야 함.
	public void insert(SqlSession sqlSession ,Emp emp) throws EmpException{
		try {
			sqlSession.insert("Emp.insert", emp);
		} catch (Exception e) {
			e.printStackTrace();
			throw new EmpException("사원 등록 실패", e);
		}
	}
}
