package cn.syl.java.redis.set;

import redis.clients.jedis.Jedis;

import java.util.Set;

public class LotteryDemo implements Lottery{


    public static void main(String[] args) {
        Lottery lottery = new LotteryDemo();
        for (int i = 1; i < 10; i++) {
            lottery.add(i);
        }
        System.out.println("参与抽奖人数:" + lottery.userCount());
        System.out.println("参与抽奖人员：" + lottery.members());
        System.out.println("测试随机挑选一个：" + lottery.getOne());
        System.out.println("参与抽奖人数:" + lottery.userCount());
        System.out.println("开始抽奖：" + lottery.getAny(3));
//        System.out.println("剩余抽奖人员：" + lottery.members());
        System.out.println("参与抽奖人数:" + lottery.userCount());

    }

    private Jedis jedis = new Jedis("39.106.39.129",6379);

    private static final String LOTTERY = "LOTTERY";

    @Override
    public long add(long userId) {
        return jedis.sadd(LOTTERY, String.valueOf(userId));
    }

    @Override
    public long userCount() {
        return jedis.scard(LOTTERY);
    }

    @Override
    public Set<String> members() {
        return jedis.smembers(LOTTERY);
    }

    /**
     * 只返回不移除
     * @return
     */
    @Override
    public String getOne() {
        return jedis.srandmember(LOTTERY);
    }

    /**
     * spop移除并返回
     * @param count
     * @return
     */
    @Override
    public Set<String> getAny(int count) {
        return jedis.spop(LOTTERY,count);
    }
}

interface Lottery{

    /**
     * 添加人员
     * @param userId
     * @return
     */
    long add(long userId);

    long userCount();

    Set<String> members();

    String getOne();

    Set<String> getAny(int count);
}