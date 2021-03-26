[TOC]

# 基础
### JAVA和C++区别
- 都是面向对象的语言，都支持封装、继承和多态
- Java 不提供指针来直接访问内存，程序内存更加安全
- Java 的类是单继承的，C++ 支持多重继承；虽然 Java 的类不可以多继承，但是接口可以多继承。
- Java 有自动内存管理机制，不需要程序员手动释放无用内存
- 在 C 语言中，字符串或字符数组最后都会有一个额外的字符‘\0’来表示结束。但是，Java 语言中没有结束符这一概念。 这是一个值得深度思考的问题，具体原因推荐看这篇文章： https://blog.csdn.net/sszgg2006/article/details/49148189


参考：https://github.com/Snailclimb/JavaGuide/blob/master/docs/java/Java基础知识.md

### 字符型常量和字符串常量的区别?
- 形式上: 字符常量是单引号引起的一个字符; 字符串常量是双引号引起的若干个字符
- 含义上: 字符常量相当于一个整型值( ASCII 值),可以参加表达式运算; 字符串常量代表一个地址值(该字符串在内存中存放位置)
- 占内存大小 字符常量只占 2 个字节; 字符串常量占若干个字节 (注意： char 在 Java 中占两个字节)

### Java泛型了解么？什么是类型擦除？介绍一下常用的通配符？
#### 泛型
Java 泛型（generics）是 JDK 5 中引入的一个新特性, 泛型提供了编译时类型安全检测机制，该机制允许程序员在编译时检测到非法的类型。泛型的本质是参数化类型，也就是说所操作的数据类型被指定为一个参数。

#### 类型擦出
Java的泛型是伪泛型，这是因为Java在编译期间，所有的泛型信息都会被擦掉，这也就是通常所说类型擦除。如在代码中定义List<Object>和List<String>等类型，在编译后都会变成List，JVM看到的只是List，而由泛型附加的类型信息对JVM是看不到的
```
        ArrayList<Integer> list = new ArrayList<Integer>();

        list.add(1);  //这样调用 add 方法只能存储整形，因为泛型类型的实例为 Integer

        list.getClass().getMethod("add", Object.class).invoke(list, "asd");

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
        output:
        1
        asd
```

####  [Java泛型类型擦除以及类型擦除带来的问题](https://www.cnblogs.com/wuqinglong/p/9456193.html) 

#### [常用的通配符为： T，E，K，V，？](https://juejin.im/post/6844903917835419661)

- ？ 表示不确定的 java 类型  上界通配符< ? extends E>（特定某些类型子类等）下界通配符 < ? super E>（父类 或直至object）
- T (type) 表示具体的一个java类型
- K V (key value) 分别代表java键值中的Key Value
- E (element) 代表Element

### ==和equals的区别
**==** 判断java基本数据类型的数值和引用类型的对象（指针，内存地址）是否相等

**equals()**:判断两个对象是否想到，不能用来比较基本类型，存在于Object类中

Object类equals()方法：
```
public boolean equals(Object obj) {
     return (this == obj);
}
```
###  hashCode()与 equals()
**针对会创建类对应的散列表，eg：HashMap**（如果不会创建，那么它俩毫无关系）

Object的hashCode()方法
```
//通常用来将对象的 内存地址 转换为整数之后返回
public native int hashCode();
```
> hashCode介绍：hashCode()的作用是获取哈希吗，也称为散列码，这个码的作用就是确定对象在哈希表中的索引位置，散列表存储的是键值对，这里的键就是利用了散列码可以快速找到所需要的对象

为什么要有hashCode？检测重复值，以HashSet举例，将对象放入hashset的时候首先计算对象的hashcode来判断对象所存储的位置，如果没有找到就认为对象没有重复出现，如果发现相同hashcode的对象此时才会调用equals方法，判断对象是否真的相同，这样的好处是大大减少了equals方法的执行次数，提高了执行速度。

为什么重写equals时必须重写hashCode？如果两个对象相等，那么他们的hashcode也一定相等，equals方法返回的结果一定是true，但是两个对象的hashcode相同却并不一定是同一个对象

### String = 和 new String（）区别
String a = "abc",在编译期间，JVM会在常量池中寻找是否存在abc，如果不存在，就在常量池中开辟一块空间来存储abc，如果存在就不用开辟。然后在栈内存中开盘一个名字为a的内存，来存储abc在常量池中的地址值。

