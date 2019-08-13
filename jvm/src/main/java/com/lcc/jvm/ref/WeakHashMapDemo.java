package com.lcc.jvm.ref;

import java.util.HashMap;
import java.util.WeakHashMap;

/**
 * 原理是使用了 WeakReference包装了key
 *
 * @see WeakHashMap.Entry  super(key, queue);
 * @see WeakHashMap#expungeStaleEntries()
 *
 * -Xms5m -Xmx5m -XX:+PrintGCDetails -XX:+UseSerialGC
 */
public class WeakHashMapDemo {

	public static void main(String[] args)  {
		// stringKey();

		HashMap<Integer, Object> hashMap = new HashMap<>(16);
		Integer hashKey = new Integer(100);
		hashMap.put(hashKey, "hashValue");
		hashKey = null;
		System.out.println(hashMap);
		System.gc();
		System.out.println(hashMap);


		System.out.println("================================");

		WeakHashMap<Integer, Object> weakHashMap = new WeakHashMap<>();
		Integer weakHashKey = new Integer(200);
		weakHashMap.put(weakHashKey, "weakHashValue");
		weakHashKey = null;
		System.out.println(weakHashMap);
		System.gc();
		System.out.println(weakHashMap);
	}

	/**
	 * 使用字符串不行,使用Integer/Long .. -128~127 不行
	 * 字符串有常量池,是强引用
	 * Integer/Long .. 包装类型里有一个缓冲区,也是强引用
	 */
	private static void stringKey() {
		HashMap<String, Object> hashMap = new HashMap<>(16);
		String hashKey = "hashKey";
		hashMap.put(hashKey, "hashValue");
		hashKey = null;
		System.out.println(hashMap);
		System.gc();
		System.out.println(hashMap);


		System.out.println("================================");

		WeakHashMap<String, Object> weakHashMap = new WeakHashMap<>();
		String weakHashKey = "weakHashKey";
		weakHashMap.put(weakHashKey, "weakHashValue");
		weakHashKey = null;
		System.out.println(weakHashMap);
		System.gc();
		System.out.println(weakHashMap);
	}

}
