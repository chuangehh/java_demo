package com.juc2024.completablefuture;

import java.util.concurrent.*;

/**
 * CompletableFuture Test
 * <p>
 * 1、构建异步任务的2类方法，注意线程池参数，生产一般自定义线程池
 * pool-1-thread-1 runAsync()
 * 1.1、runAsync(Runnable, 线程池),无返回结果 null
 * 1.2、supplyAsync(Supplier, 线程池),有返回结果 supplyAsync返回结果
 * 1.3、如果没有传入自定义线程池，都用默认线程池ForkJoinPool
 *
 * <p>
 * 2、获得结果
 * 2.1、get() 阻塞获取结果: supplyAsync返回结果 耗时：1004
 * 2.2、get(long timeout, TimeUnit unit) 阻塞获取结果, 如果超时会抛超时异常: supplyAsync返回结果 耗时：514 java.util.concurrent.TimeoutException
 * 2.3、join() 和get作用一样，只是不需要显示抛异常: supplyAsync返回结果 耗时：1007
 * 2.4、getNow(备胎值) 如果计算完成就返回正常值，否则返回备胎值: 备胎值 耗时：1
 * 2.5、getNow(备胎值) 如果计算完成就返回正常值，否则返回备胎值: supplyAsync返回结果 耗时：1208
 * <p>
 * 3、串行计算的几种方法
 * 3.1 thenApply 依赖上一个任务，如果当前步骤出错就结束，下一步如果使用返回null  thenApply多个一组，一损俱损 uniApplyStage
 * 3.2 handle 依赖上一个任务，如果当前步骤出错不影响大局接着埋头苦干，下一步如果使用返回null  handle多个一组，一荣俱荣 uniHandleStage
 * 3.3 exceptionally 依赖上一个任务 之前步骤有异常时调用
 * 3.4 whenComplete 依赖上一个任务 之前任务完成时调用，只拿结果，handle = exceptionally + whenComplete
 * 3.5 thenAccept(Consumer) 依赖上一个任务, 消费上一步结果，不返回值
 * 3.6 thenRun(Runnable)  依赖上一个任务，不需要上一步结果，不返回值
 *
 * 4、谁快用谁、合并
 */
public class CompletableFutureTest {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);


        // 1、构建异步任务的2类方法，注意线程池参数，生产一般自定义线程池
        // buildCompletableFuture(fixedThreadPool);

        // 2、获得结果和触发计算
        // getResult(fixedThreadPool);

        // 3、串行计算的几种方法
        // processResult(fixedThreadPool);

        // 4、谁快用谁、合并
        CompletableFuture<String> playA = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("A come in 睡2秒");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "荣民去vivo了";
        }, fixedThreadPool);

        CompletableFuture<String> playB = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("B come in 睡3秒");
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "陈铨去货拉拉了";
        }, fixedThreadPool);

        System.out.println(playA.applyToEither(playB, e -> "applyToEither()谁快用谁 先行者：" + e).join());
        System.out.println(playA.thenCombine(playB, (a, b) -> "thenCombine()合并2个异步线程 都去大厂了：" + a + "+" + b).join());


        fixedThreadPool.shutdown();
    }

    private static void processResult(ExecutorService fixedThreadPool) throws InterruptedException {
        // 3.1 thenApply 依赖上一个任务，如果当前步骤出错就结束，下一步如果使用返回null  thenApply多个一组，一损俱损 uniApplyStage
        // 3.2 handle 依赖上一个任务，如果当前步骤出错不影响大局接着埋头苦干，下一步如果使用返回null  handle多个一组，一荣俱荣 uniHandleStage
        // 3.3 exceptionally 依赖上一个任务 之前步骤有异常时调用
        // 3.4 whenComplete 依赖上一个任务 之前任务完成时调用，只拿结果，handle = exceptionally + whenComplete
        // 3.5 thenAccept(Consumer) 依赖上一个任务, 消费上一步结果，不返回值
        // 3.6 thenRun(Runnable)  依赖上一个任务，不需要上一步结果，不返回值
        long start = System.currentTimeMillis();
        CompletableFuture<Void> exceptionally = CompletableFuture.supplyAsync(() -> "我一个人", fixedThreadPool)
                .thenApply(e -> e + " thenApply()和新的大数据兄弟成为好朋友")
                .thenApply(e -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    return e + " thenApply()大数据的兄弟走了!111" + (10 / 0);
                })
                .thenApply(e -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    return e + " thenApply()大数据的兄弟走了!222";
                })
                .handle((e, t) -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    if (t != null) {
                        t.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " handle() sleep1秒 如果上游失败了，输入参数e是null,当前线程独善其身\n");
                    int i = 10 / 0;
                    return "sleep2秒 handle()如果上游失败了，输入参数e是null,当前线程独善其身\n";
                })
                //.handle((e, t) -> e + " handle()新来的爬虫小哥说做我的兄弟，我泪目了222")
                .whenComplete((e, t) -> {
                    System.out.println("whenComplete()" + e);
                })
                .exceptionally((e) -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }

                    System.out.println(Thread.currentThread().getName() + " exceptionally() sleep2秒  如果handle把异常吃了，就轮不到我打印异常了" + e.getMessage());
                    return null;
                })
                .thenAccept(e -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " thenAccept() 再次打印结果");
                })
                .thenRun(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " thenRun() 不需要上一步的结果");
                });


        System.out.println(Thread.currentThread().getName() + "主线程去玩");
        System.out.println("exceptionally.join() " + exceptionally.join() + " 耗时：" + (System.currentTimeMillis() - start));
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

        // 2.6、complete(备胎值)  打断get方法立即返回括号值
        start = System.currentTimeMillis();
        getNowFuture = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
            return "supplyAsync返回结果";
        }, fixedThreadPool);
        try {
            Thread.sleep(500);
            // Thread.sleep(1200);
        } catch (InterruptedException ignored) {
        }
        System.out.println("2.6、complete(备胎值)  打断get方法立即返回括号值: " + getNowFuture.complete("备胎值") + " 耗时：" + (System.currentTimeMillis() - start));
    }
}
