package com.ch.mybatisapp.dto.repository;

import org.apache.ibatis.session.SqlSession;

import com.ch.mybatisapp.dto.Board;
import com.ch.mybatisapp.mybatis.MyBatisConfig;

public class BoardDAO {
	MyBatisConfig myBatisConfig = MyBatisConfig.getInstance();		// SqlSessionFactory 가 들어있는 싱글턴 객체
	
	// 글쓰기
	public int insert(Board board) {
		int result = 0;
		// 이제 상투적 JDBC code 대신, Mybatis 에게 맡긴다.
		SqlSession sqlSession = myBatisConfig.getSqlSession();
		sqlSession.insert("com.ch.mybatisapp.mybatis.dto.Board.insert", board);
		// DML은 트랜잭션을 확정지어야 한다
		sqlSession.commit();
		myBatisConfig.release(sqlSession);
		return result;
	} 
}
