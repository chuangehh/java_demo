# JVM Java虚拟机

#### JVM内存结构
* JVM体系结构 
    * 类加载子系统
        * 有哪些类加载器?
            ```
                启动类加载器，根加载器
                扩展类加载器
                应用加载器
                自定义加载器
            ```
        * 双亲委机制 
        * 类加载的沙箱机制
    * jdk7 -> jdk8 JVM 
        * http://openjdk.java.net/jeps/122
        * 永久代 -> 元空间
    * ![avatar](./src/main/resources/img/jvm.jpg)

#### GC的作用域
* 方法区
* 堆

#### 常见的垃圾回收算法
* 引用计数
    * 缺点: 每次对象赋值都要维护引用计数器,有一定消耗
    * 缺点: 较难处理循环引用,(互相引用)
    * JVM实现一般不采用
* 复制
    * MinorGC过程 (复制->清空->互换)
        * eden、SurvivorFrom 复制到SurvivorTo(如果有已经达到老年标准,赋值到老年代),年龄+1
        * 清空eden、SurvivorFrom
        * SurvivorTo和SurvivorFrom 身份互换
    * 优点: 没有产生内存碎片
    * 缺点: 有点浪费空间,大对象复制耗时    
* 标记清除
    * 先标记出要回收的对象,然后统一回收这些对象
    * 优点: 没有大面积去复制,节省空间
    * 缺点: 导致内存碎片
* 标记整理
    * 标记
        * 与标记-清除一样
    * 压缩
        * 再次扫描,并往一端滑动存活对象
        * 没有内存碎片
        * 需要移动对象的成本
* 没有最完美的算法,分代收集        

#### JVM垃圾回收的时候如何确定垃圾? 是否知道什么是GC Roots
* 什么是垃圾: 内存中已经不被使用到的对象
* 如何判断一个对象可以被回收? 
    * 引用计数法(不使用)
    * 枚举根节点做可达性分析(GC Roots)
        * 一系列名为 "GC Roots"的对象作为起始点,能遍历到的(可达的)对象判定为存活,反之判定死亡
        * 虚拟机栈中引用的对象
        * 方法区中的类静态属性引用对象
        * 方法区中常量引用对象
        * 本地方法栈中JNI(Native的方法)引用的对象
        * demo code: com.lcc.jvm.GCRootDemo

#### 你说你做过JVM调优和参数配置,请问如何盘点查看JVM系统默认值
* JVM的参数类型
    * 标配参数 (-version -help)
    * X参数
        * -Xint 解释执行
        * -Xcomp 第一次使用就编译成本地代码
        * -Xmixed 混合模式
    * XX参数
        * Boolean类型
            * -XX:+
            * -XX:-
            * Case 
                ```
                demo code: com.lcc.jvm.HelloGC
                    -XX:+PrintGCDetails
                jps -l
                    5236 com.lcc.jvm.HelloGC
                jinfo -flag PrintGCDetails 5236
                    -XX:+PrintGCDetails
                ```
        * KV设值类型
            * -XX:属性key=属性value
            * Case
                ```
                jinfo -flag MetaspaceSize 5236
                    -XX:MetaspaceSize=21807104  (20MB)
                jinfo -flag MaxTenuringThreshold 5236
                    -XX:MaxTenuringThreshold=15
                    
                demo code: com.lcc.jvm.HelloGC
                    -XX:MetaspaceSize=1G
                    -XX:MaxTenuringThreshold=14
                jps -l
                    4400 com.lcc.jvm.HelloGC
                jinfo -flag MetaspaceSize 4400
                    -XX:MetaspaceSize=1073741824
                jinfo -flag MaxTenuringThreshold 4400   
                    -XX:MaxTenuringThreshold=14
                ```
        * jinfo -flag xxx pid: 该进程一个参数
        * jinfo -flags pid: 该进程所有参数
            * Non-default VM flags: 根据操作系统自身改的一些默认参数
            * Command line: 手动填的一堆参数
        * 两个金典参数-Xms和-Xmx
            * -Xms: 等价于-XX:InitialHeapSize
            * -Xmx: 等价于-XX:MaxHeapSize