String b = new String("abc"),在编译期间，JVM先去常量池中查是否右abc存在，如果不存在，在常量池中开辟一块空间存储abc。运行期间通过String构造器在堆中new一个空间，然后将常量池中的abc复制一份到该堆空间，在栈中开辟名为b的空间，存放堆中new出来的String对象地址值。
```
String类equals()方法：

public boolean equals(Object anObject) {
    if (this == anObject) {
        return true;
    }
    if (anObject instanceof String) {
        String anotherString = (String)anObject;
        int n = value.length;
        if (n == anotherString.value.length) {
            char v1[] = value;
            char v2[] = anotherString.value;
            int i = 0;
            while (n-- != 0) {
                if (v1[i] != v2[i])
                    return false;
                i++;
            }
            return true;
        }
    }
    return false;
}
```
### int float short double long char 占字节数？
java有8种基本类型分别为以下

| 基本类型          | 字节数      | 位数 |
| ----------------- | ----------- | ---- |
| byte              | 1           | 8    |
| short             | 2           | 16   |
| float             | 4           | 32   |
| int               | 4           | 32   |
| double            | 8           | 64   |
| long              | 8           | 64   |
| char（默认u0000） | 2           | 16   |
| boolean           | 逻辑上占1位 |      |

**int long short byte范围 =[- 2^(n-1)^~ 2^(n-1)^ -1]**
### int 范围？float 范围？double

| 类型   | 范围                                         | 原因                                             |
| ------ | -------------------------------------------- | ------------------------------------------------ |
| int    | -2^31 ~ 2^31-1                               |                                                  |
| float  | -2^128 ~ 2^128 或 -3.40E+38 ~ +3.40E+38      | 1bit（符号位） 8bits（指数位） 23bits（尾数位）  |
| double | -2^1024 ~ +2^1024 或 -1.79E+308 ~ +1.79E+308 | 1bit（符号位） 11bits（指数位） 52bits（尾数位） |


参考：
java float与double的范围和精度：https://www.cnblogs.com/kakaisgood/p/8023017.html

### 什么是自动装箱与拆箱？
装箱：将基本类型用它们对应的引用类型包装起来；
拆箱：将包装类型转换为基本数据类型

### Integer a = 1,Integer b = 1 ,a == b ?
相等，Integer a = 1，再编译的时候调用的是Integer.valueOf(i)方法，而在Integer类中对其有缓存如果范围[-128,127]之间都是可以直接返回的
```
public static Integer valueOf(int i) {
        if(i >= -128 && i <= IntegerCache.high)
            return IntegerCache.cache[i + 128];
        else
            return new Integer(i);
    }
```
==注意==，Java 基本类型的包装类的大部分都实现了常量池技术，即 Byte,Short,Integer,Long,Character,Boolean；前面 4 种包装类默认创建了数值[-128，127] 的相应类型的缓存数据，Character创建了数值在[0,127]范围的缓存数据，Boolean 直接返回True Or False。如果超出对应范围仍然会去创建新的对象。

### 为什么 Java 中只有值传递？
按值调用(call by value)表示方法接收的是调用者提供的值，而按引用调用（call by reference)表示方法接收的是调用者提供的变量地址。一个方法可以修改传递引用所对应的变量值，而不能修改传递值调用所对应的变量值。 Java 程序设计语言总是采用按值调用。也就是说，方法得到的是所有参数值的一个拷贝，也就是说，方法不能修改传递给它的任何参数变量的内容。

### 重载和重写的区别
> 重载:同一个类中多个同名方法根据不同的传参来执行不同的逻辑处理

重载规则:
- 被重载的方法必须改变参数列表(参数个数或类型不一样)；
- 被重载的方法可以改变返回类型；
- 被重载的方法可以改变访问修饰符；
- 被重载的方法可以声明新的或更广的检查异常；
- 方法能够在同一个类中或者在一个子类中被重载。
- 无法以返回值类型作为重载函数的区分标准。

> 重写：发生在运行期，子类对可以访问父类的方法的实现进行重新编写。

- 参数列表与被重写方法的参数列表必须完全相同。
- 返回类型与被重写方法的返回类型可以不相同，但是必须是父类返回值的派生类（java5 及更早版本返回类型要一样，java7 及更高版本可以不同）。
- 访问权限不能比父类中被重写的方法的访问权限更低。例如：如果父类的一个方法被声明为 public，那么在子类中重写该方法就不能声明为 protected。
- 父类的成员方法只能被它的子类重写。
- 声明为 final 的方法不能被重写。
- 声明为 static 的方法不能被重写，但是能够被再次声明。
- 子类和父类在同一个包中，那么子类可以重写父类所有方法，除了声明为 private 和 final 的方法。
- 子类和父类不在同一个包中，那么子类只能够重写父类的声明为 public 和 protected 的非 final 方法。
- 重写的方法能够抛出任何非强制异常，无论被重写的方法是否抛出异常。但是，重写的方法不能抛出新的强制性异常，或者比被重写方法声明的更广泛的强制性- 异常，反之则可以。
- 构造方法不能被重写。
- 如果不能继承一个方法，则不能重写这个方法。


