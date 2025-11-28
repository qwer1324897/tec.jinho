package com.ch.notice.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.ch.notice.domain.Notice;
import com.ch.notice.repository.NoticeDAO;

public class RegistForm extends JFrame{
									 /*is a*/
	
	// 클래스가 보유한 멤버변수가 객체형일 경우 has a 관계
	JTextField title;	// 제목입력 텍스트 박스
	JTextField writer;	// 작성자 입력 텍스트 박스
	JTextArea content;		// 내용 입력 박스
	JButton bt;	// 등록버튼
	NoticeDAO dao; 	 // 오직 table에 대해 CRUD만을 처리하는 객체를 보유한다
	
	
	// 생성자의 목적은, 이 객체의 인스턴스가 생성될 때 초기화할 작업이 있을 경우
	// 초기화 작업을 지원하기 위함이다.
	public RegistForm() {

		title = new JTextField(30);	// 텍스트 박스의 디자인 길이
		writer = new JTextField(30);		
		content = new JTextArea(10,30);
		bt = new JButton("등록");
		dao = new NoticeDAO();
		
		// 컴포넌트를 부착하기 전에, 레이아웃을 결정짓자 CSS div 로 레이아웃 적용하는 것과 비슷함
		setLayout(new FlowLayout());	  // 수평이나 수직으로 흐르는 레이아웃
		
		this.add(title);	// 윈도우인 나의 몸체에 title 을 부착
		this.add(writer);
		this.add(content);
		this.add(bt);		// 윈도우인 나의 몸체에 button 을 부착
		
		this.setSize(400, 300);	// 너비와 높이를 부여
		this.setVisible(true);		// 디폴트로 안 보이므로, 보이게 만듬
		
		// 버튼에 클릭이벤트 연결하기
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// System.out.println("나 눌렀어?");
				regist();
			}
		});
	}
	
	// 게시물 등록
	public void regist() {
		Notice notice = new Notice();
		notice.setTitle(title.getText());
		notice.setWriter(writer.getText());
		notice.setContent(content.getText());
		
		int result = dao.regist(notice);	// db에 insert
		if(result < 1l) {
			JOptionPane.showMessageDialog(this, "실패");
		} else {
			JOptionPane.showMessageDialog(this, "성공");
		}
		
	}
	public static void main(String[] args) {
		
		RegistForm win = new RegistForm();	
	}
}
