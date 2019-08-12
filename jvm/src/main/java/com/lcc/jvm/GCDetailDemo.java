package com.lcc.jvm;

/**
 * -Xms10m -Xmx10m -XX:+PrintGCDetails
 *
 * 年轻代(堆的1/3)
 *   eden space: 4:1:1
 *   from space:
 *   to space:
 * 老年代(堆的2/3)
 *
 */
public class GCDetailDemo {

	public static void main(String[] args) {
		System.out.println("start gc");
		byte[] bytes = new byte[1024 * 1024];
		byte[] bytes2 = new byte[1024 * 1024 * 2];
		bytes = new byte[1024 * 1024 * 2];
		bytes = new byte[1024 * 1024 * 2];
		bytes = new byte[1024 * 1024 * 2];
		bytes = new byte[1024 * 1024 * 8];

		System.out.println("GC");
	}
}