* 盘点家底查看JVM默认值
   * -XX:PrintFlagsInitial
        * 查看初始默认值
        * java -XX:+PrintFlagsInitials > jvm.init
   * -XX:PrintFlagsFinal
        * 查看修改更新
        * java -XX:+PrintFlagsFinal -version > jvm.final
        * := 代表修改后的值
   * PrintFlagsFinal举例,运行java命令的同时打印出参数: demo code: com.lcc.jvm.HelloGC
   * -XX:+PrintCommandLineFlags: 查看命令行参数,主要用来查看jvm垃圾回收器            
   
#### 你平时工作用过的JVM常用基本配置参数有那些?
* JDK 文档 
    * https://docs.oracle.com/en/java/javase/12/
    * 工具类文档搜索tool
* JDK 1.8基础知识
    * JDK 1.8后将永久代取消了，由元空间取代
      ![avatar](src/main/resources/img/java7-gc.png)
      
      ![avatar](src/main/resources/img/java8-gc-metaspace.png)
    * 永久代与元空间本质区别
        * 永久代使用的是jvm堆内存
        * 元空间使用的是本机物理内存,元空间大小仅受本地内存限制
            * 类的元数据放入native memory
            * 字符串和类的静态变量放入java堆中
            * 加载多少元数据不在受MaxPermSize控制,而是由系统实际可用空间来控制
* 常用参数
    * -Xms 
        * 初始大小内存,默认物理内存1/64
        * 等价于 -XX:InitialHeapSize
    * -Xmx
        * 最大分配内存,默认物理内存1/4
        * 等价于 -XX:MaxHeapSize
    * -Xss
        * 设置单个线程栈的大小,默认512K - 1024K(跟随操作系统)
        * 等价于 -XX:ThreadStackSize
        * 如果为0代表使用默认值
        * ![avatar](src/main/resources/img/jvm-Xss.jpg)
    * -XX:MetaSpaceSize
        * 设置元空间大小,元空间不在虚拟机中,而是使用本地内存
        * -XX:MetaspaceSize=1024m -XX:+PrintCommandLineFlags -XX:+PrintFlagsInitial -XX:+PrintFlagsFinal
    * 典型案例
        * -Xms128m -Xmx4096m -Xss1024k -XX:MetaspaceSize=1024m -XX:PrintCommandLineFlags -XX:PrintGCDetails -XX:+UseSerialGC
    * --XX:PrintGCDetails
        * GC收集日志信息 demo code: com.lcc.jvm.GCDetailDemo
        * ![avatar](src/main/resources/img/gc.jpg)
        * ![avatar](src/main/resources/img/full-gc.jpg)
    * -XX:SurvivorRatio
        * 设置新生代 Eden和 S0/S1空间的比例
        * 默认: -XX:SurvivorRatio=8  Eden:S0:S1 =8:1:1
        * 假如: -XX:SurvivorRatio=4  Eden:S0:S1 =4:1:1
        * SurvivorRatio值为多少eden占比多少,SO,S1一样
        *  -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseSerialGC
    * -XX:NewRatio
        * 配置新生代与老年代堆占比
        * 默认: -XX:NewRatio=2 新生代1,老年代2 年轻代占1/3
        * 假如: -XX:NewRatio=5 新生代1,老年代5 年轻代占1/6
    * -XX:MaxTenuringThreshold
        * 默认进入老年代年龄 15
        * 设置为0,年轻代对象不经过Survivor区,直接进入老年代 对于老年代比较多的应用可以提高效率
        * 设置超过15会报错: must be between 0 and 15
        
       
#### 强引用、软引用、弱引用、虚引用分别是什么?
* 整体架构
    ![avatar](src/main/resources/img/reference.jpg)
* 强引用(默认)
    * 当内存不足,JVM开始垃圾回收,对于强引用的对象 OOM也不会对该对象进行回收,死都不收
    * 强引用是造成Java内存泄露的主要原因之一
    * demo code: com.lcc.jvm.ref.StrongReferenceDemo
* 软引用
    * 当内存充足不回收,内存不足时回收
    * 需要 java.lang.ref.SoftReference 来实现
    * demo code: com.lcc.jvm.ref.SoftReferenceDemo
