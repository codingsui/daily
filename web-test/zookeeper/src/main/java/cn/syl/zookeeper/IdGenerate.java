package cn.syl.zookeeper;

import org.apache.zookeeper.*;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class IdGenerate {

    private static Logger logger = LoggerFactory.getLogger(IdGenerate.class);

    private static String connect = "localhost:2181";

    private static int sessionTimeOut = 2000;

    static ZooKeeper zooKeeper = null;

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    public static void main(String[] args) throws Exception {
        zooKeeper = new ZooKeeper(connect, sessionTimeOut, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                countDownLatch.countDown();
                System.out.println("zk的监听器" + watchedEvent.getType());
            }
        });

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3,3,0, TimeUnit.SECONDS,new ArrayBlockingQueue<>(100));
        for (int i = 0; i < 100; i++) {
            threadPoolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    String s = null;
                    try {
                        s = zooKeeper.create("/idGenerate","1".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(s);
                }
            });
        }
        TimeUnit.SECONDS.sleep(5000);
        threadPoolExecutor.shutdown();

    }


}
