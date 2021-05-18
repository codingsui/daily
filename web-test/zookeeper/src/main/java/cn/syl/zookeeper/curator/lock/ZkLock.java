package cn.syl.zookeeper.curator.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.Random;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ZkLock implements Lock{
    static int count = 0;
    public static void main(String[] args) throws InterruptedException {
        ZkLock zkLock = new ZkLock();
        for (int i = 0; i < 1000; i++) {
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
        Thread.sleep(10000);
        System.out.println(zkLock.count);
    }

    private CuratorFramework zk;

    private ThreadLocal<String> local = new ThreadLocal<>();

    public ZkLock() {
        zk = CuratorFrameworkFactory.builder()
                .connectString("localhost:2181")
                .sessionTimeoutMs(2000)
                .retryPolicy(new RetryNTimes(3,3000))
                .build();
        zk.start();
    }

    @Override
    public void lock() throws Exception {
        String node = zk.create().creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                .forPath("/kpt/locks/lock");
        List<String> list = zk.getChildren().forPath("/kpt/locks");
        TreeSet<String> tree = new TreeSet<>();
        list.stream().forEach(item->{tree.add("/kpt/locks/"+item);});
        String small = tree.first();
        String preNode = tree.lower(node);
        if (small.equals(node)){
            local.set(node);
            return;
        }
        Stat stat = zk.checkExists().forPath("/kpt/locks/lock");
        if (stat == null){
            local.set(node);

            return;
        }
        NodeCache nodeCache = new NodeCache(zk,preNode);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        nodeCache.start();
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                countDownLatch.countDown();
            }
        });
        countDownLatch.await();
        nodeCache.close();
        local.set(node);
    }

    private void waitForLock() throws Exception {
        List<String> list = zk.getChildren().forPath("/kpt/locks");
        TreeSet<String> tree = new TreeSet<>();
        list.stream().forEach(item->{tree.add("/kpt/locks/"+item);});
        String small = tree.first();

    }

    @Override
    public void unLock() throws Exception {
        TimeUnit.MILLISECONDS.sleep(500);
        zk.delete().forPath(local.get());
    }
}
interface Lock{

    void lock() throws Exception;

    void unLock() throws Exception;
}
