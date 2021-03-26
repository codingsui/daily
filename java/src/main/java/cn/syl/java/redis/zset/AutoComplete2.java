package cn.syl.java.redis.zset;

import redis.clients.jedis.Jedis;

import java.util.Set;


/**
 * 在原来基础上增加了搜索过期时间
 */
public class AutoComplete2 {

    public static void main(String[] args) {
        AutoComplete2 autoComplete = new AutoComplete2();
        autoComplete.search("我爱大家");
        autoComplete.search("我喜欢学习redis");
        autoComplete.search("我很喜欢一个城市");
        autoComplete.search("我不太喜欢玩儿");
        autoComplete.search("我喜欢学习Java");


        System.out.println(autoComplete.autoComplete("我"));
        System.out.println("======================");
        System.out.println(autoComplete.autoComplete("我喜"));
        System.out.println("======================");
        System.out.println(autoComplete.autoComplete("我喜欢"));
        System.out.println("======================");
        System.out.println(autoComplete.autoComplete("我不"));
        System.out.println("======================");

    }

    private Jedis jedis = new Jedis("39.106.39.129",6379);

    /**
     * 建立倒排索引
     * @param words
     */
    public void search(String words){
        StringBuffer sb = new StringBuffer();
        for (char item : words.toCharArray()){
            sb.append(item);
            jedis.zadd("keys:"+sb.toString(),System.currentTimeMillis(),words);
            jedis.expire("keys:"+sb.toString(),10000);
        }
    }

    public Set<String> autoComplete(String keys){
        jedis.expire("keys:"+keys,10000);
        return jedis.zrevrange("keys:"+keys,0,4);
    }
}
