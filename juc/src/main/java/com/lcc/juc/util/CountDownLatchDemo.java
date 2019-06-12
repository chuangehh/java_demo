package com.lcc.juc.util;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


enum Step{
    One(1,"组装离子推动器"),
    Two(2,"准备火箭燃料"),
    Three(3,"雷达工程"),
    Four(4,"伽马射线"),
    Five(5,"重力系统"),
    Six(6,"核反应堆"),
    Seven(7,"黑洞引擎");

    private int key;
    private String value;

    Step(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String findByKey(int key) {
        return values()[key].value;
    }
}

/**
 * 跟 CyclicBarrier 相比是做减法
 *
 * 减到0 在做事
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        int count = 7;

        CountDownLatch countDownLatch = new CountDownLatch(count);

        for (int i = 1; i <= count; i++) {

            final int num = i-1;
            new Thread(() -> {

                System.out.println(Thread.currentThread().getName() + " come in" );
                try { TimeUnit.SECONDS.sleep(new Random().nextInt(3));} catch (InterruptedException e) {e.printStackTrace();}
                System.out.println(Thread.currentThread().getName() + " " + Step.findByKey(num));

                countDownLatch.countDown();
            }, "T" + i).start();
        }

        countDownLatch.await();
        System.out.println("火箭发射！！！");


    }

}
