package cn.syl.java.current.utils;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierTest {

    public static void main(String[] args) {
        ExecutorService service = Executors.newCachedThreadPool();
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(3);
        for (int i=0;i<3;i++){
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(new Random().nextInt(10000));
                        System.out.println("运动员" + Thread.currentThread().getName()
                                + "即将到达集合地点1：当前已有" + (cyclicBarrier.getNumberWaiting()+1)
                                + (cyclicBarrier.getNumberWaiting() == 2?"已经到齐，继续走":"正在等候") );
                        if (Thread.currentThread().getName().contains("pool-1-thread-2")){
                            Thread.sleep(200000);
                        }
                        cyclicBarrier.await();
                        Thread.sleep(new Random().nextInt(10000));
                        System.out.println("运动员" + Thread.currentThread().getName()
                                + "即将到达集合地点2：当前已有" + (cyclicBarrier.getNumberWaiting()+1)
                                + (cyclicBarrier.getNumberWaiting() == 2?"已经到齐，继续走":"正在等候"));
                        cyclicBarrier.await();
                        Thread.sleep(new Random().nextInt(10000));
                        System.out.println("运动员" + Thread.currentThread().getName()
                                + "即将到达集合地点3：当前已有" + (cyclicBarrier.getNumberWaiting()+1)
                                + (cyclicBarrier.getNumberWaiting() == 2?"已经到齐，继续走":"正在等候"));

                        cyclicBarrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    } finally {

                    }
                }
            };
            service.execute(runnable);
        }
    }
}
