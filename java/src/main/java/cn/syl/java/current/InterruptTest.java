package cn.syl.java.current;

public class InterruptTest {

    public static void main(String[] args) throws InterruptedException {

        Thread t = new Thread(()->{
            int i=0;
            System.out.println(Thread.currentThread().getName() + ":" + Thread.currentThread().isInterrupted());
            while (!Thread.currentThread().isInterrupted()){
                System.out.println(i++);
            }
            System.out.println(Thread.currentThread().getName() + ":" + Thread.currentThread().isInterrupted());
        });
        t.start();
        Thread.sleep(9);
        System.out.println("==========="+t.isInterrupted());
        t.interrupt();

        System.out.println("+++++++++++++" + t.isInterrupted());
        System.out.println("+++++++++++++" + t.isInterrupted());
        System.out.println("+++++++++++++" + t.isInterrupted());
//        Thread.currentThread().interrupt();
        Thread.interrupted();
        System.out.println("main==========="+Thread.interrupted());
        System.out.println("main==========="+Thread.interrupted());
        System.out.println("main==========="+Thread.interrupted());
    }

}
