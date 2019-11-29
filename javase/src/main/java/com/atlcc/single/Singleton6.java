package com.atlcc.single;

/**
 * 懒汉式-内部类
 */
public class Singleton6 {

	private Singleton6() {
	}

	static class InnerSingletion {
		private static volatile Singleton6 INSTANCE = new Singleton6();
	}

	public static Singleton6 getInstance() {
		return InnerSingletion.INSTANCE;
	}

}
