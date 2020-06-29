package cn.syl.java.current.aqs;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestInterrupt {

    public static void main(String[] args) throws Exception {
        Lock lock = new ReentrantLock();

//        for (int i = 0; i < 10; i++) {
//            Thread t = new Thread(()->{
//                lock.lock();
//                try {
//                    Thread.sleep(10000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                lock.unlock();
//            });
//            t.start();
//        }
        System.out.println(2 >>> 16);
    }
}
