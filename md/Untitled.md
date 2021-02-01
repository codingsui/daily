[TOC]
java多线程编程核心技术-》java并发编程实践-》JUC源码阅读 + 相关博客 -》做/答题

# 问题

## 并发与并行的区别

并发（concurrency）：把任务在不同的时间点交给处理器进行处理。在同一时间点，任务并不会同时运行。
并行（parallelism）：把每一个任务分配给每一个处理器独立完成。在同一时间点，任务一定是同时运行。

## Thread核心方法了解哪些

> https://www.cnblogs.com/tutubaobao/p/10050169.html

## Thread中断

> https://blog.csdn.net/weixin_42476601/article/details/83045889

| public static boolean interrupted | 测试当前线程是否已经中断。如果线程处于中断状态返回true，否则返回false。同时该方法将清除的线程的中断状态。即，如果连续两次调用该方法，则第二次调用将返回 false。该方法可用于清除线程中断状态使用。<br />```public static boolean interrupted() {    return currentThread().isInterrupted(true);}``` |
| --------------------------------- | ------------------------------------------------------------ |
| public boolean isInterrupted()    | 测试线程是否已经中断。线程的中断状态不受该方法的影响。<br />```public boolean isInterrupted() {    return this.isInterrupted(false);}``` |
| public void interrupt()           | 中断线程。<br />```public void interrupt() {    if (this != currentThread()) {        this.checkAccess();    }    Object var1 = this.blockerLock;    synchronized(this.blockerLock) {        Interruptible var2 = this.blocker;        if (var2 != null) {            this.interrupt0();            var2.interrupt(this);            return;        }    }    this.interrupt0();}``` |

## 钩子函数和回调函数

