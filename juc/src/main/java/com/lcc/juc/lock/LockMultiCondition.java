package com.lcc.juc.lock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 锁绑定多个 Condition,实现精确唤醒
 * <p>
 * 需求:
 * AA 打印 "我爱中华" 3次
 * BB 打印 "我的区长父亲" 4次
 * CC 打印 "为梦想干杯" 5次
 * <p>
 * AA -> BB -> CC 依次打印 轮询5次
 */
public class LockMultiCondition {

	public static void main(String[] args) {

		PrintService printService = new PrintService();

		new Thread(() -> {
			for (int i = 0; i < 5; i++) {
				printService.printAA();
			}
		}, "AA_1").start();

		new Thread(() -> {
			for (int i = 0; i < 5; i++) {
				printService.printBB();

			}
		}, "BB_1").start();


		new Thread(() -> {
			for (int i = 0; i < 5; i++) {
				printService.printCC();
			}
		}, "CC_1").start();

	}
}


/**
 * 打印服务
 * <p>
 * 线程 操作(方法) 资源类
 * 判断 干活 通知
 */
class PrintService {

	AtomicInteger atomicInteger = new AtomicInteger(1);
	ReentrantLock lock = new ReentrantLock();
	Condition conditionAA = lock.newCondition();
	Condition conditionBB = lock.newCondition();
	Condition conditionCC = lock.newCondition();

	void printAA() {
		lock.lock();
		try {
			while (atomicInteger.get() != 1){
				conditionAA.await();
			}
			for (int i = 0; i < 3; i++) {
				System.out.println(Thread.currentThread().getName() + " " + "我爱中华");
			}
			atomicInteger.set(2);
			conditionBB.signalAll();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			lock.unlock();   //释放锁
		}
	}

	void printBB() {
		lock.lock();
		try {
			while (atomicInteger.get() != 2){
				conditionBB.await();
			}
			for (int i = 0; i < 4; i++) {
				System.out.println(Thread.currentThread().getName() + " " + "我的区长父亲");
			}
			atomicInteger.set(3);
			conditionCC.signalAll();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			lock.unlock();   //释放锁
		}

	}

	void printCC() {
		lock.lock();
		try {
			while (atomicInteger.get() != 3){
				conditionCC.await();
			}
			for (int i = 0; i < 5; i++) {
				System.out.println(Thread.currentThread().getName() + " " + "为梦想干杯");
			}
			System.out.println();
			atomicInteger.set(1);
			conditionAA.signalAll();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			lock.unlock();   //释放锁
		}

	}

}