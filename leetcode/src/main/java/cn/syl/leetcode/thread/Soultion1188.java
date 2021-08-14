package cn.syl.leetcode.thread;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Soultion1188 {

    private Lock lock = new ReentrantLock();

    private Condition p = lock.newCondition();

    private Condition c = lock.newCondition();

    private LinkedList linkedList;

    private int capacity;

    public Soultion1188(int capacity) {
        linkedList = new LinkedList();
        this.capacity = capacity;
    }

    public void enqueue(int element) throws InterruptedException {
        try {
            lock.lock();
            while (linkedList.size() == capacity){
                c.signalAll();
                p.await();
            }
                linkedList.addLast(element);
                c.signalAll();
        }finally {
            lock.unlock();
        }
    }

    public int dequeue() throws InterruptedException {
        try {
            lock.lock();
            while (linkedList.size() == 0){
                p.signalAll();
                c.await();
            }

            return (int) linkedList.removeFirst();
        }finally {
            c.signalAll();
            lock.unlock();
        }
    }

    public int size(){
        return linkedList.size();
    }

    public static void main(String[] args) {
        Soultion1188 s = new Soultion1188(2);
        new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                            Thread.sleep(1000);
                        s.enqueue(i);
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
                        System.out.println(s.dequeue());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
