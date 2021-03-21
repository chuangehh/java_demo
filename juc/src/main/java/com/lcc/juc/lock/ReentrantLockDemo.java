package com.lcc.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁，线程可以多次进入持有同一把锁的代码块
 *
 * @author liangchuanchuan
 */
public class ReentrantLockDemo {
    static final Object LOCK_O = new Object();
    static Lock LOCK = new ReentrantLock();

    public static void main(String[] args) {
        synchronized (LOCK_O) {
            System.out.println(Thread.currentThread() + " 进入1");
            synchronized (LOCK_O) {
                System.out.println(Thread.currentThread() + " 进入2");
            }
        }

        LOCK.lock();
        try {
            System.out.println(Thread.currentThread() + " 进入3");
            LOCK.lock();
            try {
                System.out.println(Thread.currentThread() + " 进入4");
            } finally {
                LOCK.unlock();
            }
        } finally {
            LOCK.unlock();
        }


    }

}
