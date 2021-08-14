package cn.syl.java.proxy.cglib;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class FixedValue {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Product.class);
        enhancer.setCallbacks(new Callback[]{new net.sf.cglib.proxy.FixedValue() {
            @Override
            public Object loadObject() throws Exception {
                return "菜鸡";
            }
        },
        new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                System.out.println("before ---");
                return proxy.invokeSuper(obj,args);
            }
        }});
        enhancer.setCallbackFilter(method -> {
            if (method.getName().equals("getId")){
                return 0;
            }else {
                return 1;
            }
        });
        Product p = (Product) enhancer.create();
        System.out.println(p.getId("oo"));
        p.hello();
    }
}
