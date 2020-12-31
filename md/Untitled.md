[TOC]

# 基本概念

## 简述线程、程序、进程的基本概念。以及他们之间关系是什么?

- 进程:是程序的一次执行过程，一个进程就是一个程序，是程序运行的基本单位，因此进程是动态的。系统运行程序就是一个进程从创建、运行到消亡的过程。
- 线程:与进程类似，但是线程时更小的执行单位。一个进程在执行的过程中会产生若干线程，与进程不同的是同类的多个线程共享进程的堆和方法区资源，但每个线程有自己的程序计数器、虚拟机栈和本地方法栈，所以系统在产生一个线程，或是在各个线程之间作切换工作时，负担要比进程小得多，也正因为如此，线程也被称为轻量级进程。
- 程序:含有指令和静态文件，被存储在磁盘或者其他数据存储设备中，程序是静态的代码

对操作系统来说，线程是最小的执行单元，进程是最小的资源管理单元。

## 协程是什么？

是一种比线程更加轻量级的存在。正如一个进程可以拥有多个线程一样，一个线程也可以拥有多个协程。线程不同状态之间的转化是由JVM通过操作系统内核中的TCB模块来改变的，需要耗费一定的CPU资源，协程不是被操作系统内核所管理，而完全是由程序所控制。

## 进程间如何通信？

(1)管道(PIPE) 
通常指无名管道，管道是一种半双工的通信方式，数据只能单向流动，而且只能在具有亲缘关系的进程间使用。进程的亲缘关系通常是指父子进程关系。 
(2)命名管道(FIFO) 
有名管道也是半双工的通信方式，但是它允许无亲缘关系进程间的通信。 
(3)信号量(Semphore) 
信号量是一个计数器，可以用来控制多个进程对共享资源的访问。它常作为一种锁机制，防止某进程正在访问共享资源时，其他进程也访问该资源。 

(4)消息队列(MessageQueue) 
消息队列是消息的链表，存放在内核中。一个消息队列由一个标识符（即队列ID）来标识。消息队列克服了信号传递信息少、管道只能承载无格式字节流以及缓冲区大小受限等缺点。 
(5)共享内存(SharedMemory) 
指两个或多个进程共享一个给定的存储区。信号量+共享内存通常结合在一起使用，信号量用来同步对共享内存的访问。 
(6)Socket
套接字也是一种进程间通信机制，与其他通信机制不同的是，它可用于不同机器间的进程通信

## 多线程可能带来什么问题？

并发编程的目的就是为了能提高程序的执行效率提高程序运行速度，但是并发编程并不总是能提高程序运行速度的，而且并发编程可能会遇到很多问题，比如：内存泄漏、死锁、线程不安全等等。

### 什么是线程死锁?如何避免死锁?

> 死锁:多个线程同时被阻塞，它们中的一个或者全部都在等待某个资源被释放。由于线程被无限期地阻塞，因此程序不可能正常终止。

```
public class DeadLockDemo {
    private static Object resource1 = new Object();//资源 1
    private static Object resource2 = new Object();//资源 2

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (resource1) {
                System.out.println(Thread.currentThread() + "get resource1");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "waiting get resource2");
                synchronized (resource2) {
                    System.out.println(Thread.currentThread() + "get resource2");
                }
            }
        }, "线程 1").start();

        new Thread(() -> {
            synchronized (resource2) {
                System.out.println(Thread.currentThread() + "get resource2");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread() + "waiting get resource1");
                synchronized (resource1) {
                    System.out.println(Thread.currentThread() + "get resource1");
                }
            }
        }, "线程 2").start();
    }
}
Thread[线程 1,5,main]get resource1
Thread[线程 2,5,main]get resource2
Thread[线程 1,5,main]waiting get resource2
Thread[线程 2,5,main]waiting get resource1
```

### 如何避免线程死锁?

#### 产生死锁必须具备以下四个条件

1. 互斥使用，即当资源被一个线程使用(占有)时，别的线程不能使用
2. 不可抢占，资源请求者不能强制从资源占有者手中夺取资源，资源只能由资源占有者主动释放。
3. 请求和保持，即当资源请求者在请求其他的资源的同时保持对原有资源的占有。
4. 循环等待，即存在一个等待队列：P1占有P2的资源，P2占有P3的资源，P3占有P1的资源。这样就形成了一个等待环路。