## 乐观锁和悲观锁
 - 乐观锁认为每次去拿数据的时候别人都不会操作这块数据，因此不用对数据进行上锁。而悲观锁认为每次去操作数据的时候别人都会修改这块数据，所以在每次拿数据的时候都加上锁。因此乐观锁适合多读场景，而悲观锁适合多写场景。乐观锁使用CAS算法和版本号实现，但是会引起ABA、自旋时间长、单共享变量的问题。
 - [详解](https://www.imooc.com/article/details/id/44217)
## synchronize和lock区别
 - 区别synchronize的作用于更大(方法，块)，实现原理是悲观锁，而lock实现原理是乐观锁
 - [深入研究 Java Synchronize 和 Lock 的区别与用法](https://blog.csdn.net/natian306/article/details/18504111)

## Unsafe类的作用
- 是java提供操作操作系统的类，大部分方法通过JNI调用底层方法实现
- [详情](https://www.cnblogs.com/mickole/articles/3757278.html)

## CAS
- CAS比较并交换算法，乐观锁策略，核心是以当前的内存地址V，旧的期望值A，与更新新值B，当满足当前A与V内的值相等时，在会将当前内存值A更改为B，否则不修改
- 缺点
    - 自旋时间过长
    - 只能维持一个共享的原子变量
    - ABA问题
- 与阻塞同同步机制的区别，无需切换线程上下文和唤醒操作，获取同一个锁的线程进入自旋进入非阻塞同步
- [面试必问的CAS](https://blog.csdn.net/v123411739/article/details/79561458)
- [什么是CAS机制？](https://blog.csdn.net/qq_32998153/article/details/79529704)

## Class 类 getField、getDeclaredField区别
- getField仅能获取类(或父类)的public成员 
- getDeclaredField仅能获取类本身的成员

## Field的getModifiers
- 获取方法或变量前面的修饰符
- 具体可以参考Modifier类

属性 | 值
---|---
public | 1
private | 2
protect | 4
static | 8
final | 16


## Thread.sleep与object.wait区别
- sleep是静态方法，wait是实例方法
- sleep使用没有限制，而wait方式必须在同步方法或者同步块内使用
- sleep只会让出cup不会释放对象锁，而wait方法会释放对象锁
- sleep在休眠时间到达时会等待获取cpu时间片继续执行，而wait方法需要notify或者notifyAll方法通知，才会进入就绪状态

## 守护线程的概念

## JMM（java 内存模型）
### 在JAVA中什么地方引出的JMM？
由于多线程之间可能出现线程不安全，而引发线程安全问题的核心就是内存数据不一致和指令的重排序，想要了解这两个问题的产生原因那么久需要了解JMM了
### 概念
java内存模型本质就是一种规则，用来约束程序中的共享变量的访问方式，为了屏蔽掉各种硬件/操作系统的内存访问差异让java程序运行在各种平台下都能打到内存一致性的效果。
### JMM的抽象模型 
实际就是在程序运行过程中，由于CPU的处理速度和主存的读写不在一个量级上，因此，共享变量存储在主存中，每个线程都有自己的工作内存，并会将主存中的共享变量拷贝到自己的工作内存上，之后的读写都使用工作内存上的变量副本，并在某个时刻将工作内存的变量副本写入到主存。
### 抽象示意图 
![image](https://user-gold-cdn.xitu.io/2018/4/30/16315b2410a9e3eb?imageView2/0/w/1280/h/960/format/webp/ignore-error/1)
    
### 内存间的交互操作
#### 抽象图
![image](https://oscimg.oschina.net/oscnet/536c6dfd-305a-4b95-b12c-28ca5e8aa043.png)
#### 具体操作
- read:将主存中的一个变量传输到工作内存中
- load:在read后执行，将read得到的值放入工作内存的变量副本中
- use:把工作内存中的值传入到执行引擎
- assign:将执行引擎接收到的值赋给工作内存的变量
- store:将工作内存中的值传送到主存中
- wtite:在store之后，将store获得的值写入主存的变量中
- lock:作用于主存
- unlock:作用于主存

### 重排序
#### 产生原因
为了提高性能，编译器和处理器常常会对指令进行重排序

遵守
> as-if-serial（不过怎样进行重排序，单线程程序的执行结果不能被改变）
#### 种类
- 编译器重排序
- 指令级的重排序
- 内存系统的重排序

#### 条件
- 单线程环境不能改变指令的顺序
- 存在数据关系依赖的不能重排序
- 无法通过happens-before原则退出来的，才能进行重排序

#### JMM两种不同性质的重排序
![image](https://user-gold-cdn.xitu.io/2018/4/30/16315b40eb50a329?imageView2/0/w/1280/h/960/format/webp/ignore-error/1)

### 相关博客
- [深入理解JMM](https://segmentfault.com/a/1190000019200613)

## happens-before(先行发生)规则
规则的含义是来阐述内存之间的可见性，实际上就是给编译器优化重排序加上约束，如果一个操作需要对另一个操作可见，那么这两个操作之间必然存在HB关系

具体规则
- 程序顺序规则：一个线程中的每个操作，happens-before于该线程中的任意后续操作。
- 监视器锁规则：对一个锁的解锁，happens-before于随后对这个锁的加锁。
- volatile变量规则：对一个volatile域的写，happens-before于任意后续对这个volatile域的读。
- 传递性：如果A happens-before B，且B happens-before C，那么A happens-before C。
- start()规则：如果线程A执行操作ThreadB.start()（启动线程B），那么A线程的ThreadB.start()操作happens-before于线程B中的任意操作。
- join()规则：如果线程A执行操作ThreadB.join()并成功返回，那么线程B中的任意操作happens-before于线程A从ThreadB.join()操作成功返回。
- 程序中断规则：对线程interrupted()方法的调用先行于被中断线程的代码检测到中断时间的发生。
- 对象finalize规则：一个对象的初始化完成（构造函数执行结束）先行于发生它的finalize()方法的开始。
- 一个happens-before规则对应于一个或多个编译器和处理器重排序规则。对于Java程序员来说，happens-before规则简单易懂，它避免Java程序员为了理解JMM提供的内存可见性保证而去学习复杂的重排序规则以及这些规则的具体实现方法

## DCL(Double Check Lock)问题
看着这个名字就可以想起单例模式的一种双重检查锁模式
```
public class LazySingleton {  
    private int someField;  
      
    private static LazySingleton instance;  
      
    private LazySingleton() {  
        this.someField = 5;         // (1)  
    }  
      
    public static LazySingleton getInstance() {  
        if (instance == null) {                               // (2)  
            synchronized(LazySingleton.class) {               // (3)  
                if (instance == null) {                       // (4)  
                    instance = new LazySingleton();           // (5)  
                }  
            }  
        }  
        return instance;                                      // (6)  
    }  
      
    public int getSomeField() {  
        return this.someField;                                // (7)  
    }  
}  
```
- 情况分析
    1.  A和B线程同时进入(2)
    2.  A先获得锁，进入(3)，进行判断(4)进入(5)
    3.  开始执行instance = new LazySingLeton(),可能JVM先分配一块空内存给instance，还未进行初始化，A释放锁(下面是初始化过程，由于JVM可能会对指令进行重排序，导致instance引用未被完全初始化)
        1. memory＝allocate() //分配内存
        2. ctorInstance（memory）； //对象初始化
        3. instance＝memory；    //设置instance指向刚被分配的内存；  
    4.  B获得锁检测instance != null，释放锁，获得instacne对象
    5.  B开始调用getSomeField()方法但是获得得instance未进行初始化，someField=0，与预期的期望不符，**出现bug**
- 解决方法
    1. 静态内部类（推荐）
    2. volatile变量
- 博客
    - [详解](https://www.iteye.com/topic/260515) 
    

## java对象头
### 介绍
java对象存储在堆空间中，一个java对象包含对象头，对象体和对齐字节。
![image](https://img-blog.csdnimg.cn/20190115141050902.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1NDRE5fQ1A=,size_16,color_FFFFFF,t_70)

各部分作用
- Mark Word（标记字）：存储对象的锁状态，还可以配和GC，存储对象的hashcode
- Klass Word：指向方法区中class对象的指针，表名该对象随时可以知道自己是哪一个class的实例
- 数组长度：是可选的，只有当对象是数组时才有这部分
- 对象体：存储对象的属性和值
- 对齐字节：为了减少对内存的碎片空间

[详解博客](https://blog.csdn.net/scdn_cp/article/details/86491792)

### Mark Word（标记字）
![image](https://img-blog.csdnimg.cn/20190111092408622.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xpdWR1bl9jb29s,size_16,color_FFFFFF,t_70)

![image](https://img-blog.csdnimg.cn/20190115142040348.png)

### 锁的分级
#### 无锁状态
#### 偏向锁
当一个线程访问同步块并获取锁时，会在对象头和栈帧中的锁记录里存储锁偏向的线程ID，以后该线程在进入和退出同步块时不需要进行CAS操作来加锁和解锁，只需简单地测试一下对象头的Mark Word里是否存储着指向当前线程的偏向锁。如果测试成功，表示线程已经获得了锁。如果测试失败，则需要再测试一下Mark Word中偏向锁的标识是否设置成1（表示当前是偏向锁）：如果没有设置，则使用CAS竞争锁；如果设置了，则尝试使用CAS将对象头的偏向锁指向当前线程
- ![image](https://user-gold-cdn.xitu.io/2018/4/30/16315cb9175365f5?imageView2/0/w/1280/h/960/format/webp/ignore-error/1)

####  轻量级锁
线程在执行同步块之前，JVM会先在当前线程的栈桢中创建用于存储锁记录的空间，并将对象头中的Mark Word复制到锁记录中，官方称为Displaced Mark Word。然后线程尝试使用CAS将对象头中的Mark Word替换为指向锁记录的指针。如果成功，当前线程获得锁，如果失败，表示其他线程竞争锁，当前线程便尝试使用自旋来获取锁。
![image](https://user-gold-cdn.xitu.io/2018/4/30/16315cb9193719c2?imageView2/0/w/1280/h/960/format/webp/ignore-error/1)
#### 重量级锁

### 各种锁比较

锁 | 优点 | 缺点 | 适用场景
---|---|---|---
偏向锁| 加锁和解锁不需要消耗额外的资源，和执行非同步方法相比只有纳秒级的差距 | 如果线程间存在锁的竞争会带来额外撤销所的 | 适合单线程获取同步锁
轻量级锁 | 竞争锁不会发生阻塞，提高程序的响应速度 | 如果始终得不到锁的竞争，会导致自旋消耗CPU | 追求响应时间，同步块执行速度非常快
重量级锁| 不会发生自旋，不会消耗CPU | 线程阻塞，响应时间长 | 追求吞吐量，同步块执行时间长



## volatile
volcatile是一种乐观锁机制，采用CAS算法实现对共享变量的同步，对于volcatile变量，每个线程对于变量的读写操作都需要同步主存，不在自己的工作内存直接读取和操作

下面就展示了一个变量读取同步例子
```
public class A {
    private static  boolean isOver = false;

    public static void main(String[] args) {
        Thread thread = new Thread(()->{
                while (!isOver);
        });

        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        isOver = true;

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end");
    }

}

```


## concurrent包的结构层次
![image](https://user-gold-cdn.xitu.io/2018/5/3/163260cff7cb847c?imageView2/0/w/1280/h/960/format/webp/ignore-error/1)

## 初识AQS
AQS是同步器，java提供的一种能够


```
graph TDd

A(acquire开始执行)-->B{获取同步状态}
B -->|成功|C(结束)
subgraph addWait方法
B-->|失败|newNode[当前线程包装为Node]
newNode-->isTailNode{同步队列尾结点是否为null}
isTailNode-->|否|CASTail{直接将当前节点CAS尾插入到同步队列}
subgraph enq方法
isTailNode-->|是|initTail{尾结点是否为null}
initTail-->|是|initHead{CAS设置头结点}
initHead-->|失败|initTail
initHead-->|成功|D[尾结点=头结点]
D-->initTail
initTail-->|否|E{设置尾结点}
E-->|失败|initTail
end
subgraph acquireQueued方法
E-->|成功|F{前驱节点是头结点 & 获取锁成功}
F-->|是|C
F-->|否|d
end
end

```


- [AQS相关方法调用+解释](https://www.jianshu.com/p/9421028f821b)

## java中>、>>、>>>的含义
1.  '>',在数字间是判断大小的,结果是boolean
eg:
```
int a = 1;
int b = 2;
if(a > bw){
    ...
}
```

2. <<代表左移、>>代表右移,在数字中就是将整数变为二进制数，然后移位，在移位过程中是区分正负号的

3. '>>>'无符号移动

## ^、| 、&、~
1. ^ 异或运算
2. | 或晕眩
3. & 与运算
4. ~ 非运算

## ConcurrentHashMap

- [ConcurrentHashMap相关方法介绍](https://segmentfault.com/a/1190000015907000) 
- [ConcurrentHashMap1.7与1.8区别](https://blog.csdn.net/weixin_44460333/article/details/86770169) 