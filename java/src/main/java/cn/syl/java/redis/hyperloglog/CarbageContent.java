package cn.syl.java.redis.hyperloglog;

import redis.clients.jedis.Jedis;

public class CarbageContent {

    public static void main(String[] args) {
        CarbageContent c = new CarbageContent();
        System.out.println(c.isCarbageContent("哈哈哈哈"));
        System.out.println(c.isCarbageContent("哈哈哈哈"));
        System.out.println(c.isCarbageContent("哈哈哈哈"));
        System.out.println(c.isCarbageContent("1"));
        System.out.println(c.isCarbageContent("2"));
        System.out.println(c.isCarbageContent("3"));
    }

    private Jedis jedis = new Jedis("39.106.39.129",6379);


    public long isCarbageContent(String content){
        return jedis.pfadd("isCarbageContent",content);
    }
}
