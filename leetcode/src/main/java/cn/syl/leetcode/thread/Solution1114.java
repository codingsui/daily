package cn.syl.leetcode.thread;

import java.util.concurrent.CountDownLatch;

public class Solution1114 {
    private CountDownLatch l1 = new CountDownLatch(1);
    private CountDownLatch l2 = new CountDownLatch(1);
    public Solution1114() {

    }

    public void first(Runnable printFirst) throws InterruptedException {
        // printFirst.run() outputs "first". Do not change or remove this line.
        printFirst.run();
        l1.countDown();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        l1.await();
        // printSecond.run() outputs "second". Do not change or remove this line.
        printSecond.run();
        l2.countDown();
    }

    public void third(Runnable printThird) throws InterruptedException {
        l2.await();
        // printThird.run() outputs "third". Do not change or remove this line.
        printThird.run();
    }
}
