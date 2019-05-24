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
