package cn.syl.zookeeper.lock;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;
import java.util.concurrent.CountDownLatch;

public class ZklockTest {

    public static void main(String[] args) {
        ZkLock zklock = new ZkLock("lock");
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                try {
                    zklock.lock();
                    int sleep = new Random().nextInt(10000);
                    Thread.sleep(sleep);
                    System.out.println(String.format("%s 睡眠 %s",Thread.currentThread().getName(),sleep));
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    try {
                        zklock.unLock();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

}

 class ZkLock implements Lock{

    private ZooKeeper zooKeeper;

    private CountDownLatch start = new CountDownLatch(1);

    private String lockRoot = "/lockroot";

    private String lockName ;

    private ThreadLocal<String> threadLocal = new ThreadLocal<>();


     public ZkLock(String lockName) {
         this.lockName = lockName;
         init();
     }

     private void init(){
        try {
            zooKeeper = new ZooKeeper("localhost:2181",2000,watchedEvent -> {
                if (watchedEvent.getState() == Watcher.Event.KeeperState.SyncConnected){
                    start.countDown();;
                }
            });
            start.await();
            Stat stat = zooKeeper.exists(lockRoot,false);
            if (stat == null){
                zooKeeper.create(lockRoot,lockRoot.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            System.out.println("===========连接建立成功==========");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     @Override
     public void lock() throws Exception {
        String node = zooKeeper.create(lockRoot + "/" + lockName,lockName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
         List<String> nodes = zooKeeper.getChildren(lockRoot,false);
         TreeSet<String> treeSet = new TreeSet<>();
         nodes.stream().forEach(item->{treeSet.add(lockRoot + "/" + item);});
         String smallNode = treeSet.first();
         String preNode = treeSet.lower(node);
         if (smallNode.equals(node)){
             threadLocal.set(node);
             return;
         }
         CountDownLatch waitForPre = new CountDownLatch(1);
         zooKeeper.exists(preNode,watchedEvent -> {
            if (watchedEvent.getType() == Watcher.Event.EventType.NodeDeleted){
                waitForPre.countDown();
//                System.out.println("监听到之前节点释放");
            }
         });
         waitForPre.await();
         threadLocal.set(node);

     }

     @Override
     public void unLock() throws Exception {
        zooKeeper.delete(threadLocal.get(),-1);
     }
 }
interface Lock{
    void lock() throws Exception;

    void unLock() throws Exception;

}