避免死锁

1. 如果想要打破互斥条件，我们需要允许线程同时访问某些资源，这种方法受制于实际场景，不太容易实现条件；
2. 打破不可抢占条件，这样需要允许线程强行从占有者那里夺取某些资源，或者简单一点理解，占有资源的线程不能再申请占有其他资源，必须释放手上的资源之后才能发起申请，这个其实也很难找到适用场景；
3. 线程在运行前申请得到所有的资源，否则该线程不能进入准备执行状态。这个方法看似有点用处，但是它的缺点是可能导致资源利用率和进程并发性降低；
4. 避免出现资源申请环路，即对资源事先分类编号，按号分配。这种方式可以有效提高资源的利用率和系统吞吐量，但是增加了系统开销，增大了线程对资源的占用时间。

## 线程间如何通信？

- 锁与同步
- 等待/通知机制（使用Object类的wait() 和 notify() 方法）
- 基本LockSupport实现线程间的阻塞和唤醒
- 使用 volatile 关键字
- InheritableThreadLocal
- 使用JUC工具类 CountDownLatch
- 管道：管道是基于“管道流”的通信方式。JDK提供了PipedWriter、 PipedReader、 PipedOutputStream、 PipedInputStream。其中，前面两个是基于字符的，后面两个是基于字节流的。

## 线程的生命周期有哪些状态？怎么转换？

| 状态              | 说明                                                         |
| ----------------- | ------------------------------------------------------------ |
| NEW               | 初始状态，线程被构建但是还没有调用start方法                  |
| RUNNABLE（RUNNING | READY）                                                      |
| WAITING           | 等待状态，表示线程进入等待状态，进入该状态表示线程需要等待其他线程做出一些特定的动作（中断或者通知） |
| BLOCKED           | 阻塞状态，表示线程阻塞于锁                                   |
| TIME_WAITING      | 超时等待装填，不同于WAITING，可以在指定的时间返回            |
| TERMINATED        | 终止状态，表示线程已经执行完毕                               |

