# java_and_scala

## 简介
```
我是98年的，15年高中干了一半就出来学Java了
刚去北京培训班的时候，那时我是班力最小的只有17岁

第一家公司入职的时候我还不到18岁，真的很感激
期间做过Java工程师，大数据工程师，现在是一名基层管理负责大数据;Java后端;采集团队

最近几年我喜欢上了读书，读来读去 都是在读人，有趣

交个朋友，我的微信 chuanchuan768
```

## JUC 2024
* CompletableFuture
    * 跨境平台产品比价, 怎么由多个平台穿心执行3秒 优化到1秒？ 见代码 ComletableFutureMallDemo
    * CompletableFuture 
        * 1、构建异步任务的2类方法，注意线程池参数，生产一般自定义线程池
        * 2、获得结果
        * 3、串行计算的几种方法
        * 4、谁快用谁、合并 
        * 生产问题： 高并发登录打爆服务器如何优化，答案：合并请求批处理，比如合并1秒的请求一起读取数据库返回
