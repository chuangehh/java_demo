package com.lcc.juc.blockingqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueDemo {

    public static void main(String[] args) throws InterruptedException {

//  操作	抛出异常	返回指定的值	无限阻塞	超时阻塞
//  插入	add(e)	    offer(e)	    put(e)	    offer(e,time,unit)
//  移除	remove()	poll()	        take()	    poll(time,unit)
//  检查	element()	peek()	        不提供	    不提供

//        BlockingQueue blockingQueue = new ArrayBlockingQueue(10);
        BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>(3);


        timeout_block(blockingQueue);
    }

    private static void timeout_block(BlockingQueue<Integer> blockingQueue) throws InterruptedException {
        blockingQueue.offer(1);
        blockingQueue.offer(2);
        blockingQueue.offer(3);

        System.out.println(blockingQueue);
        System.out.println("===========");

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll(2, TimeUnit.SECONDS));
    }

    private static void block(BlockingQueue<Integer> blockingQueue) throws InterruptedException {
        blockingQueue.put(1);
        blockingQueue.put(2);
        blockingQueue.put(3);

        System.out.println(blockingQueue);
        System.out.println("===========");

        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
        System.out.println(blockingQueue.take());
    }

    private static void return_value(BlockingQueue<Integer> blockingQueue) {
        blockingQueue.offer(1);
        blockingQueue.offer(2);
        blockingQueue.offer(3);

        System.out.println(blockingQueue.peek());
        System.out.println("===========");

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
    }

    private static void throw_error(BlockingQueue<Integer> blockingQueue) {
        blockingQueue.add(1);
        blockingQueue.add(2);
        blockingQueue.add(3);

        System.out.println(blockingQueue.element());

        blockingQueue.remove(1);
        blockingQueue.remove(2);
        blockingQueue.remove(3);
    }

}
