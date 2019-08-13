package com.lcc.jvm.ref;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * 软引用
 *
 * @author liangchuanchuan
 */
public class WeakReferenceDemo {

	public static void main(String[] args) {
		ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
		WeakReference<Object> objectWeakReference = new WeakReference<>(new Object(), referenceQueue);

		System.out.println("objectWeakReference.get(): " + objectWeakReference.get());
		System.out.println("referenceQueue.poll(): " + referenceQueue.poll());

		System.gc();
		System.out.println("=============================");

		System.out.println("objectWeakReference.get(): " + objectWeakReference.get());
		System.out.println("referenceQueue.poll(): " + referenceQueue.poll());
	}

}
