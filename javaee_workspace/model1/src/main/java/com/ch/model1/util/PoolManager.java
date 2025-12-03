package com.ch.model1.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

// DAO의 각 메서드마다 커넥션 풀로부터 Connection 을 얻어오는 코드를 중복 작성할 경우 유지보수성이 심각하게 떨어진다.
// JNDI 명칭이 바뀌거나, 연동할 db의 종류가 바뀌는 등, 외부에서 변화가 있을 때 코드에 영향이 적어야 한다.
// 따라서 커넥션 풀로부터 Connection 을 얻거나 반납하는 중복된 코드는, 아래의 클래스로 처리한다.

public class PoolManager {
	
	private static PoolManager instance;	// instance 라는 변수명은 보통 싱글톤에 의해 인스턴스를 얻어갈 수 있다는 약속으로서 많이 쓰인다. 
	
	DataSource ds;
	
	// 외부에서 아무도 직접 new 못하게 막자.
	
	private PoolManager() {
		try {
			InitialContext context = new InitialContext();
			ds = (DataSource)context.lookup("java:comp/env/jndi/mysql");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public static PoolManager getInstance() {
		// 클래스 변수인 instance 변수에 아무것도 존재하지 않을 때는 아직 인스턴스가 없는 것이므로
		// 없을 때만 new로 생성하게 하자.
		// 이렇게 PoolManager 를 싱글톤으로 선언하면 java EE 개발에서 수많은 DAO 들이 PoolManger를 매번 생성해서 발생될 메모리 낭비를 방지할 수 있다.
		if (instance==null) {
			instance = new PoolManager();
		}
		return instance;
	}
	
	// 외부의 DAO 들이 직접 Connection 을 얻는 코드를 작성하게 하지 않으려면,
	// 이 PoolManager 클래스에서 DAO 대신 Connection 얻어와서 반환하자.
	
	public Connection getConnection() {
		Connection connection = null;
		try {
			connection = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	// 빌려간 커넥션만을 반납.
	public void closeConnection(Connection connection) {
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
	
	// 아래의 오버로딩 메서드는 DML에 사용. 빌려간 pstmt와 connection 반납
	public void closeConnection(PreparedStatement pstmt, Connection connection) {
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	// rs, pstmt, connection 셋 다 반납
	public void closeConnection(ResultSet rs,  PreparedStatement pstmt, Connection connection) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		if(connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		
	}	
	
}
