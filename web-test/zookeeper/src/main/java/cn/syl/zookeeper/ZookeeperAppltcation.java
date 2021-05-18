package cn.syl.zookeeper;


import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

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
        List<String> list =  zooKeeper.getChildren("/testlocks/c",true);
        logger.info(list.toString());
        Thread.sleep(100000);
    }

    @Test
    public void exist() throws KeeperException, InterruptedException {
        Stat stat =  zooKeeper.exists("/b2an",true);
        logger.info(stat == null ? "no exit" : "exit");
        Thread.sleep(100000);
    }

}
