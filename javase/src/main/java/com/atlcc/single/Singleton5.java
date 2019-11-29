package com.atlcc.single;

/**
 * 懒汉式-双检锁
 */
public class Singleton5 {

	private static volatile Singleton5 INSTANCE;

	private Singleton5() {
	}

	public static Singleton5 getInstance() {
		if (INSTANCE == null) {
			synchronized (Singleton5.class) {
				if (INSTANCE == null) {
					INSTANCE = new Singleton5();
				}
			}
		}
		return INSTANCE;
	}

}
