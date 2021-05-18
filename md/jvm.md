[TOC]

## 程序代码是怎样跑起来的（类加载机制）？

### jvm在什么情况下会加载一个类？

###  类加载过程是怎样的？

- 加载：通过类的完全限定名来获取类的二进制字节流，将其转化为所代表的数据结构存储在方法区运行时数据结构，在内存中生成一个java.lang.String对象代表这个类。（ps：数组类是由jvm直接创建，而不是类加载器创建，到那时数组类型是由类加载器创建）
    -  作用：一、通过一个类的全限定名来获取定义此类的二进制字节流。二、将这个字节流所代表的静态存储结构转化为方法区的运行时数据结构。三、在内存中生成一个代表这个类的 java.lang.Class 对象，作为方法区这个类的各种数据的访问入口。
- 连接
  - 验证：确保字节流包含的信息符合虚拟机的要求，不会危害到虚拟机的安全。
    - 文件格式验证、元数据验证、字节码验证、符号引用验证 
  - 准备：为类变量分配空间并初始化，对于final static会直接赋值
  - 解析：将符号引用替换成直接引用
- 初始化：开始执行类中定义的java代码
- 使用
- 卸载

![image](http://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/429495704B6241B8A5F0C3D4BB88F87F/16547)

### 类加载器有什么用？

将“通过一个类的全限定名来获取此类描述的二进制字节流”这个动作放到jvm外部实现，以便让应用程序自己决定如何获取类。这个动作的代码模块就成为类加载器。任何一个类都需要有类加载器和类本身确定它的唯一性，两个类判定相等的前提是必然他们的类加载器是相同的



### 你了解有哪些类加载器？

- 启动类加载器（Bootstrap ClassLoader）：将<JAVA_HOME>\lib目录下的并且是虚拟机识别的类库加载到jvm内存。启动类加载器无法被直接引用，如果需要委托则在自定义加载器中直接返回null。
- 扩展类加载器（Extension ClassLoader）：开发者可以直接扩展的类加载器，负责加载<JAVA_HOME>/lib/ext
- 应用程序类加载器（Application ClassLoader）：负责加载开发者路径（classpath）所指定的类库，开发者可以直接使用，如果没有定义自己的类加载器，那么默认就是它。
- 自定义类加载器：根据需求自定义类加载器

![image](http://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/1043A9AE642242DF8A0BFC11D5C08733/16545)

#### tomcat类加载是怎么设计的？

![image](http://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/0DEEB93E761546659C524DD1B5A3A023/16549)

Tomcat自定义了Common、Catalina、Shared等类加载器，其实就是用来加载Tomcat自己的一些核心基础类库的。然后Tomcat为每个部署在里面的Web应用都有一个对应的WebApp类加载器，负责加载我们部署的这个Web应用的类

至于Jsp类加载器，则是给每个JSP都准备了一个Jsp类加载器。

**Tomcat是打破了双亲委派机制的**

### 双亲委派机制是什么？工作原理？

类加载器之间的层级关系，成了类的双亲委派模型，除了顶层的类加载器之外其他类加载器都需要有父类加载器。如果一个类加载器收到了类加载请求，它首先不会自己加载这个类，而是将这个类委派给父类加载器去完成，每一个层次的类加载器都是如此，因此所有的加载请求最终都应该到顶层的启动类加载器，只有当父类加载无法完成这个加载请求（它搜索范围内找到所需要的类），子类加载器才尝试自己加载。

#### 双亲委派模式的优点？

- java类随着它的类加载器一起具备了优先级的层次关系，使得基础类得到了统一
- 避免多份同样字节码加载

1. 采用双亲委派模式的是好处是Java类随着它的类加载器一起具备了一种带有优先级的层次关系，通过这种层级关可以避免类的重复加载，当父亲已经加载了该类时，就没有必要子ClassLoader再加载一次
2. 其次是考虑到安全因素，java核心api中定义类型不会被随意替换，假设通过网络传递一个名为java.lang.Integer的类，通过双亲委托模式传递到启动类加载器，而启动类加载器在核心Java API发现这个名字的类，发现该类已被加载，并不会重新加载网络传递的过来的java.lang.Integer，而直接返回已加载过的Integer.class，这样便可以防止核心API库被随意篡改。

## 到底什么是jvm内存区域的划分？

### 运行时数据区划分？

- 线程私有

  - 虚拟机栈：生命周期和线程一致，描述的是java方法执行的线程内存模型，存储局部变量表、动态链接、操作数栈和方法出口等信息。每一个方法从调用到结束，都对应一个虚拟机栈入栈出栈的过程。
    - 局部变量表：存储基本数据类型，对象引用和指令地址。
    - -Xss：虚拟机栈大小
  - 本地方法栈：和虚拟机栈类似，虚拟机栈主要对java方法服务，而本地方法栈对native方法服务
  - 程序计数器：内存空间小，只有它不会发生OOM，SOF异常，是字节码解析器执行指令的行号指示器。

- 线程共享

  - 堆：存储对象实例和数组
    - -Xms -Xmx -Xmn 堆初始化大小、堆最大，新生代大小（堆空闲空间<40%会扩展到最大堆大小，空闲>70% 减少到初始大小，一般设置Xms=Xmx）
    - 老年代大小=Xmx-Xmn-永久代

  - 方法区：存储加载后的类信息（类名称、方法、字段信息），运行时常量池，静态变量，常量等数据，已被虚拟机加载的代码缓存等
    - 运行时常量池：存放编译期生成的各种字面量和符号引用。编译器和运行期的String.intern（）都可以将常量放入池内。
    - JVM设置最小最大：-XX:PermSize=64M -XX:MaxPermSize=128M

![image](http://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/862176E51DD144A59C42BA6E376D7D1C/16551)

另外掌握

- 直接内存：不是运行时数据区的一部分，但是被频繁使用，是java堆对象DirectByteBuffer对象作为这块内存的引用进行操作，避免在堆和native堆之间复制数据，使用的是运行时内存。一般随着老年代Full GC顺便清理
- 元空间：jdk8后，永久代即方法区概念废弃，取而代之的是Metaspace元空间，使用的是本地内存而不是堆内存。
  - -XX:MetaspaceSize:128M -XX:MaxMetaspaceSize=128M

### jdk1.6，1.7，1.8运行时数据区对比

![image](http://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/8C70A25BB40D489D9D9393C8554DEE02/17304)

- 在JDK1.7之前运行时常量池，字符串常量池，静态域等存放在方法区, 运行时常量池逻辑包含字符串常量池，此时hotspot虚拟机对方法区的实现为永久代。
- 在JDK1.7中字符串常量池和静态域被从方法区（永久代）拿到了堆中（在堆中另开辟了一块空间）,这里没有提到运行时常量池,也就是说字符串常量池被单独拿到堆,运行时常量池剩下的东西还在方法区,也就是hotspot中的永久代。
- 在JDK1.8 hotspot移除了永久代，用元空间(Metaspace)取而代之, 这时候字符串常量池还在堆,运行时常量池还在方法区,只不过方法区的实现从永久代变成了元空间(Metaspace)。

### 各种池相关概念

> https://blog.csdn.net/qq_26222859/article/details/73135660

#### 常量

被final修饰的*成员变量*。静态变量、实例变量、局部变量。或者基本类型的缓存

#### 常量池

存储字面量和符号引用。

- 字面量：文本字符串，被final修饰的常量、基本数据类型
- 符号引用：类和接口的全限定名、字段名称和描述符、方法名称和描述符

#### 方法区中的运行时常量池

存储

### 什么是NIO？

> https://blog.csdn.net/qq_36520235/article/details/81318189
>
> https://blog.csdn.net/u013857458/article/details/82424104?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-2.control&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromBaidu-2.control

### 为什么移除永久代？

> https://www.cnblogs.com/jjj250/p/11994913.html

1.  由于永久代使用jvm内存经常不够用或发生内存泄露，引发恼人的OutOfMemoryError异常（在Java Web开发中非常常见）。
2.  移除永久代可以促进HotSpot JVM与JRockit VM两种虚拟机的融合，因为JRockit没有永久代。
3.  对永久代进行调优是很困难的。永久代中的元数据可能会随着每一次Full GC发生而进行移动。

### jvm栈帧包含哪些内容?

> https://blog.csdn.net/wb_snail/article/details/80610676

## 对象

### 对象的创建过程？

遇到new指令时，首先检查这个参数是否能在常量池中定位到一个类的符号引用，并检查这个符号引用代表的类是否已经被加载、解析、初始化过。如果没有，执行相应的类加载。
类加载检查通过后，为新对象分配内存。在对的空闲内存中划分一块区域（“指针碰撞-内存规整或空闲列表-内存交错”分配）。内存空间分配完成后会初始化为0，接下来就是填充对象头，把对象是哪个类的实例、如果才能找到类的元数据信息、对象的哈希吗、对象的gc分代年龄存入对象头。
执行new指令后，执行init方法才算真正可用的对象创建完成。

#### 对象创建过程的并发问题？

对象创建行为时一个非常频繁的行为，即时是修改一个指针，在并发环境里也不是线程安全的。可能出现正在给A分配内存空间，指针还没来得及修改，对象B又使用了指针来分配内存。两种解决思路:一种是对分配的内存空间采用同步处理:实际上虚拟机采用CAS(乐观锁的一种)配上失败重试的方式来保证更新的原子性；另一种是将内存的分配以线程隔开，也就是在创建线程的时候，每个线程在java堆中先分配一块内存，称为本地线程分配缓冲(Thread Local Allocation Buffer,TLAB)，用来给本线程的对象分配内存空间,只有TLAB用完时，才需要同步锁。 
当分配完内存之后，接下来就要对对象做必要的设置了，例如该对象是哪个类的实例、如何才能找到类的元数据类型、对象的哈希吗、对象的GC分代年龄等信息。这部分信息存放在对象的对象头部分。 
当上面工作都完成后，其实就可以使用该对象了，不过对象只是进行了默认初始化里面数据都为默认的，然后就会执行init方法了，将对象按照创建时的代码进行初始化，这样才算是一个真正的对象。

### 内存分配的方式（怎样选择？）

指针碰撞：假设堆中的内存是规整的，将内存分为两块，一块是被使用过的，一块是没有使用的，中间放一个指针作为分界点，所分配的内存是指针想空闲空间方向挪动与对象大小相等的距离
空闲列表：堆中的内存并不是规整的，已经被使用过的内存与空闲内存交错在一起，虚拟机内部维护了一个列表，记录了哪些内存块是可以使用的，在分配的时候在列表中找到一块足够大的空间分配给对象实例，并更新列表记录

### 对象存活判断(对象已死？)
- 引用计数算法：在对象中添加一个引用计数器，每当有一个地方引用它时，计数器值加一，引用失效的时候计数器减一，当计数器为0时对象不可以再被使用
  - 缺点：无法解决对象相互循环引用的问题
- 可达性分析算法：通过一个被认为是GCROOT的根对象作为起始结点，如果一个对象到GCROOT间没有任何引用链相连，那么证明此对象不可以再使用

#### 哪些对象可以作为GCROOT？
可以用来当作GCROOT的对象
- 虚拟机栈（栈帧中的本地变量表）中引用的对象，例如线程被调用的方法堆栈使用到的参数，局部变量，临时变量
- 方法区中静态属性引用的对象，eg:java类中的引用类型静态变量
- 方法区中常量引用的对象，eg：字符串常量池中的引用
- 被同步锁Synchronized持有的对象

### 对象引用类型

- 强引用：代码中的引用复制操作，类似Object obj = new Object()，只要强引用关系存在垃圾收集器就不会回收掉被引用的对象
- 软引用：使用SoftReference修饰的，只要被软引用关联的对象，在系统即将发生内存溢出异常之前，会把这些对象列入回收范围内进行二次回收，如果回收后还没有足够的内存就会抛出内存溢出异常
- 弱引用：使用WekReference修饰的，被弱引用修饰的对象，只能存活到下一次垃圾收集之前，当垃圾手机开始工作时，无论当前内存是否足够都会收回若引用关联的对象
- 虚引用：使用PhantomReference修饰的，无法通过一个虚引用获取对象实例，为一个对象设置虚引用的目的是为了这个对象能在垃圾回收的时候收到一个系统的通知

### 对象收集判断（finalizer方法的作用）？

在可达性分析算法中被判定为不可达的对象也不是非死不可，这时候还处于缓刑状态，如果真正要宣告一个对象死亡，最少要经过两次标记过程：如果对象在可达性分析中发现没有调用链，那么它会被第一次标记，随后经过一次筛选，筛选条件是是否有必要执行finalize()方法，如果对象没有重写finalize或者已经被虚拟机调用过那么被认为没有必要执行，如果对象有必要执行finalize方法，那么就会将该对象放在一个名为F-Queue的队列中，稍后会有虚拟机自动建立的、低调度优先级的Finalizer线程去执行（只是触发方法开始运行，并不会等待结束，原因是万一执行finalize非常耗时可能导致F-Queue队列中的其他对象永远处于等待状态，导致内存回收崩溃）他们的finalize方法。稍后收集器会对F-Queue中的对象进行二次小规模的标记，如果对象要在finalize中成功拯救自己-只要重新与引用链上的任何一个对象建立关联即可，那么第二次标记的时候它就会被移除即将回收的集合。

### 对象之间会存在跨代引用怎么办？
​	跨代引用假说：跨代引用相对于同代引用来说仅占极少数。不必浪费专门的空间记录每一个对象是否存在及存在哪些跨代引用，只在新生代上建立一个全局的数据结构（被称为“记忆集”），这个结构会把老年代分成若干小块，标示老年代的哪一块内存会存在跨代引用，如果之后发生Minor GC时，只包含了跨代引用的小块内存里的对象才会被加入到GC Roots进行扫描。

### 对象的分代年龄为什么是15

https://javap.blog.csdn.net/article/details/103721326?utm_medium=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromBaidu-1.not_use_machine_learn_pai&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-BlogCommendFromBaidu-1.not_use_machine_learn_pai

### 对象结构

> https://blog.csdn.net/zqz_zqz/article/details/70246212

## 垃圾回收

### 垃圾回收涉及的专业名词

- Partial GC 部分收集
  - Minor GC 新生代收集
  - Major GC 老年代收集
  - Mixed GC 混合收集：收集整个新生代及部分老年代 目前只有G1收集器会有
- Full GC 整堆收集：收集整个Java堆和方法区的垃圾收集

### 什么是安全点？

用户线程必须在达到安全点后才能暂停

#### 如何让所有线程都停下来？

- 抢先式中断：不需要线程代码主动配合，在垃圾回收的时候，系统会把所有的用户线程停止，如果发现有用户线程不在安全点上，就恢复这个线程，让他一会儿再中断，直到达到安全点。
- 主动式中断：当需要中断的时候不直接对线程操作，仅设置一个标识为，各个线程在执行过程会主动轮训这个标示，一旦发现中断标示为真时，就主动在自己的临近安全点上挂起。轮询标志的地方和安全点是完全重合
  - 缺点
    - 主动轮询可能耗费大量的资源
    - Hotspot使用内存陷阱的方式，将轮询操作简化到一条汇编指令的程度

#### 如果有线程处于没有“执行”（没有获取到CPU）的时候？怎么让这些也进入安全点？

安全区域，确保在一段代码片段中，引用关系不会发生变化，因为在这个区域中的任意位置开始垃圾回收都是安全的

### 垃圾回收算法有哪些？

- 标记-清除算法：分为标记和清除两个阶段，标记出所有需要回收的对象，在标记完成后，统一回收掉所有已经标记的一项，也可以反转
  		- 缺点
    		- 执行效率不稳定：如果java堆中存在大量需要标记回收的对象，那么标记和清除的过程执行效率都与对象数量成负相关
    - 内存空间碎片化问题：标记清除后会存在大量不连续的内存碎片，内存碎片太多可能会在运行时分配大内存对象时无法找到足够的连续内存空间而再次进行另一次垃圾收集动作
- 标记-复制算法（复制算法）：将内存划分为等量大小的两块，每次只使用其中的一块，当其中一块使用完毕时，将还存活的对象复制到另一块上，然后把使用过的内存空间一次清理掉。如果内存中绝大数对象都是存活的那么会造成大量的复制开销。如果内存中只有少量对象存活，那么只需移动堆顶指针，按顺序分配，不会造成内存碎片的清空。但
  	- 缺点：可用内存大小为原来的一般，空间利用率低
- 标记-整理算法：标记过程于标记-清除算法一样，但是后续不是对对象进行直接清理，而是让所有存活的对象都向内存空间移动，然后直接清理掉边界以外的内存
    - 缺点
        - 必须暂停用户程序才能进行，即Stop The World
        - 需要移动对象

### 目前采用的算法（复制回收）？

分代回收，对复制算法做了优化，将新生代分为一块较大的Eden空间和两块较小的Survivor空间，每次分配内存只使用Eden和其中一块Survivor。发生垃圾收集的时候，将Eden和Survivor中仍然存活的对象复制到另一块Survivor空间，然后直接清理Eden和已经使用过的Survivor。HotSpot默认Eden和Survivor大小比例是8:1，这样新生代可用的内存空间为90%。

### 对象回收的原则？

- 对象优先在Eden分配：大多数情况，对象在Eden分配，如果没有足够的空间则发起一次Minor GC
- 大对象直接进入老年代：-XX：PretenureSizeThreshold
- 长期存活的对象将进入老年代：虚拟机给每个对象定义了一个对象年龄计数器，存储在对象头中。对象通常在Eden中创建，如果第一次Monior GC存活，并被Survior容纳的话，那么该对象就会被移动到Survivor中，并且将对象的年龄设置为1岁，对象每在Survior中存活过一次Minor GC，年龄就增加1岁，当达到一定程度（默认15），就会晋升老年代。-XX:MaxTenuringThreshold
- 动态对象年龄判断：并不是所有对象的年龄都需要通过MaxTenuringThreshold判断才能进入老年代的，如果在Survivor空间中相同年龄所有对象的综合大于Survivor空间的一半，年龄>=该年龄的对象可以直接进入老年代。
- 空间担保分配：在Monior GC之前，虚拟机先检查一下老年大最大可用连续空间是否大于新生代所有对象总空间，如果条件成立，则认为此次Monior GC是安全的。如果不成立则查看-XX:HandlePromotionFailure设置的参数是否允许担保失败，如果允许，则检查老年代最大可用连续空间是否大于历次晋升到老年代对象的平均大小，如果大于则尝试Monior GC，尽管是有风险的。如果小于，或者不允许担保失败，则进行Full GC



### 老年代垃圾回收算法

标记整理

标记清除

速度很慢

### 方法区的内存回收触发时机

1. 该类的所有实例已经被回收，也就是java堆中不存在该类及其任何派生子类的实例
2. 加载该类的类加载器已经被回收，eg：JSP重载
3. 该类对应的java.lang.class对象没有在任何地方被引用，无法在任何地方通过反射访问该类的方法



### 垃圾收集器

![image](http://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/A73FCAC62F7341E785908A2BBEC08A3B/16564)

#### Serial

单线程收集器，只会使用一个处理器或者一个收集线程去工作，强调在垃圾收集的时候必须暂停所有的工作线程，直到收集结束,标记-复制算法

![image](http://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/DDBDA1BC0E134CDDA36541E8DC22036F/16566)

优点:对于内存资源受限的环境，他是所有收集器里额外消耗资源最少的 

#### ParNew

实质上是Serial收集器的多线程并行版本，除了同时使用多条线程进行垃圾收集外，其余和Serial收集器完全一致。默认开启的线程数于处理器核心数量一致，标记-复制算法

![image](http://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/4879E6C5C17D45449C96B49CDD1DBD09/16568)

#### Parallel Scavenge
也是基于标记-复制算法的收集器，并且可以进行并发收集的多线程收集器，看似和其他并行收集器一样，CMS是尽可能的缩短垃圾收集时用户线程的停顿时间，而Parallel Scavenge的目标是达到一个可控的吞吐量
ps:吞吐量=用户代码运行时间/(用户代码运行时间+运行垃圾收集时间)

##### 核心参数

-XX:MaxGCPauseMilis 
控制最大垃圾收集停顿时间，并不是说它越小收集的速度越快，它是牺牲吞吐量和新生代空间为代价。原先500M 10秒收集一次，一次停顿100ms，现在300M，5秒收集一次，一次停顿70ms，停顿时间下降了，但是吞吐量也下降了
-XX:GCTimeRatio 

设置吞吐量大小，0-100的整数，相当于吞吐量的倒数，垃圾收集时间占总时间的比例

-XX:UseAdaptiveSizePolicy 开关，当指定的时候就不需要人工设置新生代的大小，Eden和Survivor的比例等细节参数，它有自适应调节策略

#### Serial Old

收集器的老年代版本，是一个单线程收集器，使用标记整理算法，作为CMS收集器发生失败的Backup，在并发收集发生Concurrent Mode Failure使用

#### Parallel Old
Parallel Scavenge 收集器的老年代版本，支持多线程并发收集，基于标记整理算法。JDK6开始提供，注重吞吐量或者处理器资源稀缺场合使用

#### CMS

基于标记清除算法，以获取最短停顿时间为目标的收集器，目前很多java应用都是B/S模式，这类应用通常比较关注服务器的响应速度，希望系统停顿时间尽可能短

##### 收集过程

- 初始标记：标记GC Roots能直接关联的对象，速度很快，STW
- 并发标记：从GC Roots的直接关联对象开始遍历整个对象图的过程，这个过程耗时长但是不会停顿用户线程，可以于垃圾收集线程一起工作
- 重新标记：修正在并发标记期间，因用户线程继续运行导致标记产生变动的那一部分对象的标记记录，这个阶段停顿时间通常比初始标记长，但是远比并发标记短,STW
- 并发清除：清除掉标记阶段判断已经死亡的对象，由于不需要移动存活对象所以也可以与用户线程并发运行

##### 优缺点

- 优点：并发收集，低停顿
- 缺点
  - 占用一部分线程，降低了总吞吐量。默认启动的回收线程是（处理器个数+3）/4
  - 无法清除浮动垃圾，必须预留足够的内存空间提供给用户线程使用，无法像其他垃圾收集器那样等到老年代几乎被填满后在进行收集
    - 浮动垃圾是CMS在并发标记和并发清理过程中由于用户线程还在运行，程序自然而然会产生一部分新的垃圾对象，这部分垃圾对象出现在标记过程结束后，CMS无法在当次回收处理，只能留在下次收集过程清理，这部分垃圾就是浮动垃圾
    - -XX:CMSInitiatingOccupancyFraction 调整老年代收集的阈值
  - 记清除算法可能会造成大量的内存碎片，内存碎片过多会对大对象的分配带来马方，往往老年代还有很多剩余空间，但是不得不提前触发一次Full GC，在FullGC过程中内存必须移动存活的对象，无法并发，虽然解决的内存碎片的问题，但是停顿时间变长了

#### G1

基于Region的堆内存布局，遵循分代收集理论，不再固定大小及数量的分代区域划分，而是把java堆分为多个大小相等的独立区域（Region），每个Region都可以根据需要扮演新生代的Eden、Survivor空间或者老年代空间（不需要连续）。收集器对于不同的角色采用不同的策略处理。照耀超过一个Region容量一半的对象就可以判断为大对象存储在专门的Humongous区域，大多数情况会把Humongous Region作为老年代的一部分看待。

##### 收集过程？

- 初始标记：仅标记一下GC Roots能直接关联的对象，并且修改TAMS指针，让下一阶段用户线程并发运行时，能正确在可用Region中分配新对象，需要停顿线程但是耗时很短
- 并发标记：从GC Roots开始对堆中对象进行可达性分析，递归整个堆里的对象图，找出要回收的对象，这个阶段耗时长，但是与用户线程并发进行。重新处理SATB记录下的并发时有引用变动的对象。
- 最终标记：对用户线程做一个短暂的暂停，用于处理并发阶段结束后少量的SATB表中的记录
- 筛选回收：负责更新Region的统计数据，对各个Region的回收成本和价值进行排序，根据用户所期望的停顿时间来制定回收计划，可以自由选择多个Region构成回收集，然后将决定回收的Region里存活的对象复制到空的Region中，再清理整个旧Region的全部空间。这里操作涉及了对象的移动，所以必须暂停用户线程，由多条收集器线程并发完成。

##### 到底有多少个Region呢？每个Region的大小是多大呢？

其实这个默认情况下自动计算和设置的，我们可以给整个堆内存设置一个大小，比如说用“-Xms”和“-Xmx”来设置堆内存的大小。然后JVM启动的时候一旦发现你使用的是G1垃圾回收器，可以使用“-XX:+UseG1GC”来指定使用G1垃圾回收器，此时会自动用堆大小除以2048因为JVM最多可以有2048个Region，然后Region的大小必须是2的倍数，比如说1MB、2MB、4MB之类的

##### 跨Region引用对象如何解决？

使用记忆集避免全堆作为GC Roots扫描，每个Region都有自己的记忆集，这些记忆集会记录别的Region指向自己的指针。G1的记忆集本质是Hash表，Key存储别的Region的起始地址，value是一个集合，里面存储的元素是卡表的索引号。卡表结构别人指向我，我指向别人。G1至少耗费大约相当于java堆10%至20%的额外内存维持工作

##### 并发阶段如果保证收集线程与用户线程互不干扰？

G1是通过原始快照算法实现的，CMS收集器采用增量更新算法实现。每个Region设计了两个TAMS指针，把Region中的一部分空间划分出来用于并发回收过程中新对象的分配，并发回收时，新分配的对象都必须在这两个指针位置易守难攻。G1默认为这个地址以上的对象是被隐式标记过的，默认他们是存活的。如果内存回收速度赶不上内存分配速度G1也会冻结用户线程的执行导致Full GC

##### 优缺点？

- 优点
  - 并行与并发
  - 分代收集
  - 空间整合
  - 可预测的停顿

- 缺点：G1无论是垃圾收集产生的内存占用还是程序运行的额外执行负载都要比CMS高

##### G1是如何工作的？

##### 对象什么时候进入新生代的Region？

##### 什么时候触发Region GC？

##### 什么时候对象进入老年代的Region？

##### 什么时候触发老年代的Region GC？

新生代包含多少Region，
新生代如何动态增加Region
Eden和Survivor两个区域仍然还是存在
什么时候触发新生代的垃圾回收
垃圾回收的复制算法
还有G1特有的预设GC停顿时间的作用
什么时候对象进入老年代
大对象的独立Region存放和回收

#### jvm方案解决

### parnew+cms的gc，如何保证只做ygc，jvm参数如何配置？

从两个方面考虑

1. 避免FUll GC
2. 避免年轻代对象进入老年代



1. 分析每秒钟新增多少对象到新生代里面
2. 平均多长时间触发一次minor GC
3. 分析minor GC后，进入survivor区的对象大小（关键）
4. 避免动态年龄判断直接晋升老年代的对象，将年龄限制调大
5. 将survior区的大小设置为平均进入survivor区的对象大小

### jvm参数模版

![image](http://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/D50A0D2EA7324D839EC5EAC29B8BE96B/17056)

## 作业

执行多个方法的调用时，如何把方法的栈帧压入线程的Java虚拟机栈？

栈帧里如何放局部变量？

如何在Java堆里创建实例对象？

如何让局部变量引用那个实例对象？

方法运行完之后如何出栈？

垃圾回收是如何运行的？

多长时间发生一次Young GC？
Young GC耗时多久？
然后你觉得它对你的系统影响大吗？

看看你们线上系统一般每隔多长时间发生一次Full GC？
每次Full GC持续多久？
对你们系统的性能有影响吗？

### 到底什么时候会尝试触发Minor GC？

虚拟机在进行minorGC之前会判断老年代最大的可用连续空间是否大于新生代的所有对象总空间
1、如果大于的话，直接执行minorGC
2、如果小于，判断是否开启HandlerPromotionFailure，没有开启直接FullGC
3、如果开启了HanlerPromotionFailure, JVM会判断老年代的最大连续内存空间是否大于历次晋升（晋级老年代对象的平均大小）平均值的大小，如果小于直接执行FullGC
4、如果大于的话，执行minorGC

### 触发Minor GC之前会如何检查老年代大小，涉及哪几个步骤和条件？

MinorGC之前,发现老年代的可用内存已经小于新生代的全部对象大小,则会看一个参数: -XX:-HandlePromotionFailure 是否配置了;

如果有则进行下一步尝试,看看老年代的内存大小，是否大于之前每一次MinorGC后进入老年代的对象平均大小;

### 什么时候在Minor GC之前就会提前触发一次Full GC？

新生代现有存活对象>老年代剩余内存情况下，未设置空间担保 或 空间担保失败

### Full GC的算法是什么？

标记整理

### Minor GC过后可能对应哪几种情况？

1.MinorGC后，剩余的存活对象的大小，是小于Survivor区域的大小的，那么此时存活对象进入Suivivor区域即可.

2.MinorGC后,剩余的存货对象的大小，是大于Suivivor区域的大小的，但是是小于老年代可用内存大小的，此时就直接进入老年代即可;

3.很不幸，MinorGC后，剩余存活对象的大小，大于了Survivor区域的大小，也大于了老年代可用内存的大小。此时老年代都放不下这些存活对象了，就会发生"Handle Promition Failure"的情况,这个时候就会触发一次"Full GC".

FullGC 对老年代进行垃圾回收，同时也对新生代进行垃圾回收;

此时必须把老年代里没有人引用的对象给回收掉,然后才可用让MinorGC过后剩余的存活对象进入老年代里面.

如果FullGC后，老年代还是没有足够的空间存放MinorGC后的剩余存活对象，那么此时就会导致所谓的OOM内存溢出了;

### 哪些情况下Minor GC后的对象会进入老年代？

1. MinorGC后的对象太多无法放入Survivor区域,需要直接放入老年代；
2. 对象分代年龄大于设置的阈值

### 触发老年代GC的时机？

1. 老年代可用连续空间大小小于新生代全部对象大小，如果没有开启空间担保，就会触发Full GC
2. 老年代可用内存大小小于平均晋升老年代对象大小，提前触发Full GC
3. 新生代Minor GC后，存活对象大小大于Survivor区，进入老年代时老年代可用内存不足，触发Full GC
4. 如果老年代可用内存大小大于晋升老年代平均对象大小，但是老年代内存空间使用比例达到-XX：CMSInitiatingOccupancyFaction，触发Full GC

### 为什么新生代GC快？

新生代垃圾回收存活很少 且采用了复制算法，比标记整理效率高。存活对象少，迁移内存很快，然后一次性清理垃圾对象，这个速度就是快。老年代要先挪动对象压在一起，存活对象那么多，这里涉及到漫长的对象移动的过程，所以速度慢

### 为什么老年代GC很慢？

对于老年代，对象存活量大，每次遍历堆分别去标记存活对象和垃圾对象，再遍历把垃圾对象清除了，最后还要移动存活对象，防止太多内存碎片。
因为存活量大，耗时的地方我觉得在gc root引用的追踪还有存活对象的移动

### 为什么新生代用的是复制算法?老年代用的是标记整理算法？既然复制算法比较快，为什么老年代不采用新生代的这种优化版的复制算法呢？
因为老年代的存活对象太多了，采用复制算法来回挪动大量的对象，效率更差

### 如何调整JVM参数

以下几个方面评估

Eden区对象增速
Minor GC 频率
Minor GC 耗时
Minor GC 存活对象大小
老年代对象增速
Full GC 频率
Full GC 耗时

### 类运行+回收流程

![image](http://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/0ECA047A186B4C51B665D393BB140268/17052)

## 真实案例
### 动态年龄规则判断进入老年代？

![image](http://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/A404BE52185E491FB2AC688B8D8CA76D/17044)

### Minor GC后放不下Survivor区，直接进入老年代

![image](http://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/01532DDAECE540A08680195E27E29CC2/17048)

### 老年代GC是如何触发的？
![image](http://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/38ECE9668A414893BE0968A2EFCE5677/17046)

## 常用jvm参数

| 参数                                                         | 说明                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| -Xms4g                                                       | 初始化堆栈大小                                               |
| -Xmx4g                                                       | 最大堆栈大小                                                 |
| -Xmn                                                         | 新生代大小，大小是（eden+ 2 survivor space).与jmap -heap中显示的New gen是不同的。如果设置了Xmn，则Xmn=MaxNewSize=NewSize |
| XX:PretenureSizeThreshold                                    | 大于这个值的参数直接在老年代分配                             |
| -XX:MaxNewSize=2g                                            | 最大新生代大小                                               |
| -XX:+UseConcMarkSweepGC                                      |                                                              |
| -XX:CMSInitiatingOccupancyFraction=75                        | 使用CMS收集器的情况下，老年代使用了指定阈值的内存时，触发FullGC，没有指定时，默认值-1，**最终((100 - MinHeapFreeRatio) + (double)( CMSTriggerRatio * MinHeapFreeRatio) / 100.0) / 100.0 决定** |
| -XX:+HeapDumpOnOutOfMemoryError                              | 参数表示当JVM发生OOM时，自动生成DUMP文件                     |
| -Xss                                                         | JDK5.0以后每个线程堆栈大小为1M,以前每个线程堆栈大小为256K. 根据应用的线程所需内存大小进行 调整.在相同物理内存下,减小这个值能生成更多的线程.但是操作系统对一个进程内的线程数还是有限制的,不能无限生成,经验值在3000~5000左右<br/>一般小的应用， 如果栈不是很深， 应该128k够用的 大的应用建议使用256k。这个选项对性能影响比较大，需要严格的测试。 |
| -XX:UseConcMarkSweepGC                                       | 使用CMS收集器                                                |
| -XX:HandlerPromotionFailure                                  | java7不再使用，运行老年代担保分配，jdk7后默认开启            |
| -XX:G1NewSizePercent                                         | G1收集器新生代占堆内存占比，默认5%                           |
| -XX:G1MaxNewSizePercent                                      | G1收集器新生代占堆内存最大占比，默认60%                      |
| -XX:MaxGCPauseMills                                          | G1收集器最大停顿时间，默认200ms                              |
| -XX:InitiatingHeapOccupancyPercent                           | G1收集器，老年代占比超过此值就会进行新生代和老年代的混合回收机制，默认45% |
| -XX:G1MixedGCCountTarget                                     | G1，一次混合回收的过程中，最后一个阶段执行几次混合回收，默认值是8次 |
| -XX:G1MixedGCLiveThresholdPercent                            | 该参数的默认值是85%。规定只有存活对象低于85%的Region才可以被回收。 |
| -XX:G1HeapWastePercent=5                                     | 默认5%可回收的内存超过这个比例时，g1才开始mixed gc的周期     |
| -XX:+UseCMSCompactAtFullCollection                           | 默认true，是否在full gc的时候压缩空间                        |
| -XX:CMSFullGCsBeforeCompaction                               | 默认0，再多少次full gc后进行一次压缩操作                     |
| -XX:TraceClassLoading                                        | 追踪类加载情况                                               |
| -XX:traceClassUnLoading                                      | 追踪类卸载情况                                               |
| -XX:+CNSOarakkekIntialMarkEnabled 初始标记阶段多线程执行，减少STW<br/>-XX:+CMSParallelRemarkEnabled 重新标记阶段多线程执行，减少STW<br/>-XX:+PrintHeapAtGC 每次GC前都要输出GC堆的概括 |                                                              |
| -XX:+HeapDumpOnOutOfMemoryError<br />-XX:HeapDumpPath=/usr/local/logs/oom | OOM的时候自动dump内存快照出来<br />内存快照的存储位置        |
## 线上设置

Non-default VM flags: -XX:CICompilerCount=3 -XX:+HeapDumpOnOutOfMemoryError -XX:InitialHeapSize=1073741824 -XX:MaxHeapSize=1073741824 -XX:MaxNewSize=357564416 -XX:MetaspaceSize=1073741824 -XX:MinHeapDeltaBytes=524288 -XX:NewSize=357564416 -XX:OldSize=716177408 -XX:-OmitStackTraceInFastThrow -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+PrintHeapAtGC -XX:+TieredCompilation -XX:+UseCompressedClassPointers -XX:+UseCompressedOops -XX:-UseGCOverheadLimit -XX:+UseParallelGC
Command line:  -Xms1024m -Xmx1024m -XX:MaxPermSize=256m -XX:MetaspaceSize=1024m -XX:-OmitStackTraceInFastThrow -Djava.net.preferIPv4Stack=true -XX:+TieredCompilation -Djava.awt.headless=true -XX:-UseGCOverheadLimit -XX:+HeapDumpOnOutOfMemoryError -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintHeapAtGC -Dsun.misc.URLClassPath.disableJarChecking=true -Dfile.encoding=UTF-8 -Duser.timezone=Asia/Shanghai -Djava.security.egd=file:/dev/./urandom



rong     25971     1  3 Oct19 ?        1-17:49:10 /home/rong/jdk/bin/java -Djava.util.logging.config.file=/home/rong/tomcat/tf_crawler_chsi/conf/logging.properties -Djava.util.logging.manager=org.apache.juli.ClassLoaderLogManager -server -Xms4g -Xmx4g -XX:MaxNewSize=2g -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=75 -XX:+HeapDumpOnOutOfMemoryError -javaagent:/home/rong/tomcat/tf_crawler_chsi/lib/jmxtrans-agent.jar=/home/rong/tomcat/tf_crawler_chsi/conf/jmxtrans-config-falcon-test.xml -Djdk.tls.ephemeralDHKeySize=2048 -Djava.protocol.handler.pkgs=org.apache.catalina.webresources -classpath /home/rong/tomcat/tf_crawler_chsi/bin/bootstrap.jar:/home/rong/tomcat/tf_crawler_chsi/bin/tomcat-juli.jar -Dcatalina.base=/home/rong/tomcat/tf_crawler_chsi -Dcatalina.home=/home/rong/tomcat/tf_crawler_chsi -Djava.io.tmpdir=/home/rong/tomcat/tf_crawler_chsi/temp org.apache.catalina.startup.Bootstrap start01

# 经典面试题
- 最容易忽视的10个JVM问题 https://mp.weixin.qq.com/s/9U1aHd2ZNouPnryvveajzg