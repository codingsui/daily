package cn.syl.zookeeper;

import org.apache.zookeeper.*;

import java.util.ArrayList;
import java.util.List;

public class ZookeeperClient {

    public static void main(String[] args) throws Exception{
        ZookeeperClient server = new ZookeeperClient();

        server.getConnection();

        server.getChildren();

        server.process();
    }

    private void getChildren() throws KeeperException, InterruptedException {
        List<String> childrens = zooKeeper.getChildren("/servers",true);
        List<String> hosts = new ArrayList<>();
        for (String item:childrens) {
            byte[] data = zooKeeper.getData("/servers/" +item,false,null);
            hosts.add(new String(data));
        }
        System.out.println(hosts);
    }

    private ZooKeeper zooKeeper = null;

    private void process() {
        while (true){}
    }


    private void getConnection () throws Exception {
        zooKeeper = new ZooKeeper("localhost:2181", 2000, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                try {
                    getChildren();
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
