package src.board;

import java.io.IOException;
import javax.servlet.ServletException;
import java.io.PrintWriter; // 문자 기반의 출력 스트림  

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListServlet extends HttpServlet{

    // 웹브라우저로 접근하는 유저들에게 메세지 출력하기    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        // javaSE 의 스트림 객체를 사용한다
        PrintWriter out = response.getWriter(); // 클라이언트에게 문자열을 출력받기 위해 응답 객체로부터 스트림을 얻는다.
        out.println("this is my respose data");

    }

}
