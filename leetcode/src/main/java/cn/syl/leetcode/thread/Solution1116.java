package cn.syl.leetcode.thread;

import java.util.concurrent.*;

public class Solution1115 {

    private ArrayBlockingQueue<Integer> s1 = new ArrayBlockingQueue<Integer>(1);
    private ArrayBlockingQueue<Integer> s2 = new ArrayBlockingQueue<Integer>(1);
    private int n;

    public Solution1115(int n) {
        this.n = n;
        try {
            s1.put(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            // printFoo.run() outputs "foo". Do not change or remove this line.
            s1.take();
            printFoo.run();
            s2.put(i);
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            s2.take();
            // printBar.run() outputs "bar". Do not change or remove this line.
            printBar.run();
            s1.put(i);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Solution1115 s = new Solution1115(2);
        new Thread(){
            @Override
            public void run() {
                try {
                    s.foo(()->{
                        System.out.print("foo");
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                try {
                    s.bar(()->{
                        System.out.print("bar" + " ");
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
}
