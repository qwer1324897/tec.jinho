package com.ch.mvcframework.mybatis;

import java.io.IOException;
import java.io.InputStream;

// ibatis는 mybatis의 이전 명칭
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

// mybatis 설정 파일(xml)은 프로그래밍 언어가 아닌 단순한 리소스이므로, 이 리소스를 읽어 해석할 객체가 필요함. 
public class MyBatisConfig {
	private static MyBatisConfig instance;
	
	private SqlSessionFactory sqlSessionFactory;
	
	private MyBatisConfig() {
		try {
			String resource = "com/ch/mvcframework/mybatis/config.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			
			// Mybatis 를 이용하면 개발자는 더 이상 JDBC를 직접 사용하여 DB 연동 코드를 작성할 필요가 없다.
			// 이 때, 개발자가 쿼리문을 수행하기 위해선 Mybatis 가 제공해주는 SqlSession 객체를 이용해야 하는데
			// 이 SqlSession 객체는 SqlSessionFactory 로부터 얻고, 사용이 끝난 후 그냥 close 로 닫으면 된다.
			
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static MyBatisConfig getInstance() {
		if(instance == null ) {
			instance = new MyBatisConfig();
		}
		return instance;
	}
	
	// 팩토리로부터 쿼리문 실행에 필요한 SqlSession 객체를 가져갈 수 있도록 메서드를 정의
	// 외부의 객체는 이 메서드 호출만으로 팩토리로부터 SqlSession 을 얻어갈 수 있다.
	// 이 때 SqlSession 은 이미 접속 정보를 가지고 있으며 쿼리문도 실핼할 수 있는 객체이므로
	// 자바 개발자는 기존의 JDBC 코드에서 Connection, PrepareStatement 를 직접 다루었던 비효율적 코드를 다루지 않는다.
	public SqlSession getSqlSession() {
		return sqlSessionFactory.openSession();
	}
	
	// SqlSession 은 쿼리문 수행 후 닫아야 하므로, 아래의 메서드로 닫아주는 기능 구현.
	public void release(SqlSession sqlSession) {
		if (sqlSession != null) {
			sqlSession.close();
		}
	}
}
