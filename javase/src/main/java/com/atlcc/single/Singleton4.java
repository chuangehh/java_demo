package com.atlcc.single;

/**
 * 懒汉式-线程不安全
 */
public class Singleton4 {

	private static Singleton4 INSTANCE;

	private Singleton4() {
	}

	public static Singleton4 getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Singleton4();
		}
		return INSTANCE;
	}

}