方法的重写要遵循“两同两小一大”（以下内容摘录自《疯狂 Java 讲义》,issue#892 ）：

“两同”即方法名相同、形参列表相同；
“两小”指的是子类方法返回值类型应比父类方法返回值类型更小或相等，子类方法声明抛出的异常类应比父类方法声明抛出的异常类更小或相等；
“一大”指的是子类方法的访问权限应比父类方法的访问权限更大或相等。


**区别**

| 区别点     | 重载     | 重写                                                         |
| ---------- | -------- | ------------------------------------------------------------ |
| 发生范围   | 同一个类 | 子类                                                         |
| 参数列表   | 必须修改 | 一定不能修改                                                 |
| 返回类型   | 可修改   | 子类方法返回值类型应比父类方法返回值类型更小或相等           |
| 异常       | 可修改   | 子类方法声明抛出的异常类应比父类方法声明抛出的异常类更小或相等； |
| 访问修饰符 | 可修改   | 一定不能做更严格的限制（可以降低限制）                       |
| 发生阶段   | 编译期   | 运行期                                                       |

### 深拷贝、浅拷贝区别

>浅拷贝:对基本数据类型进行值传递，对引用数据类型进行传递般的拷贝。增加了一个指针指向已存在的内存地址，仅仅是指向被复制的内存地址，如果原地址发生改变，那么浅复制出来的对象也会相应的改

>深拷贝:对基本数据类型进行值传递，对引用数据类型，创建一个新的对象，并复制其内容。增加了一个指针并且申请了一个新的内存，使这个增加的指针指向这个新的内存。 在计算机中开辟一块新的内存地址用于存放复制的对象

 implements Cloneable

参考：https://www.cnblogs.com/qingdaye/p/12605281.html

***区别***

深拷贝和浅拷贝最根本的区别在于是否真正获取一个对象的复制实体，而不是引用。

