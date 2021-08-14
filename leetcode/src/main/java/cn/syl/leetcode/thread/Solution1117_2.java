package cn.syl.leetcode.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

public class Solution1117_2 {

    private Semaphore h = new Semaphore(2);

    private Semaphore o = new Semaphore(1);

    private CyclicBarrier c = new CyclicBarrier(3,()->{
        h.release(2);
        o.release(1);
    });



    private volatile int status = 0;

    public Solution1117_2() {

    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {

        // releaseHydrogen.run() outputs "H". Do not change or remove this line.
        h.acquire();
        releaseHydrogen.run();
        try {
            c.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        o.acquire();
        // releaseOxygen.run() outputs "O". Do not change or remove this line.
        releaseOxygen.run();
        try {
            c.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Solution1117_2 s = new Solution1117_2();


        new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        s.hydrogen(()->{
                            System.out.println("h");
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        s.oxygen(()->{
                            System.out.println("o");
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