![image](https://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/96CDF3BD499A4628B378814C1035972A/15833)

## 关于锁池和等待池

在Java中，每个对象都有两个池，锁(monitor)池和等待池

- 锁池:假设线程A已经拥有了某个对象(注意:不是类)的锁，而其它的线程想要调用这个对象的某个synchronized方法(或者synchronized块)，由于这些线程在进入对象的synchronized方法之前必须先获得该对象的锁的拥有权，但是该对象的锁目前正被线程A拥有，所以这些线程就进入了该对象的锁池中。
- 等待池:假设一个线程A调用了某个对象的wait()方法，线程A就会释放该对象的锁(因为wait()方法必须出现在synchronized中，这样自然在执行wait()方法之前线程A就已经拥有了该对象的锁)，同时线程A就进入到了该对象的等待池中。如果另外的一个线程调用了相同对象的notifyAll()方法，那么处于该对象的等待池中的线程就会全部进入该对象的锁池中，准备争夺锁的拥有权。如果另外的一个线程调用了相同对象的notify()方法，那么仅仅有一个处于该对象的等待池中的线程(随机)会进入该对象的锁池.

## wait和sleep的区别？什么时候会用sleep？

区别：

- wait是Object类的方法，调用的时候会释放对象锁，进入等待池中，只有调用该对象的notify|notifyAll方法后才会重新进入锁定池中准备。
- sleep是Thread方法，调用的时候不会释放对象锁，暂停执行指定的时间，让出cpu该其他线程，当指定的时间到了又会自动恢复运行状态

什么时候会用sleep？
在等待某些条件初始化，或者延时执行的时候，看需要eg：访问频率

## 怎么停止线程？

- 使用标志位终止线程,    public volatile boolean exit = false; 
- 使用 interrupt() 中断线程

## 如何控制多个线程顺序？

1. join方法
2. 等待通知机制
3. 线程池
4. Condition条件变量
5. JUC并发包下的工具CountDownLatch、CyclicBarrier、Semaphore等

## 并发和并行的区别？

- 并行(parallel)：指在同一时刻，有多条指令在多个处理器上同时执行。所以无论从微观还是从宏观来看，二者都是一起执行的。
- 并发(concurrency)：指在同一时刻只能有一条指令执行，但多个进程指令被快速的轮换执行，使得在宏观上具有多个进程同时执行的效果，但在微观上并不是同时执行的，只是把时间分成若干段，使多个进程快速交替的执行。

## 什么是上下文切换？

CPU通过分配时间片来执行任务，当一个任务的时间片用完，就会切换到另一个任务。在切换之前会保存上一个任务的状态，当下次再切换到该任务，就会加载这个状态。上下文是指某一时间点 CPU 寄存器和程序计数器的内容，频繁的上下文切换可能带来大量的开销。

直接开销有如下几点：

- 操作系统保存回复上下文所需的开销
- 线程调度器调度线程的开销

间接开销：

- 处理器高速缓存重新加载的开销
- 上下文切换可能导致整个一级高速缓存中的内容被冲刷，即被写入到下一级高速缓存或主存

参考：https://blog.csdn.net/dh554112075/article/details/90696768

## 什么是JMM？

本质上是一种规则，用来约束程序中的共享变量的访问方式，为了屏蔽掉各种硬件和操作系统的内存访问差异，让Java程序在各个平台下都能达到一致访问内存的效果。定义了线程和主内存之间的抽象关系。

它规定了：所有变量都存储在主内存中，每个线程都有自己的工作内存，工作内存中存储了该线程使用到的变量的主内存副本的拷贝，线程对变量的操作全都是在工作内存中，无法直接对主内存和其他线程的工作内存的变量进行操作

![image](https://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/40355BA341DA4FE7B7BB8133B7320EDF/15950)

### 内存的交互操作有哪些？
lock、unlock、read、load、use、assign、store、write

- lock（锁定）：作用于主内存的变量，把一个变量标识为一条线程独占状态。
- unlock（解锁）：作用于主内存变量，把一个处于锁定状态的变量释放出来，释放后的变量才可以被其他线程锁定。
- read（读取）：作用于主内存变量，把一个变量值从主内存传输到线程的工作内存中，以便随后的load动作使用
- load（载入）：作用于工作内存的变量，它把read操作从主内存中得到的变量值放入工作内存的变量副本中。
- use（使用）：作用于工作内存的变量，把工作内存中的一个变量值传递给执行引擎，每当虚拟机遇到一个需要使用变量的值的字节码指令时将会执行这个操作。
- assign（赋值）：作用于工作内存的变量，它把一个从执行引擎接收到的值赋值给工作内存的变量，每当虚拟机遇到一个给变量赋值的字节码指令时执行这个操作。
- store（存储）：作用于工作内存的变量，把工作内存中的一个变量的值传送到主内存中，以便随后的write的操作。
- write（写入）：作用于主内存的变量，它把store操作从工作内存中一个变量的值传送到主内存的变量中。
![image](https://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/3FBC9B372E664B9BB033EBEB655831CE/15958)

参考：https://blog.csdn.net/liuguangqiang/article/details/52153096
### 什么是指令重排序？
为优化程序性能，对原有的指令执行顺序进行优化重新排序。重排序可能发生在多个阶段，比如编译重排序、CPU重排序等。


#### 指令重排序级别？
- 编译器优化的重排序。编译器在不改变单线程程序语义的前提下，可以重新安排语句的执行顺序。
- 指令并行的重排序。现代处理器采用了指令级并行技术来将多条指令重叠执行。如果不存在数据依赖性(即后一个执行的语句无需依赖前面执行的语句的结果)，处理器可以改变语句对应的机器指令的执行顺序。
- 内存系统的重排序。由于处理器使用缓存和读写缓存冲区，这使得加载(load)和存储(store)操作看上去可能是在乱序执行，因为三级缓存的存在，导致内存与缓存的数据同步存在时间差。

#### 遵守 as-if-serial?
不管怎么重排序（编译器和处理器为了提高并行度），（单线程）程序的执行结果不能被改变。编译器、runtime和处理器都必须遵守as-if-serial语义。为了遵守as-if-serial语义，编译器和处理器不会对存在数据依赖关系的操作做重排序，因为这种重排序会改变执行结果。但是，如果操作之间不存在数据依赖关系，这些操作就可能被 编译器和处理器重排序。

### 什么是happens-before原则？
先行发生，如果一个操作执行的结果需要对另一个操作可见，那么这两个操作之间必须存在happens-before关系。

#### 为什么需要happens-before？
JVM会对代码进行编译优化，会出现指令重排序情况，为了避免编译优化对并发编程安全性的影响，需要happens-before规则定义一些禁止编译优化的场景，保证并发编程的正确性。

#### hb常见规则？
1. 程序顺序规则：一个线程中的每个操作，happens-before于该线程中的任意后续操作。
2. 监视器锁规则：对一个锁的解锁，happens-before于随后对这个锁的加锁。
3. volatile变量规则：对一个volatile域的写，happens-before于任意后续对这个volatile域的读。
4. 传递性：如果A happens-before B，且B happens-before C，那么A happens-before C。
5. start()规则：如果线程A执行操作ThreadB.start()（启动线程B），那么A线程的ThreadB.start()操作happens-before于线程B中的任意操作。
6. join()规则：如果线程A执行操作ThreadB.join()并成功返回，那么线程B中的任意操作happens-before于线程A从ThreadB.join()操作成功返回。
7. 程序中断规则：对线程interrupted()方法的调用先行于被中断线程的代码检测到中断时间的发生。
8. 对象finalize规则：一个对象的初始化完成（构造函数执行结束）先行于发生它的finalize()方法的开始。

#### 双重检查锁（DCL）？
![image](https://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/F9865DEF49D440D8BF84B72ED7074570/16001)

正确的写法
1. 静态内部类

JVM在类的初始化阶段（即在Class被加载后，且被线程使用之前），会执行类的初始化。在执行类的初始化期间，JVM会去获取一个锁。这个锁可以同步多个线程对同一个类的初始化。基于这个特性，可以实现另一种线程安全的延迟初始化方案。
优点是：官方推荐，可以保证实现懒汉模式。代码少。
缺点是：第一次加载比较慢，而且多了一个类多了一个文件，总觉得不爽。
```
public class SingletonKerriganF {     
      
    private static class SingletonHolder {     
        static final SingletonKerriganF INSTANCE = new SingletonKerriganF();     
    }     
      
    public static SingletonKerriganF getInstance() {     
        return SingletonHolder.INSTANCE;     
    }     
}    
```
2. volatile关键字
volatile禁止了指令重排序，所以确保了初始化顺序一定是1->2->3，所以也就不存在拿到未初始化的对象引用的情况。
优点：保持了DCL，比较简单
缺点：volatile这个关键字多少会带来一些性能影响。
3. 初始化完后赋值
通过一个temp，来确定初始化结束后其他线程才能获得引用。
同时注意，JIT可能对这一部分优化，我们必须阻止JTL这部分的"优化"。
```
public class Singleton {    
    
    private static Singleton singleton; // 这类没有volatile关键字    
    
    private Singleton() {    
    }    
    
    public static Singleton getInstance() {    
        // 双重检查加锁    
        if (singleton == null) {    
            synchronized (Singleton.class) {    
                // 延迟实例化,需要时才创建    
                if (singleton == null) {    
                        
                    Singleton temp = null;  
                    try {  
                        temp = new Singleton();    
                    } catch (Exception e) {  
                    }  
                    if (temp != null)    //为什么要做这个看似无用的操作，因为这一步是为了让虚拟机执行到这一步的时会才对singleton赋值，虚拟机执行到这里的时候，必然已经完成类实例的初始化。所以这种写法的DCL是安全的。由于try的存在，虚拟机无法优化temp是否为null  
                        singleton = temp; 
                }    
            }    
        }    
        return singleton;    
    }  
}  
```

>参考：https://baijiahao.baidu.com/s?id=1654963077694559106&wfr=spider&for=pc
https://www.jianshu.com/p/ca19c22e02f4

### 什么是MESI（缓存一致性协议）？

当CPU写数据时，如果发现操作的变量是共享变量，即在其他CPU中也存在该变量的副本，会发出信号通知其他CPU将该变量的缓存行置为无效状态，因此当其他CPU需要读取这个变量时，发现自己缓存中缓存该变量的缓存行是无效的，那么它就会从内存重新读取。

#### 怎么发现数据是否失效呢？（嗅探）

每个处理器通过嗅探在总线上传播的数据来检查自己缓存的值是不是过期了，当处理器发现自己缓存行对应的内存地址被修改，就会将当前处理器的缓存行设置成无效状态，当处理器对这个数据进行修改操作的时候，会重新从系统内存中把数据读到处理器缓存里。

## 什么是volatile？作用？
volatile是一种乐观锁机制，采用CAS算法实现对共享变量的同步，
对于volatile变量，每个线程对于变量的读写操作都需要同步主存，不在自己的工作内存直接读取和操作。

重要的作用就是保证变量的内存可见性和禁止对volatile重排序。

### volatile的缺点？

由于Volatile的MESI缓存一致性协议，需要不断的从主内存嗅探和cas不断循环，无效交互会导致总线带宽达到峰值。

所以不要大量使用Volatile，至于什么时候去使用Volatile什么时候使用锁，根据场景区分。

## JVM是怎么还能限制处理器的重排序的呢？volatile呢？
它是通过内存屏障来实现的。

 volatile？

- 在每个volatile写操作的前面插入一个StoreStore屏障；
- 在每个volatile写操作的后面插入一个StoreLoad屏障；
- 在每个volatile读操作的后面插入一个LoadLoad屏障；
- 在每个volatile读操作的后面插入一个LoadStore屏障。

### 什么是内存屏障？
为了防止编译器和硬件的不正确优化，使得对存储器的访问顺序（其实就是变量）和书写程序时的访问顺序不一致而提出的一种解决办法。 它不是一种错误的现象，而是一种对错误现象提出的解决方。java编译器在生成指令的时候，会在适当的位置插入内存屏障指令来禁止特定处理器重排序，为了实现volatile内存语义，JMM会限制特定的编译器和处理器重排序，并生成处理器表

#### 内存屏障有两个作用？
1. 保证特定操作的执行顺序，阻止屏障两侧的指令重排序；
2. 保证某些变量的内存可见性，强制把写缓冲区/高速缓存中的脏数据等写回主内存，或者让缓存中相应的数据失效。

#### 内存屏障分类？

屏障类型 |指令诶行| 说明
---|---|---
LoadLoad |Load1:LoadLoad:Load2|确保Load1数据的装在优先于Load2及后续装载指令的装载
StoreStore |Store1:StroeStore:Store2|确保Sotre1数据对其他数据可见（刷新到主存）先于Store2及所有后续存储指令的存储
LoadStore |Load1:LoadStore:Store2|确保Load1数据装载闲鱼Store2及所有后续的存储指令刷新到内存
StoreLoad |Store:StoreLoad:Load2|确保Store1数据对其他处理器可见先于Load2及所有后续装在指令的装载，会使改屏障之后的所有内存访问指令完成后，才执行屏障之后的内存访问指令


## synchronize怎样实现的？
在使用synchronized之后，底层会在执行代码块或者方法的之前添加moniterenter指令，去获取或者等待锁，一旦获取到锁，那么就会根据指令机制对当前对象的计数器+1，释放锁monitorexit-1。未获取到锁则进入同步队列中。锁的重入性:同一线程中，如果已经获取锁对象，再次获取锁对象时，不需要再次获取锁，而是在对象计数器机型累加，退出时累减
![image](https://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/30C7EA7FDB114041A247E8CE0E1A5F42/16070)

>参考：https://blog.csdn.net/weixin_42762133/article/details/103241439

# 锁
## 什么是CAS？
比较与交换算法，采用乐观锁策略。它假设所有线程获取锁都不会冲突，如果出现冲突其他线程也不会被阻塞，而是进入有CAS设计的算法中，去重试获取锁

### CAS的原理？

调用Unsafe类中的CAS方法

#### Unsafe是什么？

是CAS核心类，由于Java方法无法直接访问地层系统，需要通过本地(native)方法来访问，Unsafe相当 于一个后门，基于该类可以直接操作特定内存数据。Unsafe类存在于 sun.misc 包中，其内部方法操作可 以像C的指针一样直接操作内存，因为Java中CAS操作的执行依赖于Unsafe类的方法。

### CAS缺点？
1. 自旋时间过长，CPU开销比较大
2. 只能维持一个共享的原子变量，不能保证操作的原子性
3. ABA问题
#### 什么是ABA问题？怎么解决？
线程ABC执行如下
A：获取对象锁要将A->B,CAS失败，自旋

B:  CAS成功，将A->B，END

C:  CAS成功，将B->A，END

A:	  CAS检测值没有变化，将A->B END

以上在某些转账，库存扣减时可能引发巨大的问题

解决：
追加版本号，每次更新将版本号+1，那么就变成了1A->2B->3A了
### CAS与synchronized区别？
1.sync在多线程竞争同一个锁的时候可能出现线程阻塞和唤醒带来一定的性能消耗问题，是阻塞同步
2.cas在多线程竞争同一个锁的时候会进行尝试，不会将线上挂起，所以无须线程的上下文切换和唤醒，是非阻塞同步，但是CAS会消耗cpu资源

## 什么是对象？
存储在java堆空间中
### 对象的构成？
1. 对象头
2. 对象体（实例数据） 存储对象的属性和值
3. 对齐字节 仅仅起着占位符的作用，为了减少内存的碎片空间
![image](https://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/A592409288804CCC8DD6233884F94CE8/16129)

#### 对象头的组成？
1. MARK WORD（标记字）仅仅起着占位符的作用，为了减少内存的碎片空间
2. CLASS WORD 指向方法区类对象的指针，表明该对象随时可以知道自己是哪一个类的实例
3. 数组长度 只有数组对象才有此属性
#### 对象头的MARK WORD格式？
![image](https://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/3ADEC446D4D04282BD093142889715E5/16117)
字段含义：

字段| 含义
---|---
bised_lock|1:启用偏向锁，0：没有启用
age | 4位对象年龄，在GC中，如果对象在survivor区复制一次，那么age+1，当达到阈值时，将会晋升老年代。默认情况下并行的GC年龄时15，并发的GC年龄时5。age最大4位，即最大值是15。所以参数-XX:MaxTenuringThreshold为15的原因
identity_hashcode|31位的对象hashcode，采用延时加载技术，调用System.idtentityHashCode()计算，并将该结果写入到对象头里面。当对象锁升级为偏向、轻量级、重量级锁后，由于没有足够的存储空间，所以该值会被转移到管称monitor中
thread|偏向锁ID
epoch|偏向时间戳
ptr_to_lock_record|指向锁记录的指针
ptr_to_heavyweight_monitor|指向对象监视器的monitor指针

## synchronized的原理？
在使用synchronized之后，底层会在执行代码块或者方法的之前添加moniterenter指令，去获取或者等待锁，一旦获取到锁，那么就会根据指令机制对当前对象的计数器+1，释放锁monitorexit-1。未获取到锁则进入同步队列中

### synchronized重入原理
同一线程中，如果已经获取锁对象，再次获取锁对象时，不需要再次获取锁，而是在对象计数器机型累加，退出时累减

### 锁的分类？各自的适用场景？
- 乐观锁
    - 总是假设最好的情况，每次去拿数据都认为其他线程不会修改，但是等到更新操作时，会判断别人有没有更新这个数据，可以使用版本号+CAS算法。像数据库的write_condition和java的原子变量类都是使用乐观锁的CAS实现的。
    - 适用场景：一般适用于读比较多，写比较少的场景，可以减少锁的开销，提高系统的吞吐量
- 悲观锁
    - 每次去拿数据都会假设别人会修改，所以每次拿数据都会上锁，这样别的线程想拿到这个锁就会阻塞，知道它拿到锁。传统的数据库读锁、写锁、行锁、表锁，java的synchronized和ReentrantLock都是悲观锁的思想实现
    - 适用场景：一般适用与写比较多的场景，可以减少其他线程对CPU的消耗
    
### 锁的状态?
	正常 01
	偏向锁 01
	轻量级锁 00
	重量级锁 10
	GC标记 11
### 锁是怎样升级的？
1. 当一个对象刚刚建立，没有任何线程竞争时，偏向锁标识0，锁状态01
2. 当有一个线程来竞争锁时，如果当前对象无锁竞争，CAS替换ThreadId替换成功则获取偏向锁成功，替换失败开始偏向锁撤销。如果发现对象头已经有偏向线程Id，则检测是否是当前线程Id，如果是则获取偏向所执行代码，如果不是则锁升级。在竞争不激烈的情况下，效率非常高
3. 当有两个线程开始竞争锁时，说明当前锁存在一定的竞争，锁由偏向锁升级为轻量级锁，原持有偏向锁的线程的栈中分配锁记录，拷贝对象头的Mark Word到原持有偏向锁的锁记录中，原持有偏向锁线程获得到轻量级锁，CAS指向原持有偏向线程锁记录的指针，标识位00，唤醒线程，从安全点继续执行。另一个竞争的线程栈中分配锁记录，拷贝对象头重的Mark Word到锁记录中，CAS替换对象头的MarkWord锁记录指针，失败则自旋。等到执行完毕开始轻量级锁的解锁。
4. 当自旋到达一定的次数依然没有成功，JVM会将锁升级为重量级锁，这个锁就叫同步锁，锁对象的标记字就会再发生改变，指向一个监视器对象，这个监视器对象采用集合的形式，来登记和管理排队线程。此时未抢到锁的线程都会被阻塞，由Monitor来管理。

### 锁是怎样撤销的？
1. 偏向锁：偏向锁使用了一种等待竞争才释放锁的机制，所以当其他线程竞争偏向锁时，持有偏向锁的线程才会释放锁。同时，偏向锁的撤销需要等到原持有偏向锁的线程到达全局安全点（在这个时间点上没有正 在执行的字节码），首先会暂停持有偏向锁的线程，然后检测持有偏向锁的线程是否存活，如果不处于活动状态，则将对象头设置无锁状态；如果线程依然存活，拥有偏向锁的线程会被执行，去遍历对象的锁记录，栈中的锁记录和对象头要么重新偏向其他线程，要么恢复到无锁或者标记对象不适合作为偏向锁，最后唤醒等待线程
2. 轻量级锁：解锁时，会使用CAS操作将Displayed Mark Word替换到对象头，如果成功则表明没有发生竞争。如果失败表示当前锁存在竞争，锁就会膨胀为重量级锁。
3. 重量级锁：一旦升级为重量级锁将不会再恢复到轻量级锁。当其他线程尝试竞争锁时都会被阻塞，持有锁的线程释放锁后会唤醒这些线程，这些线程会重新去展开新一轮的竞争锁

### 各种锁的优缺点？

![image](http://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/53B7A146BF0C41D985C978B47A8B67EE/16167)



![image](https://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/7F783D6DC24047018A9F06194FE5BC7A/16137)



# 其他

## final实现原理？

写final域会要求编译器在final域写之后，构造函数返回前插入一个StoreStore屏障。读final域的重排序规则会要求编译器在读final域的操作前插入一个LoadLoad屏障。

### 重排序对final的影响？

- 基本数据类型
	- 写：禁止final写与构造方法重排序，即禁止final域写重排序到构造方法外，从而保证对所有线程的可见性，该对象的final域已经全部被初始化
	- 读：禁止初次对象的读应与读该对象包含的final域重排序
- 引用数据类型：额外增加约束：禁止在构造函数对一个final成员域的写入与随后将这个被构造的对象引用赋值给引用变量的重排序

# 中断

## [java多线程 interrupt(), interrupted(), isInterrupted()方法区别](https://www.cnblogs.com/huangyichun/p/7126851.html)

- ### interrupt()

  作用是中断线程

  - 本线程中断自身是被允许的，且"中断标记"设置为true
  - 其它线程调用本线程的interrupt()方法时，会通过checkAccess()检查权限。这有可能抛出SecurityException异常。
    - 若线程在阻塞状态时，调用了它的interrupt()方法，那么它的“中断状态”会被清除并且会收到一个InterruptedException异常。 
      - 例如，线程通过wait()进入阻塞状态，此时通过interrupt()中断该线程；调用interrupt()会立即将线程的中断标记设为“true”，但是由于线程处于阻塞状态，所以该“中断标记”会立即被清除为“false”，同时，会产生一个InterruptedException的异常。
  - 如果线程被阻塞在一个Selector选择器中，那么通过interrupt()中断它时；线程的中断标记会被设置为true，并且它会立即从选择操作中返回。
    - 如果不属于前面所说的情况，那么通过interrupt()中断线程时，它的中断标记会被设置为“true”。

- ### interrupted()

  判断的是当前线程是否处于中断状态。是类的静态方法，同时会清除线程的中断状态。

  ```java
  public static boolean interrupted() {
      return currentThread().isInterrupted(true);
   }
  ```

  

- ### isInterrupted()

  判断调用线程是否处于中断状态，不会清除线程的中断状态

  参考：https://www.cnblogs.com/huangyichun/p/7126851.html

## 线程的基本操作？

### interrupted

暂停当前线程，让出CPU，执行此方法会向系统线程调度器（Schelduler）发出一个暗示，告诉其当前JAVA线程打算放弃对CPU的使用，但该暗示，有可能被调度器忽略。使用该方法，可以防止线程对CPU的过度使用，提高系统性能。

### join

A线程调用B线程的join()方法，将会使A等待B执行，直到B线程终止。如果传入time参数，将会使A等待B执行time的时间，如果time时间到达，将会切换进A线程，继续执行A线程

### sleep

使当前线程进入阻塞状态，状态变为：TIME_WAITING，若睡眠时其他线程调用了interrupt方法，会导致sleep抛出异常InterruptException





## LockSupport是什么？

LockSupport是用于创建锁和其他同步类的线程阻塞基本原语。通过二元信号量实现阻塞，休眠时线程状态为WAITING，无需捕获InterruptedException，但是会相应中断。某个线程调用LockSupport.park，如果对应的“许可证”可用，则此次调用立即返回，否则线程将会阻塞直到中断发生、超时或者许可证状态变为可用。



### 为什么LockSupport也是核心基础类?

 AQS框架借助于两个类：Unsafe(提供CAS操作)和LockSupport(提供park/unpark操作)

### LockSupport.park()会释放锁资源吗?

不会，它只负责阻塞当前线程，释放锁资源实际上是在Condition的await()方法中实现的

### 如果在park()之前执行了unpark()会怎样?

park被通过，消耗一个许可



# 什么是Lock？

锁是多个线程访问共享资源的方式，一般来说锁能够防止多个线程同时访问一个共享资源。在Lock之前，java以synchronized关键字实现锁的功能，而在1.5后增加了lock接口，它提供了与synchronzed一样的锁功能，虽然没有隐式的加锁解锁的方便性，但是它却拥有了锁释放和获取的可操作性，可中断锁以及超时获取锁等多种synchronzed不具有的特性

## lock使用的标准模版？

```java
Lock lock = new ReentrantLock();
lock.lock();
try{
	.......
}finally{
	lock.unlock();
}
```

## 含有哪些接口？

```
lock();
lockInterruptibly();
tryLock();
tryLock(long,TimeUnit);
unlock();
newCondition();
```

# 什么是AQS？

AQS负责同步状态的管理，线程的排队，等待和唤醒这些底层操作，而Lock等同步组件主要专注于实现同步语义。

## AQS可以重写的方法？
![image](http://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/8601A95B20724196A3FDF20FDEDB7AD7/16169)
## AQS的模版方法？
![image](http://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/4CF1E8BE00114F1FB68DFD62DC6C84D5/16171)

## AQS包含的哪两种锁？

- 独占式：同一时刻只有一个线程可以获得到锁
- 共享式：同一时刻多个线程可以获得到锁

举例：读写锁：

一个线程对一个共享资源进行读操作时，其他写操作均被阻塞，而其他线程的读操作时可以进行的。

但是当对共享资源进行写操作时，其他线程的读写操作均被阻塞

## AQS同步器原理

![image](http://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/7F240816597F42B3B0F2D56DBFF12B2B/16173)



### node结点

![image](http://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/B875AE9C8249491CAD4960C925407335/16175)

### 独占式获取同步状态的流程？

```
public final void acquire(int arg){

	if(!tryAcquire(arg) &&
		acquireQueued(addWaiter(Node.EXCLUSIVE),arg))
		selfInterrupt();
}
```



## AQS的应用？

ReentrantLock 
ReentrantReadWriteLock 
Semaphore
CountDownLatch 



> 参考：https://juejin.im/post/5ed6f54e518825433a57bbb5