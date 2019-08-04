package com.lcc.jvm;

/**
 * 可作为GC Root对象
 *
 * 1.虚拟机栈中引用的对象
 * 2.方法区中的类静态属性引用对象
 * 3.方法区中常量引用对象
 * 4.本地方法栈中JNI(Native的方法)引用的对象
 */
public class GCRootDemo {

    /**
     * 100MB
     */
    private byte[] bytes = new byte[100 * 1024 * 1024];

    private static GCRootDemo staticGCRootDemo = new GCRootDemo();

    private final static GCRootDemo finalGCRootDemo = new GCRootDemo();


    public static void main(String[] args) {
        method1();
    }

    static void method1(){
        GCRootDemo gcRootDemo = new GCRootDemo();
        System.gc();
        System.out.println("First GC");
    }

}
