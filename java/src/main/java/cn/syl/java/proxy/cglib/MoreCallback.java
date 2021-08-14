package cn.syl.java.proxy.cglib;

import net.sf.cglib.proxy.*;

import java.lang.reflect.Method;

public class MoreCallback {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Product.class);
        enhancer.setCallbacks(new Callback[]{
                new MethodInterceptor() {
                    @Override
                    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                        System.out.println("one intercept before");
                        Object result = proxy.invokeSuper(obj,args);
                        System.out.println("one intercept after");
                        return result;
                    }
                },
                new MethodInterceptor() {
                    @Override
                    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                        System.out.println("two intercept before");
                        Object result = proxy.invokeSuper(obj,args);
                        System.out.println("two intercept after");
                        return result;
                    }
                },
        });
        enhancer.setCallbackFilter(new CallbackFilter() {
            @Override
            public int accept(Method method) {
                if (method.getName().equals("price")){
                    //用第一个拦截器
                    return 0;
                }
                //用第二个拦截器
                return 1;
            }
        });
        Product p = (Product) enhancer.create();
        p.hello();
        p.price("10");
    }
}
