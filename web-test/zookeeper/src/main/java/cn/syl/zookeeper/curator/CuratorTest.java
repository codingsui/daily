package cn.syl.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorEventType;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.retry.RetryOneTime;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class CuratorTest {

    private CuratorFramework curatorFramework;
    @Before
    public void init(){
        curatorFramework =  CuratorFrameworkFactory.builder()
                .connectString("localhost:2181")
                .sessionTimeoutMs(2000)
                .retryPolicy(new RetryNTimes(3,3000)).build();
        curatorFramework.start();
    }

    @Test
    public void create() throws Exception{
        curatorFramework.create()
                .withMode(CreateMode.PERSISTENT)
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                .forPath("/curator","a".getBytes());
    }
    @Test
    public void create2() throws Exception{
        curatorFramework.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                .forPath("/curator/a/b","a".getBytes());
    }
    @Test
    public void create3() throws Exception{
        curatorFramework.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.PERSISTENT)
                .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                .inBackground(new BackgroundCallback() {
                    @Override
                    public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                        System.out.println("异步创建成功");
                    }
                })
                .forPath("/curator/a/c/b","a".getBytes());
        System.out.println(curatorFramework.getState());
        TimeUnit.SECONDS.sleep(20);
        System.out.println("done");
    }

    @Test
    public void modify() throws Exception{
        curatorFramework.setData()
                .forPath("/curator/a","c".getBytes());
    }
    @Test
    public void modify2() throws Exception{
        curatorFramework.setData()
                .withVersion(-1)
                .inBackground(new BackgroundCallback() {
                    @Override
                    public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                        if (curatorEvent.getType() == CuratorEventType.SET_DATA){
                            System.out.println(curatorEvent.getPath() + " = " + curatorEvent.getType());
                        }
                    }
                })
                .forPath("/curator/a","d".getBytes());

        TimeUnit.SECONDS.sleep(10);
    }
    @Test
    public void delete() throws Exception{
        curatorFramework.delete().forPath("/curator/a/c/b");
    }

    @Test
    public void delete2() throws Exception{
        curatorFramework.delete()
                .deletingChildrenIfNeeded()
                .withVersion(2)
                .inBackground(new BackgroundCallback() {
                    @Override
                    public void processResult(CuratorFramework curatorFramework, CuratorEvent curatorEvent) throws Exception {
                        System.out.println(curatorEvent.toString());
                    }
                })
                .forPath("/curator/a");
        TimeUnit.SECONDS.sleep(10);

    }

    @Test
    public void readNode() throws Exception{
        byte[] a = curatorFramework.getData().forPath("/curator/a");
        System.out.println(new String(a));
    }

    @Test
    public void readChildren() throws Exception{
        curatorFramework.getChildren().forPath("/curator/a").stream().forEach(System.out::println);
    }

    @Test
    public void watch() throws Exception{
        NodeCache nodeCache = new NodeCache(curatorFramework,"/curator/a");
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.println(nodeCache.getCurrentData().getPath() + "  + " + new String(nodeCache.getCurrentData().getData()));
            }
        });
        nodeCache.start();
        TimeUnit.SECONDS.sleep(20);
        nodeCache.close();
        System.out.println("done");
    }


    @Test
    public void watch2() throws Exception{
        PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework,"/curator/a",true);
        pathChildrenCache.start();
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
                if (pathChildrenCacheEvent != null){
                    System.out.println("------------------");
                    // 获取子节点数据
                    System.out.println(new String(pathChildrenCacheEvent.getData().getData()));
                    // 路径
                    System.out.println(pathChildrenCacheEvent.getData().getPath());
                    // 事件类型
                    System.out.println(pathChildrenCacheEvent.getType());
                    System.out.println("------------------");

                }
            }
        });
        TimeUnit.SECONDS.sleep(20);
        pathChildrenCache.close();
        System.out.println("done");
    }


    @Test
    public void trace(){

    }
}
