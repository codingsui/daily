package cn.syl.java.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.Random;
import java.util.Set;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        Jedis jedis = new Jedis("39.106.39.129",6379);
        String res = jedis.set("111","111");
        System.out.println(res);
        ScanParams scanParams = new ScanParams();
        scanParams.match("k*");
        scanParams.count(2);
        ScanResult<String> result =  jedis.scan("6",scanParams);
//        System.out.println(result.getStringCursor());
        System.out.println(result.getResult());

        boolean ret=jedis.setbit("bitmap",2L,"1");
        System.out.println(ret);
        Long a = jedis.bitcount("bitmap");
        System.out.println(a);


        String key = new Random().nextInt(100) + "";
        Long l = jedis.zadd("send",System.currentTimeMillis() ,key);
        System.out.println("=======");
        System.out.println(l);
        Thread.sleep(1000);
        Set<String> set = jedis.zrangeByScore("send",0,System.currentTimeMillis());
        for (String item : set) {
            System.out.println(item);
        }
        l = jedis.zrank("send",key);
        System.out.println(l);



    }
}
