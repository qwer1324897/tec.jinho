package com.ch.mvcframework.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.ch.mvcframework.dto.Board;
import com.ch.mvcframework.mybatis.MyBatisConfig;

// Model
public class BoardDAO {
	MyBatisConfig myBatisConfig = MyBatisConfig.getInstance();
	
	// 글 한 건 등록
	public int insert(Board board) {
		
		int result = 0;
		
		SqlSession sqlSession = myBatisConfig.getSqlSession();
		result = sqlSession.insert("Board.insert", board);
		// SqlSession 은 디폴트로 autocommit 속성이 false 로 되어있기 때문에
		// commit 하지 않으면 insert 가 db 에 확정되지 않음.
		sqlSession.commit();	// 이 때 커밋은 DML 만을 대상으로 한다.
		
		myBatisConfig.release(sqlSession); 	// 메모리 반납
		
		return result;
	}
	
	// 모든 글 가져오기(select all)
	public List selectAll() {
		List list = null;
		SqlSession sqlSession = myBatisConfig.getSqlSession();
		list = sqlSession.selectList("Board.selectAll");
		// select 이므로 커밋 X
		myBatisConfig.release(sqlSession);
		return list;
	}
	
	
	public Board select(int board_id) {
		Board board = null;
		SqlSession sqlSession = myBatisConfig.getSqlSession();
		board = sqlSession.selectOne("Board.select", board_id);
		myBatisConfig.release(sqlSession);
		return board;
	}
}





