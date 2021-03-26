package cn.syl.rabbitmq;

import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibTest {
    public static void main(String[] args) {
        Enhancer enhancer =new Enhancer();
        enhancer.setSuperclass(A.class);
        enhancer.setCallback(new MethodInterceptor(){
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("调用前:" + method.getName());
                Object result = methodProxy.invokeSuper(o,objects);
                System.out.println("调用后" + method.getName());
                return result;
            }
        });
        A a = (A) enhancer.create();
        System.out.println(a);
        a.say();
    }


}
class A{
    public A() {
    }

    public void say(){
        System.out.println("hello world");
    }

    @Override
    public String toString() {
        return "A class :" + getClass();
    }
}