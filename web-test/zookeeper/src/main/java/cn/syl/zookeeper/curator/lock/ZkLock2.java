package cn.syl.zookeeper.curator.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;

import java.util.List;
import java.util.Random;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;

public class ZkLock2 implements Lock{

    static int count = 0;
    public static void main(String[] args) throws InterruptedException {
        ZkLock2 zkLock = new ZkLock2();

        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                try {
                    zkLock.lock();
                    int sleep = new Random().nextInt(10000);
//                    TimeUnit.MILLISECONDS.sleep(sleep);
                    count++;
                    System.out.println(Thread.currentThread().getName() + " 等待 " + sleep + "s");
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    try {
                        zkLock.unLock();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        Thread.sleep(8000);
        System.out.println(zkLock.count);
    }
    private CuratorFramework zk;

    private ThreadLocal<String> local = new ThreadLocal<>();

    public ZkLock2() {
        zk = CuratorFrameworkFactory.builder()
                .connectString("localhost:2181")
                .sessionTimeoutMs(2000)
                .retryPolicy(new RetryNTimes(3,3000))
                .build();
        zk.start();
    }


    @Override
    public void lock() throws Exception {
        try {

            zk.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                    .forPath("/kpt/locks/lock","a".getBytes());
        }catch (Exception e){
            CountDownLatch countDownLatch = new CountDownLatch(1);
            zk.checkExists().inBackground(new BackgroundCallback() {
                @Override
                public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                    countDownLatch.countDown();
                }
            });
            countDownLatch.await();
            lock();
        }

    }

    @Override
    public void unLock() throws Exception {
        zk.delete().forPath("/kpt/locks/lock");
    }
}