* 弱引用
    * 每次GC都会进行回收
    * 需要 java.lang.ref.WeakReference 来实现
    * demo code: com.lcc.jvm.ref.WeakReferenceDemo
    * 使用过WeakHashMap吗? demo code: com.lcc.jvm.ref.WeakHashMapDemo
* 虚引用
    * 使用 getReference获取不到引用,需要配合引用队列来使用
        * DirectByteBuffer是通过虚引用来实现堆外内存的释放的
    * demo code: com.lcc.jvm.ref.PhantomReferenceDemo


#### 请谈谈你对OOM的认识
* java.lang.StackOverflowError
    * 栈溢出
    * demo code: com.lcc.jvm.oom.StackOverflowErrorDemo
* java.lang.OutOfMemoryError: Java heap space
    * 堆溢出
    * demo code: com.lcc.jvm.oom.JavaHeapSpaceDemo
* java.lang.OutOfMemoryError: GC overhead limit exceeded
    * jvm 98%的时间做GC, 2%的时间做工作
    * ![avatar](src/main/resources/img/gc-overhead-limit-exceeded.png)
    * demo code: com.lcc.jvm.oom.OverheadLimitExceededDemo
    * -XX:-UseGCOverheadLimit 使用该参数会造成什么后果呢?
        * GC执行后内存又快速被填满,迫使GC再次执行,形成恶性循环
        * cpu持续100% 却没有任何效果
* java.lang.OutOfMemoryError: Direct buffer memory
    * 写NIO 程序经常使用ByteBuffer来读取或者写入数据,这是一种基于通道(Channel)与缓冲区(Buffer)的I/O方式
    * 使用native 函数库分配堆外内存,通过java堆中DirectByteBuffer操作这块内存,这样能显著提升性能,避免了Java堆和native堆中来回复制
    * 如果不断分配本地内存,本地内存快满了,Java堆内存使用很少,就不会回收DirectByteBuffer对象,GC以及重试回收堆外内存9次还是不行报错误
    * com.lcc.jvm.oom.DirectBufferMemoryDemo
      ```java
      class Demo{
          public static void main(String[] args){
              // 分配Java对内存,GC管辖,需要拷贝速度慢
              ByteBuffer.allocate(capacity);
              // 分配OS本地内存,非GC管辖,不需要拷贝速度快
              ByteBuffer.allocateDirect(capacity);
          }
      }  
      ```
* java.lang.OutOfMemoryError: unable to create new native thread
    * 不能创建更多本地线程
    * demo code: com.lcc.jvm.oom.UnableToCreateNewNativeThreadDemo
* java.lang.OutOfMemoryError: Metaspace
    * 在Java8中,将之前 PermGen 中的所有内容, 都移到了 Metaspace 空间。例如: class 名称, 字段, 方法, 字节码, 常量池, JIT优化代码, 等等。
    * 错误的主要原因, 是加载到内存中的 class 数量太多或者体积太大。
    * demo code: com.lcc.jvm.oom.MetaspaceDemo


#### GC垃圾回收算法和垃圾收集器的关系? 分别是什么请你谈谈
* GC算法: 引用计数/复制/标记清除/标记整理,是回收方法论,垃圾收集器是具体实现
* 没有最完美的垃圾收集器,针对具体应用用最合适的垃圾收集器进行分代回收
* 4种类型的垃圾收集器
    * ![avatar](src/main/resources/img/type-of-java-gc.jpg)
    * https://javapapers.com/java/types-of-java-garbage-collectors/
    * 串行垃圾收集器
        * 使用一个线程进行垃圾收集
        * 执行垃圾回收时停止所有用户线程(Stop the World)
        * 适合简单的命令行程序
        * -XX:+UseSerialGC
    * 并行垃圾收集器
        * 使用多个线程进行垃圾收集
        * 执行垃圾回收时停止所有用户线程(Stop the World)
        * jdk8 默认垃圾收集器
    * CMS垃圾收集器
        * 用户线程和垃圾收集线程可以并行
        * 适用于对响应有要求的场景
        * XX:+USeParNewGC / -XX:+UseConcMarkSweepGC
    * G1垃圾收集器
        * 将堆内存分割成不同区域,然后并发进行垃圾回收

