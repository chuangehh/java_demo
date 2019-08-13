package com.lcc.jvm.ref;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

/**
 * 虚引用
 *
 * @author liangchuanchuan
 */
public class PhantomReferenceDemo {

	public static void main(String[] args) {
		ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();
		Reference<Object> objectWeakReference = new PhantomReference<>(new Object(), referenceQueue);

		System.out.println("objectWeakReference.get(): " + objectWeakReference.get());
		System.out.println("referenceQueue.poll(): " + referenceQueue.poll());

		System.gc();
		System.out.println("=============================");

		System.out.println("objectWeakReference.get(): " + objectWeakReference.get());
		System.out.println("referenceQueue.poll(): " + referenceQueue.poll());
	}

}