### java 异常体系？RuntimeException Exception Error 的区别，举常见的例子
![image](https://note.youdao.com/yws/public/resource/53b088853ba2efc078c3e41f7996b610/xmlnote/759B5C38970143629B8411E7FBD80B87/15679)

在java中Throwable是所有异常的共同祖先。他有两个子类Exception和Error。
- Exception：程序可以处理的异常
- Error：程序无法处理的错误。表示程序中较为严重的问题。大多数发生在JVM中，常见的有OOM，stackOverFlowError
- 注意：异常和错误的区别：异常能被程序本身处理，错误是无法处理。

Throwable 类常用方法

- public string getMessage():返回异常发生时的简要描述
- public string toString():返回异常发生时的详细信息
- public string getLocalizedMessage():返回异常对象的本地化信息。使用 Throwable 的子类覆盖这个方法，可以生成本地化信息。如果子类没有覆盖该方法，则该方法返回的信息与 getMessage（）返回的结果相同
- public void printStackTrace():在控制台上打印 Throwable 对象封装的异常信息
### try-with-resources与try-catch-finally区别？
>try-with-resources是java7新出的语法糖，它不用我们在手动关闭io资源，但是它需要声明的资源实现AutoClosable和Closeable 接口，若try中用到了多个资源类，则close方法会按照与其声明时相反的顺序依次调用。遇到了异常，close关闭语句会先于catch语句执行。如果try代码块中抛出了异常，则控制权转移到catch部分。在控制权跳转过程中，该资源会自动调用close()方法。　在用close()方法关闭该资源时，可能会抛出一个异常，如示例中的Lion所示，该关闭异常将被抑制，而try代码块中抛出的异常（try{}中调用close方法前被抛出的异常），会被try-with-resources语句真正抛出。　　那我们如何查看这些被抑制的异常呢？答案是用Throwable.getSuppressed()方法来获取。


```
try (Scanner scanner = new Scanner(new File("test.txt"))) {
    while (scanner.hasNext()) {
        System.out.println(scanner.nextLine());
    }
} catch (FileNotFoundException fnfe) {
    fnfe.printStackTrace();
}
```
```
public interface AutoClosable {
    public void close() throws Exception;
}

```
参考：https://blog.csdn.net/frgod/article/details/53414813
### lambda 表达式中使用外部变量，为什么要 final？

## 面向对象
### 面向对象和面向过程的区别？
### 构造器 Constructor 是否可被 override?
构造器不可以被重写，但是可以被重载

### 无参构造器的作用？
java在执行子类构造方法的时候，如果没有super显示的调用父类构造方法，那么就会隐式的调用父类的无参构造器，因此，如果父类没有定义无参构造器，子类没有显示调用父类构造方法，那么编译时就会发生错误

### 成员变量与局部变量的区别有哪些？
### 创建一个对象用什么运算符?对象实体与对象引用有何不同?
new 运算符，new 创建对象实例（对象实例在堆内存中），对象引用指向对象实例（对象引用存放在栈内存中）。一个对象引用可以指向 0 个或 1 个对象（一根绳子可以不系气球，也可以系一个气球）;一个对象可以有 n 个引用指向它（可以用 n 条绳子系住一个气球）

###  在调用子类构造方法之前会先调用父类没有参数的构造方法,其目的是?
帮助子类做初始化工作

### 面向对象三大特征
#### 封装
封装是指把一个对象的状态信息（也就是属性）隐藏在对象内部，不允许外部对象直接访问对象的内部信息。但是可以提供一些可以被外界访问的方法来操作属性。eg：遥控器
#### 继承
不同类型的对象，相互之间经常有一定数量的共同点，继承是使用已存在的类的定义作为基础建立新类的技术，新类的定义可以增加新的数据或新的功能，也可以用父类的功能，但不能选择性地继承父类。通过使用继承，可以快速地创建新的类，可以提高代码的重用，程序的可维护性，节省大量创建新类的时间 ，提高我们的开发效率。
#### 多态
表示一个对象具有多种的状态。具体表现为父类的引用指向子类的实例

### String StringBuffer 和 StringBuilder 的区别是什么? String 为什么是不可变的?
String为什么不可变？ 因为String类内部采用final修饰的字符数组保存字符串，priavte final char[] value，所以String对象是不可变的

StringBuffer和StringBuilder都继承了AbstractStringBuilder，内部的字符数组没有用final修饰，是可变的，但是StringBuffer对相关的方法加了同步锁synchiorc，所以是线程安全的，而StringBuilder是线程不安全的

### Object 类
```
public final native Class<?> getClass()//native方法，用于返回当前运行时对象的Class对象，使用了final关键字修饰，故不允许子类重写。

public native int hashCode() //native方法，用于返回对象的哈希码，主要使用在哈希表中，比如JDK中的HashMap。
public boolean equals(Object obj)//用于比较2个对象的内存地址是否相等，String类对该方法进行了重写用户比较字符串的值是否相等。

protected native Object clone() throws CloneNotSupportedException//naitive方法，用于创建并返回当前对象的一份拷贝。一般情况下，对于任何对象 x，表达式 x.clone() != x 为true，x.clone().getClass() == x.getClass() 为true。Object本身没有实现Cloneable接口，所以不重写clone方法并且进行调用的话会发生CloneNotSupportedException异常。

public String toString()//返回类的名字@实例的哈希码的16进制的字符串。建议Object所有的子类都重写这个方法。

public final native void notify()//native方法，并且不能重写。唤醒一个在此对象监视器上等待的线程(监视器相当于就是锁的概念)。如果有多个线程在等待只会任意唤醒一个。

public final native void notifyAll()//native方法，并且不能重写。跟notify一样，唯一的区别就是会唤醒在此对象监视器上等待的所有线程，而不是一个线程。

public final native void wait(long timeout) throws InterruptedException//native方法，并且不能重写。暂停线程的执行。注意：sleep方法没有释放锁，而wait方法释放了锁 。timeout是等待时间。

public final void wait(long timeout, int nanos) throws InterruptedException//多了nanos参数，这个参数表示额外时间（以毫微秒为单位，范围是 0-999999）。 所以超时的时间还需要加上nanos毫秒。

public final void wait() throws InterruptedException//跟之前的2个wait方法一样，只不过该方法一直等待，没有超时时间这个概念

protected void finalize() throws Throwable { }//实例被垃圾回收器回收的时候触发的操作

```


# Servlet生命周期？
Servlet接口定义了5个方法，其中前三个方法与Servlet生命周期相关：

```java
void init(ServletConfig config) throws ServletException
void service(ServletRequest req, ServletResponse resp) throws ServletException, java.io.IOException
void destroy()
java.lang.String getServletInfo()
ServletConfig getServletConfig()
```
生命周期： Web容器加载Servlet并将其实例化后，Servlet生命周期开始，容器运行其init()方法进行Servlet的初始化；请求到达时调用Servlet的service()方法，service()方法会根据需要调用与请求对应的doGet或doPost等方法；当服务器关闭或项目被卸载时服务器会将Servlet实例销毁，此时会调用Servlet的destroy()方法。init方法和destroy方法只会执行一次，service方法客户端每次请求Servlet都会执行。Servlet中有时会用到一些需要初始化与销毁的资源，因此可以把初始化资源的代码放入init方法中，销毁资源的代码放入destroy方法中，这样就不需要每次处理客户端的请求都要初始化与销毁资源。