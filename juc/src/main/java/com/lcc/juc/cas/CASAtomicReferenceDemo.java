package com.lcc.juc.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;


class ShardData {

    String name;
    int age;

    public ShardData(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "ShardData{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}

public class CASAtomicReferenceDemo {

    public static void main(String[] args) {
        ShardData lcc = new ShardData("梁川川", 18);
        ShardData ycq = new ShardData("杨彩琼", 17);
        ShardData zs = new ShardData("张三", 10);

        AtomicReference<Object> person = new AtomicReference<>();
        person.set(lcc);

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " " + person.get());

            try {TimeUnit.SECONDS.sleep(1);} catch (InterruptedException e) {e.printStackTrace();}

            System.out.println(Thread.currentThread().getName() + " " + person.compareAndSet(lcc, zs));
            System.out.println(Thread.currentThread().getName() + " " + person.compareAndSet(zs, lcc));
        }, "T1").start();


        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " " + person.get());
            try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) {e.printStackTrace();}


            // 中间被修改过,有猫腻
            System.out.println(Thread.currentThread().getName() + " " + person.compareAndSet(lcc, ycq));
        }, "T2").start();
    }
}
