package com.ch.model1.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ch.model1.dto.Comment;
import com.ch.model1.dto.News;
import com.ch.model1.util.PoolManager;

// 오직 Comment 테이블에 대한 CRUD만을 수행하는 DAO 
public class CommentDAO {
	
	PoolManager pool = PoolManager.getInstance();
	

	// 댓글 등록
	public int insert(Comment comment) {
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		String sql = "insert into comment(news_id, reader, msg) values(?,?,?)";
		
		int result = 0;
		
		connection = pool.getConnection();
		
		try {
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, comment.getNews().getNews_id());		// 객체지향이므로 부모의 int 형이 아닌 객체 형태의 has a 관계로 보유하고 있음.
			pstmt.setString(2, comment.getReader());
			pstmt.setString(3, comment.getMsg());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.closeConnection(pstmt, connection);
		} 
		return result;
	}
	
	// 특정 뉴스기사에 딸려있는 댓글 모두 가져오기
	public List selectByNewsId(int news_id) {
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Comment> list = new ArrayList<Comment>();
		
		String sql = "select * from comment where news_id = ? order by comment_id desc";
		
		connection = pool.getConnection();
		
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, news_id);
			rs = pstmt.executeQuery();
			
			
			while (rs.next()) {
			
				// rs를 대신할 수 있는 데이터를 담는 용도의 DTO
				Comment comment = new Comment();
				comment.setComment_id(rs.getInt("comment_id"));
				comment.setMsg(rs.getString("msg"));
				comment.setReader(rs.getString("reader"));
				comment.setWritedate(rs.getString("writedate"));
				
				// 부모인 뉴스의 정보도 담기
				News news = new News();
				news.setNews_id(news_id);
				comment.setNews(news);	//  자식 DTO가 부모 DTO를 보유하게 만듬
				
				list.add(comment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.closeConnection(rs, pstmt, connection);
		}
		return list;
	}
	
}
