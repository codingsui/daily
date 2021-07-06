package cn.syl.java.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class SingleCallback {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Product.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                System.out.println("one intercept before");
                Object result = proxy.invokeSuper(obj,args);
                System.out.println("one intercept after");
                return result;
            }
        });
        Product p = (Product) enhancer.create();
        p.hello();
    }
}
