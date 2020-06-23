package cn.syl.java.current;

import org.openjdk.jol.info.ClassLayout;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class DeadLock {

    public static void main(String[] args) throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        O object = new O();
        Thread t1 = new Thread(()->{
            synchronized (object){
                System.out.println("t1获取偏向锁，信息：" + ClassLayout.parseInstance(object).toPrintable());
                try {
                    TimeUnit.SECONDS.sleep(6);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("t2获取锁失败导致锁升级,此时thread1还在执行:" + ClassLayout.parseInstance(object).toPrintable());
            }
        });

        Thread t2 = new Thread(()->{
            synchronized (object){
                System.out.println("t2获取偏向锁失败，最终升级为重量级锁，等待thread1执行完毕，获取重量锁成功" + ClassLayout.parseInstance(object).toPrintable());
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        TimeUnit.SECONDS.sleep(5);
        t2.start();
    }
}
class O{
    int a;
}
