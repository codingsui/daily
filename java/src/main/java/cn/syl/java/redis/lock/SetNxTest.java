package cn.syl.java.redis.lock;

import redis.clients.jedis.Jedis;

public class SetNxTest {


    public static void main(String[] args) {
        SetNxTest s = new SetNxTest();
        String key = "lcok1";
        String value = "value";
        int time = 1000;
        s.addLock(key,value,time);
    }

    private Jedis jedis = new Jedis("39.106.39.129",6379);

    public void addLock(String key,String value,int time){
        jedis.setnx(key,value);
        jedis.expire(key,time);
    }

}
