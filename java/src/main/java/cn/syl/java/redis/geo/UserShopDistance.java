package cn.syl.java.redis.geo;

import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.Jedis;

public class UserShopDistance {
    public static void main(String[] args) {
        UserShopDistance u = new UserShopDistance();
        u.addUserLocation("张三",11,12);
        u.addUserLocation("沙县小吃",11.22,12);
        System.out.println("距离："+ u.getDistance("张三","沙县小吃"));
    }

    private Jedis jedis = new Jedis("39.106.39.129",6379);

    public long addUserLocation(String uid,double j,double w){
        return jedis.geoadd("location",j,w, String.valueOf(uid));
    }

    public double getDistance(String uid,String shop){
        return jedis.geodist("location", String.valueOf(uid),shop, GeoUnit.KM);
    }
}
