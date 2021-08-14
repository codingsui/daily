package cn.syl.java.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;

public class NoOp {
    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Product.class);
        enhancer.setCallback(net.sf.cglib.proxy.NoOp.INSTANCE);
        Product p = (Product) enhancer.create();
        p.hello();
    }
}