#### 怎么查看服务器默认的垃圾收集器是那个? 生产上如何配置垃圾收集器的? 谈谈你对垃圾收集器的理解?
* 查看默认垃圾收集器: java -XX:+PrintCommandLineFlags -version
* 默认垃圾收集
    * 新生代
        * UseSerialGC
        * UseParNewGC
        * UseParallelGC
    * 老年代
        * UseSerialOldGC
        * UseParallelOldGC
        * UseConcMarkSweepGC
    * UseG1GC
    * ![avatar](src/main/resources/img/jvm-gctype.png)
* 垃圾收集器
    * 参数说明
        * DefNew: Default New Generation
        * Tenured: Old
        * ParNew: Parallel New Generation
        * PSYoungGen: Parallel Scavenge
        * ParOldGen: Parallel Old Generation
    * Server/Client 什么意思?
        * 32位Window 操作系统,不论硬件如何JVM都是Client模式
        * 32位其他 操作系统,2G内存同时2个cpu以上用Server模式,低于该配置用Client模式
        * 64位 only server模式
    * 新生代
        * 串行GC(Serial)/(Serial Copying)
        * 并行GC(ParNew)
        * 并行回收GC(Parallel)/(Parallel Scavenge)
    * 老年代
        * 串行GC(Serial Old)/(Serial MSC)
        * 并行GC(Parallel Old)/(Parallel MSC)
        * 并发标记清除GC(CMS)
* 如何选择垃圾收集器
    * 单CPU或小内存,单机程序: -XX:+UseSerialGC
    * 多CPU,需要大量吞吐,如大数据: -XX:+UseParallelGC / -XX:+UseParallelOldGC
    * 多CPU,追求停顿时间短,快速响应的互联网应用: -XX:+UseConcMarkSweepGC
    * ![avatar](src/main/resources/img/jvm-gctype-chooes.png)
    * demo code: com.lcc.jvm.GCDemo
        

#### G1垃圾收集器
* 以前收集器的特点
    * 年轻代和老年代是各自独立的连续内存块
    * 年轻代使用eden+S0+S1 复制算法进行垃圾回收
    * 老年代的垃圾收集必须遍历整个内存区域
    * 设计原则: 尽可能少且快的进行垃圾回收
* G1是什么
    * 是什么
        * G1 Garbage-First收集器,是一款面向服务端的收集器
        * 应用在多处理器和大容量内存环境中,在实现高吞吐量同时，尽可能满足垃圾收集暂停时间短的要求
        * 像CMS一样,GC回收与用户线程并行,设计目标: 取代CMS垃圾收集器(JDK9默认垃圾收集器已经是G1)
        * G1停顿时间添加了预测机制,用户可以指定期望停顿时间
    * 特点
        * 充分利用多CPU，多核环境硬件优势,尽量缩短STW时间
        * 整体采用标记-整理算法,局部使用复制算法,不产生内存碎片
        * 宏观上G1不在区分新生代和老年代,把内存划分成多个独立的区域
        * 依然在小范围进行年轻代和老年代区分
        * 不存在物理上的年轻代和老年代,只有逻辑上的分代概念,每个区域随着G1运行在不同代进行切换
* 底层原理
    * Region区域化垃圾收集器
        * 化整为零,避免全内存扫描,只需要按区域来扫描即可
        * 最大32MB * 2048 = 64G 
    * 回收步骤 ![avatar](src/main/resources/img/g1-gc.png)
    * 4步过程 ![avatar](src/main/resources/img/g1-step.png)
* 常用配置参数
    * -XX:+UseG1GC
    * -XX:G1HeapRegionSize=n  设置G1区域大小,范围是1MB~32MB    
    * -XX:MaxGCPauseMillis=n  设置期望停顿时间
* 和CMS相比优势
    * G1没有内存碎片
    * G1可以设置期望停顿时间
        

#### 生成环境服务器变慢,诊断思路和性能评估谈谈?

#### 假如生成环境出现CPU占用过高,请谈谈你的分析思路和定位

 