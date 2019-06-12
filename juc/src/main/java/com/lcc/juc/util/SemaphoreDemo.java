package com.lcc.juc.util;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;


/**
 * 信号量/信号灯
 *
 * Semaphore 是 synchronized 的加强版.作用是控制线程的并发数量(多对多)
 * synchronized: 多个线程对单个资源进行争抢
 * Semaphore: 多个线程对多个资源进行争抢
 * 主要方法 acquire:获取资源  release:释放资源
 *
 * 例子:
 * 停车场
 * 饭店吃饭等待
 *
 *
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);

        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " come in");
                    try { TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) {e.printStackTrace();}

                    System.out.println(Thread.currentThread().getName() + " 离开");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            }, "T" + i).start();
        }

    }
}
