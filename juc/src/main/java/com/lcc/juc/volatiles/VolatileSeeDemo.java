package com.lcc.juc.volatiles;


class ShardData {

    // 1.演示不加volatile 关键字,对线程的不可见性
    // private int num;

    // 2.演示加volatile 关键字,对线程的可见性,修改后通知其他线程
    private volatile int num;


    public int getNum() {
        return num;
    }

    void addNumber100() {
        num += 100;
    }

}

/**
 * volatile 保证可见性案例
 */
public class VolatileSeeDemo {

    public static void main(String[] args) {
        ShardData shardData = new ShardData();

        new Thread(() -> {
            System.out.println("T1| in");

            try {
                // 保证main 线程拿到 shardData的初始值
                Thread.sleep(1000);
                shardData.addNumber100();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + shardData.getNum());

            System.out.println("T1 out");
        }, "T1").start();

        while (shardData.getNum() == 0) {
        }


        System.out.println(Thread.currentThread().getName() + " " + shardData.getNum());
    }


}
