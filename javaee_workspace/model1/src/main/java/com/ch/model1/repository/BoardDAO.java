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
			pstmt.setString(2, board.getWriter());
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
			pool.closeConnection(pstmt, connection);
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
				board.setBoard_id(rs.getInt("board_id")); 		// id 담기
				board.setTitle(rs.getString("title")); 	// 제목 담기
				board.setWriter(rs.getString("writer")); 		// 작성자 담기
				board.setContent(rs.getString("content"));
				board.setRegdate(rs.getString("regdate"));
				board.setHit(rs.getInt("hit"));
				
				list.add(board);		// List 에 인스턴스 1개 추가
			}
		} catch (SQLException e) {                                                                                                                       
			e.printStackTrace();
		} finally {
			// con, pstmt, rs 를 대신 닫아주는 메서드 호출
			pool.closeConnection(rs, pstmt, connection);
		}
		return list;	// rs를 대체하는 객체지향 형태로 변환
	}
	
	// 레코드 한 건 가져오기
	public Board select(int board_id) {
		// 쿼리 실행을 하기 위한 db 접속은 현재 코드에서 시도하지 말고, Connection Pool 로부터 가져오자
		Connection connection = pool.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Board board = null;
		
		try {
			String sql = "select * from board where board_id=?";
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, board_id);
			rs=pstmt.executeQuery();	// select 문 실행
			
			// rs 가 죽어도 상관없으려면, 게시물 1 건을 표현할 수 있는 대체제를 사용해야 한다.
			// DB의 레코드 1 건은 java의 DTO 인스턴스 1개와 매핑
			
			if (rs.next()) {	// next()가 true 인 경우, 즉 쿼리 실행에 의해 조건에 맞는 레코드가 존재할 때만 DTO를 반환하자.
				board = new Board();	// 현재 비어있는 상태
				board.setBoard_id(rs.getInt("board_id")); 		// id 담기
				board.setTitle(rs.getString("title")); 	// 제목 담기
				board.setWriter(rs.getString("writer")); 		// 작성자 담기
				board.setContent(rs.getString("content"));
				board.setRegdate(rs.getString("regdate"));
				board.setHit(rs.getInt("hit"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.closeConnection(rs, pstmt, connection);
		}
		return board;
	}
	
	// Update
	// 레코드 1 건 수정
	public int update(Board board) {
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		int result = 0;	// 쿼리 실행 결과를 반환할 지역변수. 지역변수이다 보니 개발자가 직접 초기화 해야 한다.
		
		connection = pool.getConnection();
		String sql = "update board set title=?, writer=?, content=? where board_id=?";
	
		try {
			pstmt=connection.prepareStatement(sql);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getWriter());
			pstmt.setString(3, board.getContent());
			pstmt.setInt(4, board.getBoard_id());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			pool.closeConnection(pstmt, connection);
		}
		return result;
	}

	// Delete
	
	public int delete(Board board) {
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		int result = 0;

		connection = pool.getConnection();
		String sql = "delete from board where board_id=" +board.getBoard_id();
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