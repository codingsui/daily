package cn.syl.java.redis.zset;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * https://www.it610.com/article/1290106669979738112.htm
 */
public class NewDemo {


    public static void main(String[] args) throws InterruptedException {
        NewDemo newDemo = new NewDemo();
        for (int i = 0; i < 20; i++) {
            newDemo.add(i+1,i+1);
        }
        long startTime = 10;
        long endTime = 15;
        int page = 0;
        int size = 10;
        System.out.println("获取：" + newDemo.getNewFromTime(startTime,endTime,page,size));
    }

    private Jedis jedis = new Jedis("39.106.39.129",6379);

    private static final String NEW = "new";

    public long add(long newId,long time){
        return jedis.zadd(NEW,time,String.valueOf(newId));
    }

    public Set<String> getNewFromTime(long start, long end,int index,int count){
        return jedis.zrevrangeByScore(NEW,end,start,index,count);
    }
}
