package cn.syl.java.redis.zset;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.Set;

public class MusicRankDemo implements MusicRank {

    public static void main(String[] args) {
        MusicRank musicRank = new MusicRankDemo();
//        for (int i = 0; i < 1000; i++) {
//            musicRank.addMusic(i);
//        }
//        for (int i = 0; i < 1000; i++) {
//            for (int j = 0; j < new Random().nextInt(10); j++) {
//                musicRank.incrSorce(i);
//            }
//        }
        System.out.println("当前音乐排行:"+musicRank.getRank(100));
        System.out.println("当前音乐点击数:"+musicRank.getScore(100));
        Set<Tuple> set = musicRank.getRankList(0,10);
        for (Tuple item:set) {
            System.out.println("音乐：" + item.getElement() + " 分数：" + item.getScore() + " 排名：" +musicRank.getRank(Long.valueOf(item.getElement())));

        }
    }

    private Jedis jedis = new Jedis("39.106.39.129",6379);

    @Override
    public long addMusic(long mid) {
        return jedis.zadd("musicrank",0, String.valueOf(mid));
    }

    @Override
    public Double incrSorce(long mid) {
        return jedis.zincrby("musicrank",1, String.valueOf(mid));
    }

    @Override
    public long getRank(long mid) {
        return jedis.zrank("musicrank", String.valueOf(mid));
    }

    @Override
    public Double getScore(long mid){
        return jedis.zscore("musicrank", String.valueOf(mid));
    }

    @Override
    public Set<Tuple> getRankList(long start, long end) {
        return jedis.zrevrangeWithScores("musicrank",start,end);
//        return jedis.zrangeWithScores("musicrank",start,end);
    }
}
interface MusicRank{

    long addMusic(long mid);

    Double incrSorce(long mid);

    long getRank(long mid);

    Set<Tuple> getRankList(long start, long end);

    public Double getScore(long mid);

}