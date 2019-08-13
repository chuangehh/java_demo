package com.lcc.jvm.ref;

import java.lang.ref.SoftReference;

/**
 * -Xms5m -Xmx5m -XX:+PrintGCDetails
 */
public class SoftReferenceDemo {

	public static void main(String[] args) {

		SoftReference<Object> objectSoftReference = new SoftReference<Object>(new Object());

		System.gc();
		System.out.println(objectSoftReference.get());

		try {
			byte[] bytes = new byte[1024 * 1024 * 6];
		}catch (Throwable e){
			e.printStackTrace();
		}

		System.out.println("============================================");
		System.out.println( objectSoftReference.get());
	}

}
