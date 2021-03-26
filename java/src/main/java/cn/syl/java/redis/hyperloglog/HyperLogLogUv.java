package cn.syl.java.redis.hyperloglog;

import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

/**
 * 统计uv案例
 */
public class HyperLogLogUv {

    public static void main(String[] args) {
        HyperLogLogUv uv = new HyperLogLogUv();
//        uv.initData();
        System.out.println(uv.getUv());
    }

    public void initData(){
        SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleFormatter.format(new Date());
        for (int i = 1; i <= 1000; i++) {
            jedis.pfadd("uv:"+ date,String.valueOf(i));
        }
    }

    public long getUv(){
        SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleFormatter.format(new Date());
        return jedis.pfcount("uv:" + date);
    }
    private Jedis jedis = new Jedis("39.106.39.129",6379);

}
