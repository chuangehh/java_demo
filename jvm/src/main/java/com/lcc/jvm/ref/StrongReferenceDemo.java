package com.lcc.jvm.ref;


public class StrongReferenceDemo {

	public static void main(String[] args) {
		Object o = new Object();
		Object o2 = o;
		o = null;

		System.gc();

		System.out.println(o);
		System.out.println(o2);
	}


}
