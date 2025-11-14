// 외부에 있는 클래스를 사용하려면.

package myproject.use;

import myproject.animal.Dog;

class UseDog{
	public static void main(String[] args) {
		Dog d = new Dog();
		System.out.println("The age of Dog is " + d.age);
	}

}