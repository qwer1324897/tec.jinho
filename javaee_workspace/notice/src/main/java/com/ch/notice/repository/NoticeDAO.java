package com.ch.notice.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ch.notice.domain.Notice;

/*
 * 이 클래스의 목적은?
 * JavaEE 기반이건 JavaSE 기반이건 애플리케이션 DB를 연동하는 비즈니스 로직은 동일하다. 
 * 따라서 유지보수성과 재사용성을 높이기 위해 여러 플랫폼에서 사용할 수 있는 객체를 정의하자.
 * 특히, 로직 객체중 오직 DB 연동만을 담당하는 객체를 가리켜 애플리케이션 설계 분야에서는
 * DAO (Data Access Object) DB에 테이블이 만일 5개라면 DAO로 1:1 대응하여 5개를 만들어야 한다.
 * 이런 DB의 테이블에 데이터를 처리하는 업무를 Create(=insert) Read(select) Update Delete
 * 
 * 아래와 같은 메서드에서, 매개변수의 수가 많아질 경우, 코드가 복잡해진다.
 * 따라서 java에서는 매개변수를 각각 낱개로 전달하는 것이 아니라 객체 안에 모두 넣어서 객체 자체를 전한다. 그걸 보통
 * DTO(Data Transfer Object)라 한다. DTO는 오직 데이터만을 보유한 객체다. 로직은 구현하지 않는다.
 */
public class NoticeDAO {
	// 게시물 등록
	public int regist(Notice notice) {
		
		int result = 0;	// insert 후 성공인지 실패인지를 판단할 수 있는 반환값
		
		Connection connection = null;	// 지역변수는 자동으로 초기화가 안 되기 때문에 반드시 초기화를 해야 사용이 가능하다.(문법오류남)
		PreparedStatement pstmt = null;
		
		// 드라이버 로드
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이버 로드 성공");
			
			// 접속
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/java", "servlet", "1234");
			System.out.println(connection);
			
			// 쿼리문 날리기
			String sql = "insert into notice(title, writer, content) values(?,?,?)";
			pstmt = connection.prepareStatement(sql);
			
			pstmt.setString(1, notice.getTitle());
			pstmt.setString(2, notice.getWriter());
			pstmt.setString(3, notice.getContent());
			
			result = pstmt.executeUpdate();	// DML 수행
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("접속 실패");
			e.printStackTrace();
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(connection!=null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}
}
