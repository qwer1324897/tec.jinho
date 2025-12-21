package com.ch.mvcframework.emp.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ch.mvcframework.controller.Controller;
import com.ch.mvcframework.dto.Dept;
import com.ch.mvcframework.dto.Emp;
import com.ch.mvcframework.emp.model.EmpService;

/*
 사원 등록 요청을 처리하는 하위 컨트롤러
 3단계: 일 시키기
 4단계: 지금의 경우 DML 이므로 저장할 것이 없으므로 생략.
 */
public class RegistController implements Controller{
	
	private EmpService empService = new EmpService();
	private String viewName;
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 부서관련 정보 > Dept2 에 등록
		String deptno = request.getParameter("deptno");
		String dname = request.getParameter("dname");
		String loc = request.getParameter("loc");
		
		Dept dept = new Dept();
		dept.setDeptno(Integer.parseInt(deptno));
		dept.setDname(dname);
		dept.setLoc(loc);
		
		// 사원관련 정보 > Emp2 에 등록
		String empno = request.getParameter("empno");
		String ename = request.getParameter("ename");
		String sal = request.getParameter("sal");
		
		Emp emp = new Emp();
		emp.setEmpno(Integer.parseInt(empno));
		emp.setEname(ename);
		emp.setSal(Integer.parseInt(sal));
		
		// Emp가 Dept를 has a 관계로 보유하고 있으므로 따로따로 전달하지 말고 모아서 전달가능.
		emp.setDept(dept);
		
		// 모델 영역에 일 시키기(일 하는 구체적인 코드 작성을 하면 안 된다. 역할 분리없이 코드가 혼재되며 유지보수성이 떨어진다)
		try {
			empService.regist(emp);
			viewName = "/emp/regist/result";
		} catch (Exception e) {
			viewName = "/emp/error";
		}
		// regist() 메서드에 throws 가 처리되어 있으므로 컴파일 에러가 나지 않는데, 이는 runtimeexception 이라 그런 것이고
		// 개발자는 항상 예외처리를 해야 한다.
	}

	@Override
	public String getViewName() {
		
		return viewName;
	}

	@Override
	public boolean isForward() {
		
		return false;
	}

}
