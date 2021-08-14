package cn.syl.java.proxy.cglib;

public class Product {

    public void hello(){
        System.out.println("hello world");
    }

    public void price(String price){
        System.out.println("product price is " + price);
    }


    public String getId(String id){
        return id + "#product";
    }
}
