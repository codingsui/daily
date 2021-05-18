package cn.syl.java.mybatis;

import java.lang.reflect.Proxy;

public class Test {
    public static void main(String[] args) {
        Shop shop = (Shop) Proxy.newProxyInstance(Shop.class.getClassLoader(),new Class[]{Shop.class},(a, b, c)->{
            System.out.println(c[0]);
            return "";
        });
        shop.info(1);
    }
}
interface Shop{
    String info(long id);
}


