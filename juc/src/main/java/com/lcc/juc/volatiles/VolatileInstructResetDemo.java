package com.lcc.juc.volatiles;


/**
 * 为了优化指令，提高程序运行效率，在不影响单线程程序执行结果的前提下，尽可能地提高并行度
 * <p>
 * 单个线程执行: 保证顺序执行
 * 多个线程执行: 根据数据依赖性对指令进行重排,多个CPU执行没有依赖性的指令
 * <p>
 * <p>
 * 指令重排导致单例模式失效
 */
class Singleton {

    // private static Singleton instance = null;
    private volatile static Singleton instance = null;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    // 如果一个操作不是原子的,就会给JVM留下重排的机会
                    // memory = allocate()
                    // instance(memory)
                    // instance = memory
                    instance = new Singleton();  //非原子操作
                }
            }
        }
        return instance;
    }
}


class Context {

    boolean init;
    Object context;

    /**
     *
     */
    public void loadContext() {
        // 可能发生指令重排
        // init = true;
        // context = new Object();

        context = new Object();
        init = true;
    }


    /**
     * 如果指令重排序调用该方法线程会出现空指针异常
     */
    public void doSomethingWithConfig() {
        while (!init) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(Thread.currentThread().getName() + " " + context.hashCode());
    }


}

/**
 * volatile 禁止指令重排序
 */
public class VolatileInstructResetDemo {

    public static void main(String[] args) {

        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " " + (Singleton.getInstance() == Singleton.getInstance()));
            }, "T" + i).start();
        }

    }

}
