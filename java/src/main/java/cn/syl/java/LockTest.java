package cn.syl.java;

public class LockTest {

    public static void main(String[] args) {
        Object lock1 = new Object();
        Object lock2 = new Object();
        Thread t1 = new Thread(new Runnable1(lock1,lock2));
        Thread t2 = new Thread(new Runnable1(lock2,lock1));

        t1.start();
        t2.start();

    }

    static class Runnable1 implements Runnable{

        private Object object1;

        private Object object2;


        public Runnable1(Object object1, Object object2) {
            this.object1 = object1;
            this.object2 = object2;
        }

        @Override
        public void run() {
            synchronized (object1){
                System.out.println("线程"+ Thread.currentThread().getName() + "获取锁object1");

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (object2){
                    System.out.println("线程"+ Thread.currentThread().getName() + "获取锁object2");
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
