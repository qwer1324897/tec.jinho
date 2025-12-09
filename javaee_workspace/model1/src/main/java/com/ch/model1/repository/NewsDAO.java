package com.ch.model1.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ch.model1.dto.News;
import com.ch.model1.util.PoolManager;

// News 테이블에 대한 CRUD 만을 수행하는 DAO
public class NewsDAO {
	
	PoolManager pool = PoolManager.getInstance();
	
	public int insert(News news) {
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		String sql = "insert into news(title,writer,content) values(?,?,?)";
		
		int result = 0;
		
		connection = pool.getConnection();
		
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, news.getTitle());
			pstmt.setString(2, news.getWriter());
			pstmt.setString(3, news.getContent());
			
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
		
		List<News> list = new ArrayList<News>();
		
		Connection connection = pool.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		StringBuffer sb = new StringBuffer();
		sb.append("select n.news_id, n.title, n.writer, n.regdate, n.hit, Count(c.comment_id) as cnt");
		sb.append(" from news n");
		sb.append(" left join comment c on n.news_id = c.news_id");
		sb.append(" group by n.news_id, n.title, n.writer, n.regdate, n.hit");
		sb.append(" order by n.news_id desc");
		
		System.out.println(sb.toString());
		
		try {
			pstmt = connection.prepareStatement(sb.toString());
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				News news = new News();
				news.setNews_id(rs.getInt("n.news_id"));
				news.setTitle(rs.getString("n.title"));
				news.setWriter(rs.getString("n.writer"));
				news.setRegdate(rs.getString("n.regdate"));
				news.setHit(rs.getInt("n.hit"));
				news.setCnt(rs.getInt("cnt"));
				
				list.add(news);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.closeConnection(rs, pstmt, connection);
		}	
		return list;
	}
	
	public News select(int news_id) {
		
		Connection connection = pool.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		News news = null;
		
		String sql = "select * from news where news_id=?";
		
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, news_id);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {	
				news = new News();
				news.setNews_id(rs.getInt("news_id"));	
				news.setTitle(rs.getString("title")); 
				news.setWriter(rs.getString("writer")); 		
				news.setContent(rs.getString("content"));
				news.setRegdate(rs.getString("regdate"));
				news.setHit(rs.getInt("hit"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.closeConnection(rs, pstmt, connection);
		}
		return news;
	}
	
	// Update
		// 레코드 1 건 수정
		public int update(News news) {
			
			Connection connection = null;
			PreparedStatement pstmt = null;
			int result = 0;	// 쿼리 실행 결과를 반환할 지역변수. 지역변수이다 보니 개발자가 직접 초기화 해야 한다.
			
			connection = pool.getConnection();
			String sql = "update news set title=?, writer=?, content=? where news_id=?";
		
			try {
				pstmt=connection.prepareStatement(sql);
				pstmt.setString(1, news.getTitle());
				pstmt.setString(2, news.getWriter());
				pstmt.setString(3, news.getContent());
				pstmt.setInt(4, news.getNews_id());
				
				result = pstmt.executeUpdate();
				
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				pool.closeConnection(pstmt, connection);
			}
			return result;
		}

		// Delete
		
		public int delete(News news) {
			
			Connection connection = null;
			PreparedStatement pstmt = null;
			int result = 0;

			connection = pool.getConnection();
			String sql = "delete from news where news_id=" +news.getNews_id();
			try {
				pstmt = connection.prepareStatement(sql);
				result = pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				pool.closeConnection(pstmt, connection);
			}
			return result;
		}
	
}