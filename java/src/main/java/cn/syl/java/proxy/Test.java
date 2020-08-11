package cn.syl.java.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Test {

    public static void main(String[] args) {
        //被代理对象
        ProxyMethod proxyClass = new ProxyClass();

        System.out.println("proxyClass:"+proxyClass);

        MyHandler myHandler = new MyHandler(proxyClass);

        ProxyMethod proxyClassProxy = (ProxyMethod) Proxy.newProxyInstance(proxyClass.getClass().getClassLoader(), proxyClass.getClass().getInterfaces(), myHandler);

        System.out.println("proxyClassProxy:"+proxyClassProxy);

        int i = proxyClassProxy.sayHello();

        System.out.println(i);


    }
}


/**
 * 被代理抽象方法
 */
interface ProxyMethod {
    int sayHello();
}

/**
 * 被代理类
 */
class ProxyClass implements ProxyMethod{

    @Override
    public int sayHello() {

        System.out.println("hello world");

        return 1;
    }
}


class MyHandler implements InvocationHandler {

    // 标识被代理类的实例对象
    private Object realObject;


    // 构造器注入被代理类的对象
    public MyHandler(Object realObject) {
        this.realObject = realObject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println(method.getName());
        System.out.println("开始代理。。。" );

        Object invoke = method.invoke(realObject, args);

        System.out.println("代理结束 result:" + invoke);

        return invoke;
    }
}