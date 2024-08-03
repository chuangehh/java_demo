# java demo例子

## 简介
```

```

## 分布式事务
* 可靠消息最终一致性MQ 2个银行转账 见dtc目录下的dtx_bank1、dtx_bank2项目


## JUC 2024
* CompletableFuture
    * 跨境平台产品比价, 怎么由多个平台穿心执行3秒 优化到1秒？ 见代码 ComletableFutureMallDemo
    * CompletableFuture 
        * 1、构建异步任务的2类方法，注意线程池参数，生产一般自定义线程池
        * 2、获得结果
        * 3、串行计算的几种方法
        * 4、谁快用谁、合并 
        * 生产问题： 高并发登录打爆服务器如何优化，答案：合并请求批处理，比如合并1秒的请求一起读取数据库返回
