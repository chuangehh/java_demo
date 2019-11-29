package com.atlcc.single;

public class SingletonTest {

	public static void main(String[] args) {
		// 饿汉式
		System.out.println(Singleton1.INSTANCE);
		System.out.println(Singleton2.INSTANCE);
		System.out.println(Singleton3.INSTANCE);

		// 懒汉式
		System.out.println(Singleton4.getInstance());
		System.out.println(Singleton5.getInstance());
		System.out.println(Singleton6.getInstance());
	}

}
