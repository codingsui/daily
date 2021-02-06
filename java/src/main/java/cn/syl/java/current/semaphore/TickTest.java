package cn.syl.java.current.semaphore;

import java.util.concurrent.Semaphore;

public class TickTest {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);
        for (int i = 0; i < 20; i++) {
            new Thread(){
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                        String name = Thread.currentThread().getName();
                        System.out.println(name + "开始买票");
                        Thread.sleep(2000);
                        System.out.println(name + "买票成功");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }finally {
                        semaphore.release();
                    }
                }
            }.start();
        }
    }


}
