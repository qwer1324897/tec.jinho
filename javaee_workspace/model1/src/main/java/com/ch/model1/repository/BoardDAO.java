package com.ch.model1.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.ch.model1.dto.Board;
import com.ch.model1.util.PoolManager;

// 데이터베이스의 Board table 에 대한 CRUD를 수행하는 객체
public class BoardDAO {
	
	PoolManager pool = new PoolManager();
	
	// Create(=insert)
	// 글 1건을 등록하는 메서드
	public int insert(Board board) {	// 개발 시 파라미터의 수가 많을 땐 낱개로 처리하지 않음.
														// 특히, DB 연동 로직에서는 DTO를 이용한다.
		
		// 이 메서드를 호출할 때마다 접속을 일으키는 것이 아니라, Tomcat 이 미리 모아놓은 Connection 들이 있는
		// Connection Pool 로부터 대여하자. 또한 쿼리문 수행이 완료되더라도 얻어온 Connection 은 절대 닫지 말아야 한다.(열어놓은걸 왜 닫아)
		Connection connection = null;
		PreparedStatement pstmt = null;
		int result = 0;		// return 할 예정이므로 try문 밖에다가 선언해야 한다.
		
		try {
			InitialContext context = new InitialContext();
			DataSource pool = (DataSource)context.lookup("java:comp/env/jndi/mysql");
			connection = pool.getConnection();
			
			// 쿼리 수행
			String sql = "insert into board(title, writer, content) values(?,?,?)";
			pstmt = connection.prepareStatement(sql);
			
			pstmt.setString(1,board.getTitle());
			pstmt.setString(2, board.getRegdate());
			pstmt.setString(3, board.getContent());
			
			result = pstmt.executeUpdate();
			
			/* 아래의 코드를 작성하면 안되는 이유.
			 * out은 JavaEE 니까 BoardDAO(standard)의 중립성이 사라진다.
			 * DAO는 디자인 영역과 분리되어 DB만 담당하므로, 디자인 코드를 넣어선 안 된다.
			 * 따라서 디자인 처리는 이 메서드를 호출할 때 처리하도록 하고, 여기선 결과만 반환한다.
			if (result < 1) {
				out.print("실패")
			} else {
				out.print("성공")
			}
			*/
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null ) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(connection != null) {
				try {
					// 주의) 기존 JDBC 코드는 다 사용한 커넥션들은 닫았지만, Pool 로부터 얻어온 커넥션은 닫으면 안 됨.
					connection.close();
					// 이 객체는 DataSource 구현체로부터 얻어온 Connection 이기 때문에 일반적 JDBC 의 닫는 close() 가 아니다 
				} catch (SQLException e) {
					e.printStackTrace();
				}	
			}
		}
		return result;
	}
	
	
	// Read(=select)
	
	public List selectAll() {
		// 커넥션 얻는 코드를 이 메서드에서 직접 하지 않는다. PoolManager 가 대신 해주기 때문에
		Connection connection = pool.getConnection();
		// 여기서 직접 검색하면 jndi 검색 코드가 중복되므로 풀 매니저로 부터 커낵션 객체를 얻어온다. 
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Board> list = new ArrayList<Board>();	// 모든 게시물을 모아놓을 리스트. 여기에 들어갈 객체는 DTO 인스턴스들이 들어간다.
		
		try {
			String sql = "select * from board";
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery(); // select 문의 반환값은 ResultSet 이다.   
			
			// rs 는 무조건 이 메서드에서 닫아야 하는데, jsp 는 디자인을 담당하므로 ResultSet의 존재를 알 필요가 없다
			// 또한 ResultSet은 DB연동 기술이므로 DAO 에서 제어해야 한다. 따라서 여기 finally 에서 rs를 닫는 게 맞다.
			// 그러나, rs 를 여기서 닫으면 외부 객체에서 rs 가 closed 됐으니까 사용할 수 없음.
			// 따라서 rs 가 죽어도 상관없는 비슷한 유형의 객체로 데이터를 표현한다.
			// 이러한 객체들의 조건으로서 사물을 표현할 수 있어야 하는데(ex. 게시물 1건을 담을 수 있는 존재 - board DTO)
			// 이러한 Board DTO 로부터 생성된 게시물을 표현한 인스턴스들을 모아놓을 객체가 필요하다.(순서 O, 객체를 담을 수 있어야 함)
			// 이러한 조건을 만족하는 객체는 java에서 많이 사용했던 List 가 있다.
			
			while(rs.next()) {		// 커서를 이동하면서 true 인 동안만(= 모든 레코드 만큼)
				
				Board board = new Board();		// 게시물 한 건을 담을 수 있는 Board DTO 클래스의 인스턴스 1개 준비.(현재 비어있음)
				board.setBoard_id(rs.getInt("board_id")); 		// pk 담기
				board.setTitle(rs.getString("title")); 	// 제목 담기
				board.setWriter(rs.getString("writer")); 		// 작성자 담기
				board.setRegdate(rs.getString("regdate"));
				board.setHit(rs.getInt("hit"));
				
				list.add(board);		// List 에 인스턴스 1개 추가
			}
		} catch (SQLException e) {                                                                                                                       
			e.printStackTrace();
		}
		return list;	// rs를 대체하는 객체지향 형태로 변환
	}
	
	
	// Update
	
	// Delete
	
}
