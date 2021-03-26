package cn.syl.java.redis.zset;

import redis.clients.jedis.Jedis;

import java.util.Set;

public class AutoComplete {

    public static void main(String[] args) {
        AutoComplete autoComplete = new AutoComplete();
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
        }
    }

    public Set<String> autoComplete(String keys){
        return jedis.zrevrange("keys:"+keys,0,4);
    }
}
