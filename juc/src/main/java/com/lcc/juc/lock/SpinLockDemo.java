package com.lcc.juc.lock;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

class SpinLock{

    AtomicReference<Thread> reference = new AtomicReference<>();

    void lock(){
        System.out.println(Thread.currentThread().getName() + " Come in" );
        while (!reference.compareAndSet(null,Thread.currentThread())){
        }

        try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) {e.printStackTrace();}
        unLock();
    }

    void unLock(){
        System.out.println(Thread.currentThread().getName() + " unLock" );
        reference.set(null);
    }

}


/**
 * 自旋锁,其实也不是锁,不断的比较并交换
 * 个人理解: 也可以是非公平锁的一种
 */
public class SpinLockDemo {

    public static void main(String[] args) {
        SpinLock spinLock = new SpinLock();


        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                spinLock.lock();
            }, "T" + i).start();
            try {TimeUnit.MILLISECONDS.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
        }

    }

}
