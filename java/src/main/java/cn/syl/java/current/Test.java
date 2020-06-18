package cn.syl.java.current;

public class Test {


    public static void main(String[] args) {

        synchronized (Test.class){
            method();
        }
    }
    private static void method(){}
}
