package com.lcc.jvm;

/**
 * VM options:-XX:+PrintGCDetails
 *
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
        Thread.sleep(Integer.MAX_VALUE);
    }

}
