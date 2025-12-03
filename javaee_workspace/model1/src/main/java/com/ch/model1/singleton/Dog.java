package com.ch.model1.singleton;

/*
 * 전 세계 개발자들의 공통적 코드 패턴을 보고 각 패턴에 이름을 붙여 Design Pattern 이라는 책을 출간했다.
 *  개발 시 패턴의 이름으로 커뮤니케이션을 해야 업무 소통이 원할하다.
 *  
 *  SingleTon: 하나의 클래스로부터 오직 1개의 인스턴스 생성만을 허용하는 클래스 정의
 */

public class Dog {
	private static Dog instance;
	// 클래스는 사용하기 위해서 정의했는데 생성자를 private 으로만 지정하면 외부에서 사용을 못한다.
	private Dog() {
	}
	
	// 외부의 객체가 접근할 수 있는 일반 메서드 제공(생성자를 private 으로 막았으므로)
	// 아래의 메서드는 static 수식자(modifier)가 붙지 않았기 때문에 인스턴스 소속 메서드가 된다.
	// 즉, 외부에서 이 메서드를 호출하려면 new Dog() 으로 강아지의 인스턴스를 생성하고 그 인스턴스를 통해서만 접근 가능함.
	
	public static Dog getInstance() {
		
		// 인스턴스간 공유되고 있는 클래스 변수에, 이미 값이 채워져 있으면
		// 인스턴스가 존재하는 것이기 때문에 중복해서 new 하면 안 된다.
		// 따라서 최초 한 번만 new가 실행되도록 조건문으로 막아야 한다. => 이게 바로 싱글톤
		if (instance == null) {
			instance = new Dog();			
		}
		return instance;
	}

	public void bark() {
		System.out.println("멍멍");
	}
	
}
