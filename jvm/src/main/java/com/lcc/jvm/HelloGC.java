package com.lcc.jvm;

/**
 * VM options:-XX:+PrintGCDetails
 */
public class HelloGC {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("HelloGC");
        Thread.sleep(Integer.MAX_VALUE);
    }

}
