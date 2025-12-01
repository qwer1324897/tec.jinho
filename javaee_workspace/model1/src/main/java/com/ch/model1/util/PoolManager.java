package com.ch.model1.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

// DAO의 각 메서드마다 커넥션 풀로부터 Connection 을 얻어오는 코드를 중복 작성할 경우 유지보수성이 심각하게 떨어진다.
// JNDI 명칭이 바뀌거나, 연동할 db의 종류가 바뀌는 등, 외부에서 변화가 있을 때 코드에 영향이 적어야 한다.
// 따라서 커넥션 풀로부터 Connection 을 얻거나 반납하는 중복된 코드는, 아래의 클래스로 처리한다.

public class PoolManager {
	DataSource ds;
	public PoolManager() {
		try {
			InitialContext context = new InitialContext();
			ds = (DataSource)context.lookup("java:comp/env/jndi/mysql");
		} catch (NamingException e) {
			e.printStackTrace();
		}
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
	
	
}
