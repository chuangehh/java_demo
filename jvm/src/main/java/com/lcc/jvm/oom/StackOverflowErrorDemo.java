package com.lcc.jvm.oom;

/**
 * -XX:ThreadStackSize 默认512K - 1024K(跟随操作系统)
 *
 * @author liangchuanchuan
 */
public class StackOverflowErrorDemo {

	static void method1() {
		method1();
	}

	public static void main(String[] args) {
		method1();
	}
}
