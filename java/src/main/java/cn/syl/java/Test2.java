package cn.syl.java;

public class Test2 {


    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        Thread t1 = new Thread(new Runnable1(lock));
        Thread t2 = new Thread(new Runnable2(lock));
        t1.start();
        Thread.sleep(1000);
        t2.start();
    }

    static class Runnable1 implements Runnable{

        private Object lock;

        private int i = 1;

        public Runnable1(Object lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock){
                while (i<27){
                    System.out.println(i++);
                    try {
                        lock.notify();
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
    }
    static class Runnable2 implements Runnable{

        private Object lock;

        private int i = 65;


        public Runnable2(Object lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock){
                while (i<91){
                    System.out.println((char)i);
                    i++;
                    try {
                        lock.notify();
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

        }
    }
}
