package com.ch.mvframework.food.model;

public class FoodManager {

	public String getAdvice(String food) {
		String msg = "선택된 음식이 없음";
		
		if(food !=null) {		// 파라미터가 있을 때만
			if(food.equals("제육볶음")) {
				msg = "돼지고기와 야채를 고추장에 볶아낸 한식";
			} else if (food.equals("라멘")){
				msg = "고기를 삶아낸 육수에 면과 고명을 올려낸 일식";
			}else if (food.equals("햄버거")){
				msg = "빵 사이에 고기패티와 각종 야채, 소스를 담아낸 양식";
			}else if (food.equals("스시")){
				msg = "밥 위에 회를 올려낸 일식";
			}
		}
		return msg;
	}
	
}
