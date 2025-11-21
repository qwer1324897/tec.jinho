package com.ch.site1118.controller;

public class StringTest {

	public static void main(String[] args) {
		String str1 = "korea";
		String str2 = "korea";
		System.out.println(str1==str2);	// true 일까 false 일까
		
		String s1 = new String("korea");
		String s2 = new String("korea");
		System.out.println(s1==s2);	// true 일까 false 일까
	}

}
