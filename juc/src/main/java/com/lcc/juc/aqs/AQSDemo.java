package com.lcc.juc.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 3个顾客去银行办理事务，银行窗口只有一个
 *
 *
 *
 */
public class AQSDemo {




    public static void main(String[] args) {
        Lock lock  = new ReentrantLock();


        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " 进来办理事务" );
                try { TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                lock.unlock();   //释放锁
            }
        }, "T1").start();


        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " 进来办理事务" );
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                lock.unlock();   //释放锁
            }
        }, "T2").start();


        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " 进来办理事务" );
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                lock.unlock();   //释放锁
            }
        }, "T3").start();

    }


}
