package com.lcc.jvm.oom;

/**
 * -Xms5m -Xmx5m -XX:+PrintGCDetails
 *
 * @author liangchuanchuan
 */
public class JavaHeapSpaceDemo {

	public static void main(String[] args) {
		byte[] bytes = new byte[1024 * 1024 * 10];
	}

}
