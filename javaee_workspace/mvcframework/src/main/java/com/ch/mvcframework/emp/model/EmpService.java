package com.ch.mvcframework.emp.model;

import org.apache.ibatis.session.SqlSession;

import com.ch.mvcframework.dto.Dept;
import com.ch.mvcframework.dto.Emp;
import com.ch.mvcframework.exception.EmpException;
import com.ch.mvcframework.mybatis.MyBatisConfig;
import com.ch.mvcframework.repository.DeptDAO;
import com.ch.mvcframework.repository.EmpDAO;

/*
애플리케이션의 영역 중 서비스를 정의.
Service 를 따로 정의하는 이유. 따로 정의하지 않는다면 Controller 에 DAO 들에게 일을 시키는 코드가 많아짐 > MVC 원칙 위배
따라서 Controller 를 DAO들과 분리시키고 트랜잭션을 대신 처리할 객체가 필요함.
 
 */
public class EmpService {
	
	MyBatisConfig myBatisConfig = MyBatisConfig.getInstance();
	// 각 DAO 들의 트랜잭션이 제대로 되기 위해선, 각 DAO 마다 따로 분리된 상태에서 하는 게 아니라
	// 하나로 묶인 상태에서(이 컨트롤러에서) MybatisConfig 로 sqlSession을 취득하고 이후 insert 를 호출할 때 분리시켜서 나눠줘야 한다. 

	DeptDAO deptDAO = new DeptDAO();
	EmpDAO empDAO = new EmpDAO();

	// 한 명의 사원이 입사하면 부서와 사원을 동시에 등록하는 메서드
	public void regist(Emp emp) throws EmpException{
		SqlSession sqlSession = myBatisConfig.getSqlSession();
		// mybatis 는 default 가 autocommit == false 로 되어 있으므로 개발자가 별도로 트랜잭션 시작을 알릴 필요 없다.
		// 즉 원할 때 커밋 하면 됨.
		try {
			deptDAO.insert(sqlSession, emp.getDept());
			empDAO.insert(sqlSession ,emp);
			sqlSession.commit();	// 트랜잭션 확정
		} catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback(); 	// 둘 중 하나라도 문제 생기면 롤백. > DB 등록 취소.
			
			// 아래의 throw 코드에 의래 에러가 별생. 따라서
			// 1) 예러를 여기서 try catch로 잡던지
			// 2)  throw 할지
			throw new EmpException("사원 등록 삭제", e);
		} finally {
			myBatisConfig.release(sqlSession);
		}

	}
}
