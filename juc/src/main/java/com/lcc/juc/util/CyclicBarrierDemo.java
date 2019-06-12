package com.lcc.juc.util;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

/**
 * 跟 CountDownLatch 相比是做加法
 *
 * 加到多少在做事
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) {

        CyclicBarrier 召唤神龙 = new CyclicBarrier(7, () -> System.out.println("召唤神龙"));

        for (int i = 1; i <= 7; i++) {

            final int num = i-1;

            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " come in" );

                try { TimeUnit.SECONDS.sleep(new Random().nextInt(3));} catch (InterruptedException e) {e.printStackTrace();}
                System.out.println(Thread.currentThread().getName() + " " + Step.findByKey(num));
                try {
                    召唤神龙.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }, "T" + i).start();
        }

    }

}
