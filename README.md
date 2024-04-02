# java_and_scala

## JUC 2024
* 为什么要学CompletableFuture？
    * CompletableFuture的前身Future
        * 优点：能结合线程池显著提高程序性能
        * 缺点：①get方法会阻塞 ②isDone方法消耗CPU

## 2024年 少年回来了
```
我是98年，2015年，高中干了一半就出来学Java了
刚去北京培训班的时候，那时我是我们班最小的，刚满17岁
如今就要27了。。

第一家公司收留了我，那时我还不到18岁，真的很感激那个CTO
期间做过Java工程师，大数据工程师
现在是一名基层管理，负责大数据、Java后端、采集团队
最近几年我喜欢上了读书，读来读去 发现都是在读人，有趣

社会是个金字塔，是残酷的，只有学习才能武装保护想保护的人
我们可以做独一无二的自己，这是别人代替不了的
```
## 开启JUC的更新


## 项目结构
```sh
.
├── juc
│   ├── 请你谈谈你对volatile关键字的认识?
│   ├── CAS你知道吗?
│   ├── AtomicInteger的ABA问题谈谈? 原子更新引用知道吗?
│   ├── 我们知道ArrayList是线程不安全,请编码一个不安全的案例并给出解决方案
│   ├── 公平锁/非公平锁/可重入锁/递归锁/自旋锁谈谈你的理解?请手写一个自旋锁
│   ├── CountDownLatch/CyclicBarrier/Semaphore使用过吗?
│   ├── 阻塞队列知道吗?
│   ├── 线程池用过吗? ThreadPoolExecutor谈谈你的理解?
│   ├── 线程池用过吗? 生产上你如何设置合理参数
│   └── 死锁编码及定位分析
│
├── jvm
│   ├── JVM内存结构
│   ├── GC的作用域
│   ├── 常见的垃圾回收算法
│   ├── JVM垃圾回收的时候如何确定垃圾? 是否知道什么是GC Roots
│   ├── 你说你做过JVM调优和参数配置,请问如何盘点查看JVM系统默认值
│   ├── 你平时工作用过的JVM常用基本配置参数有那些?
│   ├── 强引用、软引用、弱引用、虚引用分别是什么?
│   ├── 请谈谈你对OOM的认识
│   ├── GC垃圾回收算法和垃圾收集器的关系? 分别是什么请你谈谈
│   ├── 怎么查看服务器默认的垃圾收集器是那个? 生产上如何配置垃圾收集器的? 谈谈你对垃圾好机器的理解?
│   ├── G1垃圾收集器
│   ├── 生成环境服务器变慢,诊断思路和性能评估谈谈?
│   └── 假如生成环境出现CPU占用过高,请谈谈你的分析思路和定位
│
├── nio
│   ├── 谈谈你对3大IO模型的理解
│   ├── 谈谈你对NIO三大组件的理解
│
├── mq
│   ├── 为什么要引入消息中间件?
│   ├── 谈谈消息中间件的定义和作用
│
├── architecture 架构
│   ├── 架构到底是指什么?
│   ├── 架构设计的目的
│
├── structures 数据结构/算法
│
├── design-patterns 设计模式
│
├── jdbc
│
├── mysql高级


```