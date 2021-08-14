package cn.syl.leetcode.thread;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

public class Solution1116_2 {

    private int n;

    private Semaphore z = new Semaphore(1);
    private Semaphore o = new Semaphore(0);
    private Semaphore e = new Semaphore(0);
    

    public Solution1116_2(int n) {
        this.n = n;
    }

    public void zero(IntConsumer printNumber) throws InterruptedException{
        for (int i = 1; i <= n; i++) {
            z.acquire();
            printNumber.accept(0);
            if (i % 2 == 0){
                e.release();
            }else {
                o.release();
            }
        }
    }
    public void even(IntConsumer printNumber) throws InterruptedException  {
        for (int i = 2; i <= n; i+=2) {
            e.acquire();
            printNumber.accept(i);
            z.release();
        }
    }
    public void odd(IntConsumer printNumber) throws InterruptedException {
        for (int i = 1; i <= n; i+=2) {
            o.acquire();
            printNumber.accept(i);
            z.release();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Solution1116_2 s = new Solution1116_2(5);
        new Thread(){
            @Override
            public void run() {
                try {
                    s.even(item->{System.out.println( "en" + item);
                    if (item % 2!= 0){
                        throw new RuntimeException();
                    }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        Thread.sleep(1000);
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
                    s.odd(item->System.out.println("odd" + item));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
