package cn.syl.java.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

public class Cas {
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger(100);
        AtomicStampedReference atomicStampedRef = new AtomicStampedReference(100, 0);

        Thread t1 = new Thread(()->{
           atomicInteger.compareAndSet(100,101);
           atomicInteger.compareAndSet(101,100);
        });
        Thread t2 = new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean a = atomicInteger.compareAndSet(100,101);
            System.out.println(a);
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        Thread t3 = new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicStampedRef.compareAndSet(100,101,atomicStampedRef.getStamp(),atomicStampedRef.getStamp() +1);
            atomicStampedRef.compareAndSet(101,100,atomicStampedRef.getStamp(),atomicStampedRef.getStamp() +1);
        });
        Thread t4 = new Thread(()->{
            int stamp = atomicStampedRef.getStamp();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean b = atomicStampedRef.compareAndSet(100,101,stamp,stamp+1);
            System.out.println(b);

        });
        t3.start();
        t4.start();
        t3.join();
        t4.join();
    }


}
