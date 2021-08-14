package cn.syl.leetcode.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

public class Solution1116 {

    private int n;

    private Lock lock = new ReentrantLock();
    private Condition z = lock.newCondition();
    private Condition e = lock.newCondition();
    private Condition o = lock.newCondition();

    private volatile int i = 1;
    private volatile int status = 0;

    public Solution1116(int n) {
        this.n = n;
    }

    public void zero(IntConsumer printNumber) throws InterruptedException{
        try {
            lock.lock();
           while (i<=n){
                if (status != 0){
                    z.await();
                }
                printNumber.accept(0);
                if (i % 2 == 0){
                    status = 2;
                    e.signal();
                }else {
                    status = 1;
                    o.signal();
                }
                z.await();
            }
           e.signal();
           o.signal();
        }
        finally {
            lock.unlock();
        }


    }

    public void even(IntConsumer printNumber)   throws InterruptedException {
        try {
            lock.lock();
            while (i<=n){
                if (status == 2){
                    printNumber.accept(i++);
                    status = 0;
                    z.signal();
                }else {
                    e.await();
                }
            }
        }
        finally {
            lock.unlock();
        }
    }

    public void odd(IntConsumer printNumber) throws InterruptedException  {
        try {

            lock.lock();
           while (i<=n){
               if (status == 1){
                   printNumber.accept(i++);
                   status = 0;
                   z.signal();
               }else {
                   o.await();
               }
            }
        }  finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Solution1116 s = new Solution1116(10);
        new Thread(){
            @Override
            public void run() {
                try {
                    s.zero(item->System.out.println(item));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                try {
                    s.odd(item->System.out.println(item));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                try {
                    s.even(item->System.out.println(item));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
