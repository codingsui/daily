package cn.syl.java.redis.geo;

import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;

import java.util.List;

public class NearByUser {
    public static void main(String[] args) {
        NearByUser u = new NearByUser();
        u.addUserLocation("张三",11,12);
        u.addUserLocation("沙县小吃",11.22,12);
        u.addUserLocation("喔喔炒鸡",11.02,12);
        u.addUserLocation("夜来香小龙虾",11.12,12);
        u.addUserLocation("海底捞",12.12,12);
        u.getNearByUsers("张三",10).stream().forEach(item->{
            System.out.println(item.getMemberByString() + "，距离：" + item.getDistance());
        });
    }

    private Jedis jedis = new Jedis("39.106.39.129",6379);

    public long addUserLocation(String uid,double j,double w){
        return jedis.geoadd("location",j,w, String.valueOf(uid));
    }

    public List<GeoRadiusResponse> getNearByUsers(String uid, double radios){
        return jedis.georadiusByMember("location", uid,radios, GeoUnit.KM);
    }
}
