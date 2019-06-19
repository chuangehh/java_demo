# JUC (java concurrent util) Java 并发包

#### 请你谈谈你对volatile关键字的认识?
* volatile是Java虚拟机提供的轻量级同步机制
    * 保证可见性
        * demo code: com.lcc.juc.volatiles.VolatileSeeDemo
    * 不保证原子性
        * demo code: com.lcc.juc.volatiles.VolatileAtomicDemo
    * 禁止指令重排
        * 指令重排序: 源代码 > 编译器优化重排 > 指令并行重排 > 内存系统重排
        * 指令重排导致单例模式失效 demo code: com.lcc.juc.volatiles.VolatileInstructResetDemo
         
* JMM你谈谈
    * JMM(Java内存模型Java Memory Model) 它描述的是一组规范,通过这组规范定义程序中的各个变量(实例、静态字段和构成数组对象的元素)的访问方式
    * JMM关于同步规定
        * 线程加锁前,必须把共享变量从主内存同步到工作内存中
        * 线程解锁前,必须把共享变量从工作内存同步到主内存中
        * 加锁、解锁是同一把锁
    * 内存模型规定,所有变量存储在主内存上,线程对变量的操作在工作内存中进行,首先要把变量从主内存拷贝到自己的工作内存,对工作内存变量噼里啪啦一顿操作后,在将工作内存的值写会主内存
    ![avatar](https://pic2.zhimg.com/v2-7abd7500588012315f4f0e068e20e341_b.jpg)
        
* 你在那些地方用到过volatile? 
    * 单例模式
    

#### CAS你知道吗?
* 比较并交换
    * demo code: com.lcc.juc.cas.CASDemo 
* CAS底层原理? 如果知道,谈谈你对UnSafe的理解
    * Compare-And-Swap, [它是一条CPU并发原语](https://github.com/unofficial-openjdk/openjdk/blob/jdk8u/jdk8u/hotspot/src/share/vm/prims/unsafe.cpp#L1233)
    * Atomic::cmpxchg(x, addr, e) x:即将更新的值,e:原内存的值
* CAS缺点
    * 循环时间长cpu开销大
    * 只能保证一个共享变量的原子操作
    * ABA问题
        

#### AtomicInteger的ABA问题谈谈? 原子更新引用知道吗?
* 原子引用 demo code: com.lcc.juc.cas.CASAtomicReferenceDemo
* 解决: 每一次变动增加一个版本号,(expectedReference:期望值,newReference:新值,expectedStamp:期望版本号,newStamp:新版本号) demo code: com.lcc.juc.cas.CASAtomicStampedReferenceDemo


#### 我们知道ArrayList是线程不安全,请编码一个不安全的案例并给出解决方案
* demo code: com.lcc.juc.collections.CollectionsConCurrentModificationException
* ArrayList默认数组长度=10,扩容机制1.5倍oldCapacity + (oldCapacity >> 1) @see java.util.ArrayList#grow
        
        
#### 公平锁/非公平锁/可重入锁/递归锁/自旋锁谈谈你的理解?请手写一个自旋锁
* 公平锁: 线程排队使用资源
* 非公平锁: 开始就试图抢占资源,如果失败了会像公平锁一样进入队列
    * 优点: 吞吐量较高,能充分利用cpu
    * 缺点: 容易造成线程饥饿
    * synchronized 与 ReentrantLock 就是典型的非公平锁
* 可重入锁与递归锁是一个概念
    * 线程可以自动进入拥有该锁的代码块
* 自旋锁
    * demo code: com.lcc.juc.lock.SpinLockDemo
    * 本质上是CAS,不断的进行比较并交换,直到成功为止
    * 优点是减少了线程的上下文切换
    * 缺点是如果一直失败对cpu损耗较大
* 独占锁(写锁)/共享锁(读锁)/互斥锁
    * 独占锁: 该锁只能被一个线程持有,比如 synchronized,ReentrantLock都是独占锁
    * 共享锁: 该锁可以被多个线程持有,能保证并发读是非常高效的。读写,写读,写写过程是互斥的
        * demo code: com.lcc.juc.lock.ReentrantReadWriteLockDemo
  
 
#### CountDownLatch/CyclicBarrier/Semaphore使用过吗?
* demo code: com.lcc.juc.util.CountDownLatchDemo
* demo code: com.lcc.juc.util.CyclicBarrierDemo
* demo code: com.lcc.juc.util.SemaphoreDemo


#### 阻塞队列知道吗?
* 队列+阻塞队列
    * 队列: 先进先出,可以用数组,也可以用链表实现
    * 阻塞队列
        * ArrayBlockingQueue 一个基于数组实现有界阻塞队列
        * LinkedBlockingQueue 一个基于链表实现的无界阻塞队列,吞吐量要大于ArrayBlockingQueue,最大值是Integer.MAX_VALUE
        * SynchronousQueue 一个基于不实际存储元素的阻塞队列,只存放一个元素的阻塞队列
        * PriorityBlockingQueue, 一个基于数组实现的支持优先级排序的无界阻塞队列
        * DelayQueue, 一个基于数组使用优先级队列实现的无界阻塞队列
        * LinkedBlockingDeque, 一个基于链表实现的双向阻塞队列
        * LinkedTransferQueue, 一个基于链表实现的无界阻塞队列
* 为什么要用,有没有好的一面?
    * 阻塞队列有没有好的一面? 餐厅吃饭排队,一个个来,抢占式还不疯了
    * 不得不阻塞,该怎么管理? 使用阻塞队列
* BlockingQueue 的核心方法
    * demo code: com.lcc.juc.blockingqueue.BlockingQueueDemo




#### 线程池用过吗? ThreadPoolExecutor谈谈你的理解?


#### 线程池用过吗? 生产上你如何设置合理参数


#### 死锁编码及定位分析



