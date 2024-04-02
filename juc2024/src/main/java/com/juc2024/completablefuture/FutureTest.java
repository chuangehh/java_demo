package com.juc2024.completablefuture;

import java.util.concurrent.*;

public class FutureTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        // 1、new 线程有几种方式？
        new Thread(new MyThread1(), "Runnable").start();

        // 2、问题，Callable 带返回值的 Thread没法玩，玩个蛋蛋，问GPT，告诉我用 Future，那整
        FutureTask futureTask = new FutureTask<>(new MyThread2());
        new Thread(futureTask, "MyThread2").start();

        // 缺点1：获取返回值时会阻塞
        // futureTask.get(1, TimeUnit.SECONDS);
        // 缺点2：获取返回值时会阻塞
        while (!futureTask.isDone()) {
            if (futureTask.isDone()) {
                System.out.println(futureTask.get());
                return;
            } else {
                Thread.sleep(500);
            }
        }

        // 3、优点 可以用线程池显著提升 性能
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Future<String> future = executor.submit(() -> "Hello, Callable!");
        String result = future.get();
        System.out.println("Callable返回结果：" + result);
        executor.shutdown();
    }

    static class MyThread1 implements Runnable {
        @Override
        public void run() {
            System.out.println("run 毛线都不返回");
        }
    }

    static class MyThread2 implements Callable {
        @Override
        public String call() throws Exception {
            Thread.sleep(2000);
            System.out.println("MyThread2启动了，握草");
            return "返回内容";
        }
    }

}
