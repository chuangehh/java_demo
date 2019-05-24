package com.lcc.juc.cas;


import java.util.concurrent.atomic.AtomicInteger;

/**
 *  1.对象 + value内存地址 获取主内存中的最新值
 *  2.获取到的值用CAS 比较并交换
 *  3.如果失败一致重试
 *
 * @see sun.misc.Unsafe#getAndAddInt(Object var1, long var2, int var4)
 *
 * public final int getAndAddInt(Object var1, long var2, int var4) {
 *      int var5;
 *      do {
 *          var5 = this.getIntVolatile(var1, var2);
 *      } while(!this.compareAndSwapInt(var1, var2, var5, var5 + var4));
 *
 *      return var5;
 * }
 */
public class CASDemo {

    public static void main(String[] args) {

        AtomicInteger atomicInteger = new AtomicInteger();

        // expect:期望值  update:更新值
        System.out.println(atomicInteger.compareAndSet(atomicInteger.get(), 1));
        System.out.println(atomicInteger.compareAndSet(0, 1));

    }

}
