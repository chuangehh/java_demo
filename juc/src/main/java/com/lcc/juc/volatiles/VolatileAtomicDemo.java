package com.lcc.juc.volatiles;


class ShardData2 {

    private volatile int num;


    public int getNum() {
        return num;
    }

    void addNumber1() {
        num++;
    }

}

public class VolatileAtomicDemo {

    public static void main(String[] args) {
        ShardData2 shardData2 = new ShardData2();

        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {

                for (int j = 0; j < 1000; j++) {
                    shardData2.addNumber1();
                }

            }, "T" + i).start();
        }

        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
        System.out.println(Thread.currentThread().getName() + " 期望值1万,实际值: " + shardData2.getNum());

    }

}
