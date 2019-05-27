package com.lcc.juc.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

public class CASAtomicStampedReferenceDemo {

    public static void main(String[] args) {
        ShardData lcc = new ShardData("梁川川", 18);
        ShardData ycq = new ShardData("杨彩琼", 17);
        ShardData zs = new ShardData("张三", 10);

        AtomicStampedReference<ShardData> person = new AtomicStampedReference<>(lcc, 1);

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " " + person.getReference());
            try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}

            int stamp = person.getStamp();
            System.out.println(Thread.currentThread().getName() + " " + person.compareAndSet(lcc, zs, stamp, ++stamp));
            System.out.println(Thread.currentThread().getName() + " =======================update========================" );

            System.out.println(Thread.currentThread().getName() + " " + person.compareAndSet(zs, lcc, stamp, ++stamp));
        }, "T1").start();


        new Thread(() -> {
            int stamp = person.getStamp();
            System.out.println(Thread.currentThread().getName() + " " + person.getReference());
            try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) {e.printStackTrace();}

            // 中间被修改过,有猫腻
            // expectedStamp = 1,主内存中的stamp = 3,then false
            System.out.println(stamp);
            System.out.println(Thread.currentThread().getName() + " " + person.compareAndSet(lcc, ycq, stamp, ++stamp));
        }, "T2").start();
    }

}
