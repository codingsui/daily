package cn.syl.java.redis;

import redis.clients.jedis.Jedis;

public class Test2 {
    public static void main(String[] args) {

        Jedis jedis = new Jedis("39.106.39.129",6379);
        print("set");
        String key = "a1";
        String res = jedis.set(key,key);
        printRes(res);
        res = jedis.setex(key,2,key);
        printRes(res);


        window(jedis);
    }

    private static void print(String set) {
        System.out.println(String.format("========%s============",set));
    }

    private static void printRes(Object set) {
        System.out.println(String.format("========%s============",set.toString()));
    }

    private static void window(Jedis jedis) {

    }
}
