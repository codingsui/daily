package cn.syl.java.current;

import lombok.SneakyThrows;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionTest {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition a = lock.newCondition();
        new Thread(){
            @Override
            public void run() {
                try {
                    lock.lock();
                    System.out.println("开始阻塞");
                    a.await();
                    System.out.println("唤醒");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        }.start();
        new Thread(){
            @SneakyThrows
            @Override
            public void run() {
                lock.lock();
                Thread.sleep(3000);
                a.signal();
                lock.unlock();
                System.out.println("去唤醒");
            }
        }.start();
    }
}
