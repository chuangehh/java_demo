package com.lcc.juc.collections;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class CollectionsConCurrentModificationException {

    public static void main(String[] args) {
        Map<String, String> map;
        // jdk7 hashMap: 数组 + 链表
        // jdk8 hashMap: 数组 + 链表 + 红黑树

        // jdk7 ConcurrentHashMap  segment(default capacity 16) + Map 分段锁
        // jdk8 ConcurrentHashMap

        // 默认容量 16,扩展因子0.75
        // map = new HashMap<>();
        // 方案1: 读写都加锁
        // map = new Hashtable<>();
        // 方案2: 读写都加锁
        // map = Collections.synchronizedMap(new Hashtable<>());
        // 方案3: jdk7(16个segment分段锁)
        // jdk8
        map = new ConcurrentHashMap<>();

        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                map.put(UUID.randomUUID().toString().substring(0, 8), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + map);
            }, "T" + i).start();
        }
    }

    private static void copyOnWriteSet() {
        Set<String> set;
        // set = new HashSet<>();
        // 方案1: 读写都加锁
        // set = Collections.synchronizedSet(new HashSet<>());
        // 方案2: 写加锁
        set = new CopyOnWriteArraySet<>();


        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + set);
            }, "T" + i).start();
        }
    }

    private static void copyOnWriteArrayList() {
        List<String> arrayList;

        // 问题 java.util.ConcurrentModificationException
        // arrayList = new ArrayList<>();
        // 方案1: 读写都加锁
        // arrayList = new Vector<>();
        // 方案2: 读写都加锁
        // arrayList = Collections.synchronizedList(new ArrayList<>());

        // 方案3: 写加锁
        // volatile + ReentrantLock
        arrayList = new CopyOnWriteArrayList<>();

        for (int i = 1; i <= 30; i++) {
            new Thread(() -> {
                arrayList.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(Thread.currentThread().getName() + arrayList);
            }, "T" + i).start();
        }
    }

}
