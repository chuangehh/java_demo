package com.lcc.juc.producerconsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者消费者,蛋糕模型三代实现
 * 1.原始API: synchronized,wait(),notify(),notifyAll()
 * 2.锁API: lock,await(),signal(),signalAll()
 * 3.阻塞队列
 */
public class CakeDemo {

	public static void main(String[] args) throws InterruptedException {
//		CakeSyncImpl cake = new CakeSyncImpl();
//		CakeLockImpl cake = new CakeLockImpl();
		CakeBlockingQueueImpl cake = new CakeBlockingQueueImpl(new LinkedBlockingQueue<>(10));
		Thread p1 = new Thread(() -> {
			for (int i = 0; i < 40; i++) {
				cake.produce();
			}
		}, "P1");
		p1.start();
		new Thread(p1, "P2").start();

		Thread c1 = new Thread(() -> {
			for (int i = 0; i < 40; i++) {
				cake.consumer();
			}
		}, "C1");
		c1.start();
		new Thread(c1, "C2").start();

		TimeUnit.SECONDS.sleep(8);
		cake.setStop();
	}

}


/**
 * 第三代
 * 线程操作资源类->蛋糕 阻塞队列版实现
 * <p>
 * 省去程序员对线程通信的管理,交给阻塞队列
 * 减少多线程Bug 笑哭
 */
class CakeBlockingQueueImpl {

	/**
	 * 是否停止生产蛋糕
	 */
	private volatile boolean stop = false;

	private volatile AtomicInteger cakeNum = new AtomicInteger();

	/**
	 * 阻塞队列
	 */
	private BlockingQueue<Integer> blockingQueue;

	public CakeBlockingQueueImpl(BlockingQueue<Integer> blockingQueue) {
		this.blockingQueue = blockingQueue;
	}

	void setStop() {
		stop = true;
	}

	void produce() {
		if (stop) {
			return;
		}
		try {
			int cake = cakeNum.incrementAndGet();
			blockingQueue.offer(cake, 600, TimeUnit.MICROSECONDS);
			System.out.println(Thread.currentThread().getName() + " 生产蛋糕: " + cake);
			TimeUnit.MILLISECONDS.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	void consumer() {
		try {
			Integer take = blockingQueue.poll(10, TimeUnit.MICROSECONDS);
			if (take == null && stop) {
				return;
			}
			System.out.println(Thread.currentThread().getName() + " 消费蛋糕: " + take);
			TimeUnit.MILLISECONDS.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}


/**
 * 第二代
 * 线程操作资源类->蛋糕 Lock Api版实现
 * <p>
 * 1.关闭蛋糕坊过程:stop 若没有写好位置会造成线程相互等待造成死锁
 * 2.蛋糕数量:cakeNum 应该用原子类或者同步代码块来操作
 */
class CakeLockImpl {

	/**
	 * 是否停止生产蛋糕
	 */
	private volatile boolean stop = false;

	/**
	 * 蛋糕数,只允许生产1个
	 */
	private volatile AtomicInteger cakeNum = new AtomicInteger();

	ReentrantLock lock = new ReentrantLock();
	Condition condition = lock.newCondition();

	void setStop() {
		stop = true;
	}

	void produce() {
		lock.lock();
		try {
			while (cakeNum.get() == 1) {
				condition.await();
			}
			if (stop) {
				System.out.println(Thread.currentThread().getName() + " 关闭蛋糕坊: " + cakeNum);
				return;
			}
			TimeUnit.MILLISECONDS.sleep(200);
			System.out.println(Thread.currentThread().getName() + " 生产蛋糕: " + cakeNum.incrementAndGet());
			condition.signalAll();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			lock.unlock();   //释放锁
		}
	}

	void consumer() {
		lock.lock();
		try {
			while (cakeNum.get() == 0) {
				if (stop) {
					System.out.println(Thread.currentThread().getName() + " 关闭蛋糕坊: " + cakeNum);
					return;
				}
				condition.await();
			}
			TimeUnit.MILLISECONDS.sleep(200);
			System.out.println(Thread.currentThread().getName() + " 消费蛋糕: " + cakeNum.decrementAndGet());
			condition.signalAll();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			lock.unlock();   //释放锁
		}
	}

}

/**
 * 第一代
 * 线程操作资源类->蛋糕 synchronized版实现
 *
 * <p>
 * 需求:
 * 1.多个生产线程生产蛋糕,蛋糕生产大于1个停止生产
 * 2.多个消费线程消费蛋糕,蛋糕生产小于1个停止消费
 * 3.蛋糕坊关闭: 生产线程停止生产蛋糕,消费线程把蛋糕卖完后退出
 */
class CakeSyncImpl {

	/**
	 * 是否停止生产蛋糕
	 */
	private volatile boolean stop = false;

	/**
	 * 蛋糕数,只允许生产1个
	 */
	private volatile int cakeNum;

	void setStop() {
		stop = true;
	}

	synchronized void produce() {
		while (cakeNum == 1) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (stop) {
			System.out.println(Thread.currentThread().getName() + " 关闭蛋糕坊: " + cakeNum);
			return;
		}
		try {
			TimeUnit.MILLISECONDS.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " 生产蛋糕: " + ++cakeNum);
		notifyAll();
	}


	/**
	 * 虚假唤醒:
	 * 1.同一个方法两个线程同时进入
	 * 2.同时过了判断条件,进入等待
	 * 3.如果是if 判断,执行后续代码,造成代码块被执行
	 */
	synchronized void consumer() {
		while (cakeNum == 0) {
			if (stop) {
				System.out.println(Thread.currentThread().getName() + " 关闭蛋糕坊: " + cakeNum);
				return;
			}
			try {
				// 2.同时过了判断条件,进入等待
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		try {
			TimeUnit.MILLISECONDS.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + " 消费蛋糕: " + --cakeNum);
		notifyAll();
	}
}




