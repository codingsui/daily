package cn.syl.java.current.utils;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangeTest {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);
        Exchanger exchanger = new Exchanger();
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String data = "100";
                    System.out.println("线程" + Thread.currentThread().getName() + "花" + data + "RMB买货物");
                    Thread.sleep((long)(Math.random() * 10000));
                    data = (String) exchanger.exchange(data);
                    System.out.println("线程" + Thread.currentThread().getName() + "已经收到货：" + data);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        service.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String data = "10斤鸡蛋";
                    System.out.println("线程" + Thread.currentThread().getName() + "准备将：" + data + "卖出");
                    Thread.sleep((long)(Math.random() * 10000));
                    data = (String) exchanger.exchange(data);
                    System.out.println("线程" + Thread.currentThread().getName() + "已经收到" + data + "RMB");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        service.shutdown();
    }
}
