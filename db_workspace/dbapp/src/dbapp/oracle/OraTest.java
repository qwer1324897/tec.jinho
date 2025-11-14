package dbapp.oracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OraTest {
	
	// java 언어로 오라클 서버에 접속하여, insert 문을 실행해보자.
	public static void main(String[] args) {
		
		// 먼저 오라클 서버에 접속을 시도한다. 기존 cmd에서 SQLPlus java/1234 로 접속하는 행위를
		// 자바 언어로 진행한다.
		// javaSE에는 기본적으로 오라클, mysql, postgresql, redis, mongodb 등을
		// 제어할 수 있는 클래스가 기본으로 포함 되어 있지 않다.
		// 따라서 드라이버(jar)를 이용해야 한다. 참고로 각 DB 제품마다
		// DB를 제작한 벤더가 드라이버를 제공할 의무가 있다.
		
		// 오라클용 드라이버 메모리에 로드
		// 아래와 같이 forName 메서드를 이용하면, 동적으로 클래스가 메서드 영역(static) 으로 올라간다.
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("드라이버 로드 성공");
			
			// 오라클에 접속.
			String url="jdbc:oracle:thin:@localhost:1521:XE";
			String id="java";
			String pwd="1234";
			
			// 접속이 성공적으로 되었는지 확인하려면, 접속 성공 후 반환되는 Connection
			// 인터페이스가 메모리에 올라왔는지 체크하면 된다.
			Connection con = DriverManager.getConnection(url,id,pwd);
			// 이클립스에서는 ctrl shift o 하면 임포트가 된다.
			
			if (con != null) {
				System.out.println("오라클 접속 성공");
				// 접속에 성공하였으므로, 레코드 1건 넣어보자!!
				String sql = "insert into student(student_id, id, pwd, name)";
				sql += "values(seq_student.nextval, 'kim', '0000', 'jay')";
				System.out.println(sql);
				// 이제 이 출력된 글자를 오라클에 입력하면 된다.
				// 하지만 이는 단지 쿼리문을 준비만 하고, 실행은 안 한 상태이다.
				// 자바의 DB 연동 기술을 가리켜 JDBC라 하며, 주로 java.sql 패키지에서 지원한다.
				// jdbc 관련 객체 중 PreparedStaterment 인터페이스가 쿼리문을 수행하는 역할을 한다.
				PreparedStatement pstmt = con.prepareStatement(sql); // 쿼리문을 수행할 인터페이스 메모리에 올라왔다.
				
				// 수행.
				pstmt.executeUpdate()	; // DML(insert, update, delete 수행)
			} else {
				System.out.println("오라클 접속 실패");
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로드 실패");
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
