package com.ch.mvcframework.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.ch.mvcframework.movie.model.MovieManager;

// 영화 정보를 출력해주는 로직을 JavaSE의 GUI 프로그래밍에서도 사용할 수 있는지 테스트.
// 만약 성공한다면, 공통 로직 작성 성공과 동시에 재사용성이 극대화 된 것.

public class MovieForm extends JFrame{
	
	JButton bt;
	JComboBox box;
	String[] movies = {"귀멸의 칼날", "체인소 맨", "나우유씨미2", "주토피아2"};
	MovieManager movieManager = new MovieManager();
	
	public MovieForm() {
		bt = new JButton("피드백 요청");
		box = new JComboBox();
		
		for (String movie : movies) 	{// 향상된 for 문
			box.addItem(movie);
		}
		// 생성한 버튼을 윈도우에 부착
		// 부착 전, html처럼 레이아웃을 먼저 설정해야 함.
		setLayout(new FlowLayout()); 		// FlowLayout: 수평, 수직의 직선으로 컴포넌트를 배치. 윈도우 창에 따라 내용물들이 흘러다님.
		
		this.add(box);	// 콤보박스를 윈도우에 부착
		// 이제 버튼을 윈도우에 부착
		this.add(bt);
		
		// 버튼에 리스너 연결
		bt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// System.out.println("누름?"); 동작확인
				// 공통 로직인 MovieManager 를 활용하여 동작
				String item = (String)box.getSelectedItem();
				String msg = movieManager.getAdvice(item);
				
				JOptionPane.showMessageDialog(MovieForm.this, msg);
			}
		});
		
		setSize(300, 200);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		// MovieForm 이 곧 윈도우 이므로, MovieForm을 new 한 순간 윈도우 창이 메모리에 생성된다.
		// is a 관계 이므로.
		MovieForm movieForm = new MovieForm();
		
	}
}
