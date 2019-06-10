package com.lcc.juc.lock;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


interface Cache<K, V> {
    V get(K t);

    void set(K key, V value);

    void clear(K t);
}

/**
 * 原始版
 *
 * 不能保证
 * 1.写-写 独占
 * 2.读-写 独占
 */
class MyCache_v1<K,V> implements Cache<K,V>{

    volatile HashMap<K, V> cache = new HashMap<>();

    @Override
    public V get(K key) {
        System.out.println(Thread.currentThread().getName() + " 读取" );

        // 模拟网络拥堵
        try { TimeUnit.MILLISECONDS.sleep(50);} catch (InterruptedException e) {e.printStackTrace();}
        V result = cache.get(key);
        System.out.println(Thread.currentThread().getName() + " 读取完成:" + result  );
        return result;
    }

    @Override
    public void set(K key, V value) {
        System.out.println(Thread.currentThread().getName() + " 写入" );

        // 模拟网络拥堵
        try { TimeUnit.MILLISECONDS.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
        cache.put(key, value);
        System.out.println(Thread.currentThread().getName() + " 写入完成" );
    }

    @Override
    public void clear(K t) {

    }

}


/**
 * ReentrantLock 版
 *
 * 不能保证
 * 读-写 独占
 */
class MyCache_v2<K,V> implements Cache<K,V>{

    volatile HashMap<K, V> cache = new HashMap<>();
    Lock lock = new ReentrantLock();

    @Override
    public V get(K key) {
        System.out.println(Thread.currentThread().getName() + " 读取" );

        // 模拟网络拥堵
        try { TimeUnit.MILLISECONDS.sleep(50);} catch (InterruptedException e) {e.printStackTrace();}
        V result = cache.get(key);
        System.out.println(Thread.currentThread().getName() + " 读取完成:" + result  );
        return result;
    }

    @Override
    public void set(K key, V value) {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 写入" );

            // 模拟网络拥堵
            try { TimeUnit.MILLISECONDS.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
            cache.put(key, value);
            System.out.println(Thread.currentThread().getName() + " 写入完成" );
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();   //释放锁
        }
    }

    @Override
    public void clear(K t) {

    }

}


/**
 * ReentrantReadWriteLock 版
 */
class MyCache_v3<K,V> implements Cache<K,V>{

    volatile HashMap<K, V> cache = new HashMap<>();
    ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public V get(K key) {
        V result = null;
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 读取" );

            // 模拟网络拥堵
            try { TimeUnit.MILLISECONDS.sleep(50);} catch (InterruptedException e) {e.printStackTrace();}
            result = cache.get(key);
            System.out.println(Thread.currentThread().getName() + " 读取完成:" + result  );
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            lock.readLock().unlock();   //释放锁
        }
        return result;
    }

    @Override
    public void set(K key, V value) {
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 写入" );

            // 模拟网络拥堵
            try { TimeUnit.MILLISECONDS.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
            cache.put(key, value);
            System.out.println(Thread.currentThread().getName() + " 写入完成" );
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            lock.writeLock().unlock();   //释放锁
        }
    }

    @Override
    public void clear(K t) {

    }

}


public class ReentrantReadWriteLockDemo {

    public static void main(String[] args) {
        // 原始版
        // Cache myCache = new MyCache_v1();
        // ReentrantLock 版
        // Cache myCache = new MyCache_v2();
        // ReentrantReadWriteLock 版
        Cache myCache = new MyCache_v3();

        for (int i = 1; i <= 5; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.set(finalI, finalI);

            }, "set " + i).start();
        }

        for (int i = 1; i <= 5; i++) {
            int finalI = i;

            new Thread(() -> {
                myCache.get(finalI);

            }, "get " + i).start();
        }

    }

}
