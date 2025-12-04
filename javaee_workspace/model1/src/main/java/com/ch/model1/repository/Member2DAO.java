package com.ch.model1.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ch.model1.dto.Member2;
import com.ch.model1.util.PoolManager;

// 이 클래스는 오직 DB 관련 로직만 담당하는 DAO 클래스

public class Member2DAO {
	
	PoolManager pool = PoolManager.getInstance();
	
	// insert - 레코드 1 건씩만 가능
	public int insert(Member2 member2) {
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		String sql = "insert into member2(id,name,email) values(?,?,?)";
		
		int result = 0;	// dml 수행 후 결과를 받아놓을 변수

		connection = pool.getConnection();
		
		try {
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1,member2.getId());
			pstmt.setString(2, member2.getName());
			pstmt.setString(3, member2.getEmail());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.closeConnection(pstmt, connection);
		}
		return result;
	}
	
	// 모든 레코드 가져오기
	public List selectAll() {
		Connection connection = pool.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		List<Member2> list = new ArrayList<Member2>();	// 모든 게시물을 모아놓을 리스트. 여기에 들어갈 객체는 DTO 인스턴스들이 들어간다.
		
		try {
			String sql = "select * from member2";
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery(); // select 문의 반환값은 ResultSet 이다.   
			
			while(rs.next()) {		// 커서를 이동하면서 true 인 동안만(= 레코드가 있는 만큼만)
				
				Member2 member2 = new Member2();		// 게시물 한 건을 담을 수 있는 Member DTO 클래스의 인스턴스 1개 준비.(현재 비어있음)
				member2.setMember2_id(rs.getInt("member2_id"));	// id 담기
				member2.setId(rs.getString("id"));
				member2.setName(rs.getString("name"));
				member2.setEmail(rs.getString("email"));
				
				list.add(member2);		// List 에 인스턴스 1개 추가
			}
		} catch (SQLException e) {                                                                                                                       
			e.printStackTrace();
		} finally {
			pool.closeConnection(rs, pstmt, connection);
		}
		return list;	
	}
}