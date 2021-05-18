package cn.syl.zookeeper;

import org.apache.zookeeper.*;

import java.io.IOException;

public class ZooKeeperServer {

    public static void main(String[] args) throws Exception{
        ZooKeeperServer server = new ZooKeeperServer();

        server.getConnection();


        server.regist(args[0]);


        server.process();
    }

    private ZooKeeper zooKeeper = null;

    private void process() {
        while (true){}
    }

    private void regist(String arg) throws KeeperException, InterruptedException {
        zooKeeper.create("/server/serve",arg.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    private void getConnection () throws Exception {
        zooKeeper = new ZooKeeper("localhost:2181", 2000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

            }
        });
    }
}
