package com.lcc.jvm;

/**
 * VM options:-XX:+PrintGCDetails
 * <p>
 * PrintFlagsFinal举例,运行java命令的同时打印出参数:
 * VM options: -Xms1g -Xmx2g -XX:+PrintFlagsFinal -XX:MaxTenuringThreshold=10
 * result:
 * InitialHeapSize                          := 1073741824
 * MaxHeapSize                              := 2147483648
 * MaxTenuringThreshold                     := 10
 */
public class HelloGC {
	public static void main(String[] args) throws InterruptedException {
		System.out.println("HelloGC");
		// -Xms
		System.out.println("Runtime.getRuntime().totalMemory() MB: " + Runtime.getRuntime().totalMemory() / 1024 / 1024);
		// -Xmx
		System.out.println("Runtime.getRuntime().maxMemory() MB: " + Runtime.getRuntime().maxMemory() / 1024 / 1024);
		// 当前可用申请的内存,
		System.out.println("Runtime.getRuntime().freeMemory() MB: " + Runtime.getRuntime().freeMemory() / 1024 / 1024);
		System.out.println("Runtime.getRuntime().availableProcessors(): " + Runtime.getRuntime().availableProcessors());

		Thread.sleep(Integer.MAX_VALUE);
	}

}
