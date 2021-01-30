package cn.syl.java.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Test2 {

    public static void main(String[] args) {
        Subject subject = new SubjectImpl();
        ClassLoader classLoader = subject.getClass().getClassLoader();
        Subject proxy = (Subject) Proxy.newProxyInstance(subject.getClass().getClassLoader()
                ,subject.getClass().getInterfaces(),new ProxyInvocationHandler(subject));
        System.out.println(proxy.hello());


        Subject proxy2 = (Subject) Proxy.newProxyInstance(Subject.class.getClassLoader(),new Class[]{Subject.class},
                new ProxyInvocationHandler2());
        System.out.println(proxy2.hello());
    }
}
interface Subject{
    String hello();
}

class SubjectImpl implements  Subject{
    @Override
    public String hello() {
        return "Success";
    }
}

class ProxyInvocationHandler implements InvocationHandler{

    private Object target;

    public ProxyInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("前置代理方法");
        return method.invoke(target,args);
    }
}

class ProxyInvocationHandler2 implements InvocationHandler{


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        return "success2";
    }
}