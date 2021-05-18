package cn.syl.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.List;
import java.util.Random;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;

public class ZooKeeperLock extends AbstractZKLockMutex{

    public static void main(String[] args) {
        ZooKeeperLock zk = new ZooKeeperLock();
        for (int i = 0; i < 10; i++) {
            new Thread(){
                @Override
                public void run() {
                    try {
                        zk.lock();
                        int a = new Random().nextInt(10000);
                        Thread.sleep(a);
                        System.out.println("睡眠:"+a + "-id:" + this.getName());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            zk.unLock();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        }
    }

    private ZooKeeper zooKeeper = null;

    private CountDownLatch start = new CountDownLatch(1);

    private CountDownLatch launch;

    private String path = "/testlocks";

    private String beforeNode = "";

    private ThreadLocal<String> local = new ThreadLocal<>();




    public ZooKeeperLock() {
        init();
    }

    private void init(){
        try {
            zooKeeper = new ZooKeeper("localhost:2181",20000,watchedEvent -> {
                if (watchedEvent.getState() == Watcher.Event.KeeperState.SyncConnected){
                        Stat stat = null;
                    try {
                        stat = zooKeeper.exists(path,false);
                        if (stat == null){
                            zooKeeper.create(path,path.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    start.countDown();

                }
                if (launch != null){
                    launch.countDown();
                }
            });
            start.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean tryLock(){
        try {
            String node = zooKeeper.create( "/testlocks/c/a", "a".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
            List<String> locks = zooKeeper.getChildren("/testlocks/c",false);
            TreeSet<String> sortedNodes = new TreeSet<>();
            locks.stream().forEach(item->sortedNodes.add("/testlocks/c/"+item));
            String preNode = sortedNodes.lower(node);
            String smallNode = sortedNodes.first();
            if (smallNode.equals(node)){
                local.set(node);
                return true;
            }
            CountDownLatch countDownLatch = new CountDownLatch(1);
            zooKeeper.exists(preNode,watchedEvent -> {
                if (watchedEvent.getType() == Watcher.Event.EventType.NodeDeleted){
                    countDownLatch.countDown();
                }
            });
            countDownLatch.await();
            local.set(node);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    void waitForLock() throws Exception {

    }

    @Override
    public void unLock() throws Exception {
        zooKeeper.delete(local.get(),-1);
    }
}

interface ZkLock{
    void lock() throws Exception;

    void unLock() throws Exception;
}

abstract class AbstractZKLockMutex implements ZkLock {
    @Override
    public void lock() throws Exception {
        if (tryLock()){
            System.out.println("获取到锁");
        }else {
            waitForLock();
        }
    }


    abstract boolean tryLock();

    abstract void waitForLock() throws  Exception;

    public abstract void unLock() throws Exception;
}

