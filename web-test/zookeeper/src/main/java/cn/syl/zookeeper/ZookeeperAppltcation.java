package cn.syl.zookeeper;


import org.I0Itec.zkclient.IZkChildListener;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ZookeeperAppltcation {

    private static Logger logger = LoggerFactory.getLogger(ZookeeperAppltcation.class);

    private String connect = "localhost:2181";

    private int sessionTimeOut = 2000;

    ZooKeeper zooKeeper = null;

    @Before
    public void init() throws IOException {
        zooKeeper = new ZooKeeper(connect, sessionTimeOut, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                try {
                    List list = zooKeeper.getChildren("/",false);
                    logger.info("watch,{}",list);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Test
    public void createNode() throws KeeperException, InterruptedException {
        String res = zooKeeper.create("/sanguo/a","wuguo".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        logger.info("=======");
        logger.info(res);
    }

    @Test
    public void getAndWatchData() throws KeeperException, InterruptedException {
        List<String> list =  zooKeeper.getChildren("/testlocks/c", new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println(watchedEvent);
            }
        });
        logger.info(list.toString());
        Thread.sleep(100000);
    }
    @Test
    public void watchWrite() throws KeeperException, InterruptedException {
        Stat stat =  zooKeeper.setData("/testlocks/c","1".getBytes(),1);
        logger.info(String.valueOf(stat));
        Thread.sleep(100000);
    }
    @Test
    public void createTest4() throws  Exception{
        zooKeeper.create("/node12", "node12".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT, new AsyncCallback.StringCallback(){
            /**
             * @param rc 状态，0 则为成功，以下的所有示例都是如此
             * @param path 路径
             * @param ctx 上下文参数
             * @param name 路径
             */
            public void processResult(int rc, String path, Object ctx, String name){
                System.out.println("==============");
                System.out.println(rc + " " + path + " " + name +  " " + ctx);
                System.out.println("==============");
            }
        }, "I am context");
        TimeUnit.SECONDS.sleep(100);
        System.out.println("结束");
    }
    @Test
    public void exist() throws KeeperException, InterruptedException {
        Stat stat =  zooKeeper.exists("/b2an",true);
        logger.info(stat == null ? "no exit" : "exit");
        Thread.sleep(100000);
    }

}
