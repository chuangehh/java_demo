package com.juc2024.completablefuture;

import java.util.concurrent.*;

/**
 * CompletableFuture Test
 * <p>
 * 1、构建异步任务的2类方法，注意线程池参数，生产一般自定义线程池
 * pool-1-thread-1 runAsync()
 * 1.1、runAsync(Runnable, 线程池),无返回结果 null
 * 1.2、supplyAsync(Supplier, 线程池),有返回结果 supplyAsync返回结果
 *
 * 2、获得结果和触发计算
 * 2.1、get() 阻塞获取结果: supplyAsync返回结果 耗时：1004
 * 2.2、get(long timeout, TimeUnit unit) 阻塞获取结果, 如果超时会抛超时异常: supplyAsync返回结果 耗时：514 java.util.concurrent.TimeoutException
 * 2.3、join() 和get作用一样，只是不需要显示抛异常: supplyAsync返回结果 耗时：1007
 * 2.4、getNow(备胎值) 如果计算完成就返回正常值，否则返回备胎值: 备胎值 耗时：1
 * 2.5、getNow(备胎值) 如果计算完成就返回正常值，否则返回备胎值: supplyAsync返回结果 耗时：1208
 *
 */
public class CompletableFutureTest {
    public static void main(String[] args) {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);


        // 1、构建异步任务的2类方法，注意线程池参数，生产一般自定义线程池
        buildCompletableFuture(fixedThreadPool);

        // 2、获得结果和触发计算
        getResult(fixedThreadPool);

        fixedThreadPool.shutdown();
    }

    private static void buildCompletableFuture(ExecutorService fixedThreadPool) {
        System.out.println("1、构建异步任务的2类方法，注意线程池参数，生产一般自定义线程池");
        CompletableFuture<Void> runAsync = CompletableFuture.runAsync(() -> System.out.println(Thread.currentThread().getName() + " runAsync()"), fixedThreadPool);
        System.out.println("1.1、runAsync(Runnable, 线程池),无返回结果 " + runAsync.join());
        CompletableFuture<String> supplyAsync = CompletableFuture.supplyAsync(() -> "supplyAsync返回结果", fixedThreadPool);
        System.out.println("1.2、supplyAsync(Supplier, 线程池),有返回结果 " + supplyAsync.join());
        System.out.println();
    }

    private static void getResult(ExecutorService fixedThreadPool) {
        System.out.println("2、获得结果和触发计算");
        // 2.1、get() 阻塞获取结果
        long start = System.currentTimeMillis();
        String get = null;
        try {
            get = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
                return "supplyAsync返回结果";
            }, fixedThreadPool).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("2.1、get() 阻塞获取结果: " + get + " 耗时：" + (System.currentTimeMillis() - start));


        // 2.2、get(long timeout, TimeUnit unit) 阻塞获取结果, 如果超时会抛超时异常
        start = System.currentTimeMillis();
        try {
            get = CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {
                }
                return "supplyAsync返回结果";
            }, fixedThreadPool).get(500, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        System.out.println("2.2、get(long timeout, TimeUnit unit) 阻塞获取结果, 如果超时会抛超时异常: " + get + " 耗时：" + (System.currentTimeMillis() - start));

        // 2.3、join() 和get作用一样，只是不需要显示抛异常
        start = System.currentTimeMillis();
        String join = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            return "supplyAsync返回结果";
        }, fixedThreadPool).join();
        System.out.println("2.3、join() 和get作用一样，只是不需要显示抛异常: " + join + " 耗时：" + (System.currentTimeMillis() - start));

        // 2.4、getNow(备胎值) 如果计算完成就返回正常值，否则返回备胎值
        start = System.currentTimeMillis();
        CompletableFuture<String> getNowFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            return "supplyAsync返回结果";
        }, fixedThreadPool);
        System.out.println("2.4、getNow(备胎值) 如果计算完成就返回正常值，否则返回备胎值: " + getNowFuture.getNow("备胎值") + " 耗时：" + (System.currentTimeMillis() - start));

        // 2.5、getNow(备胎值) 如果计算完成就返回正常值，否则返回备胎值
        start = System.currentTimeMillis();
        getNowFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            return "supplyAsync返回结果";
        }, fixedThreadPool);
        try {
            Thread.sleep(1200);
        } catch (InterruptedException ignored) {
        }
        System.out.println("2.5、getNow(备胎值) 如果计算完成就返回正常值，否则返回备胎值: " + getNowFuture.getNow("备胎值") + " 耗时：" + (System.currentTimeMillis() - start));
    }
}
