package cn.syl.java.redis.set;

import redis.clients.jedis.Jedis;

import java.util.Set;

public class BlogDemo implements Blog {


    public static void main(String[] args) {
        long myId = 1;
        long friendId = 2;
        long p1 = 3;
        long p2 = 4;
        long p3 = 5;
        Blog b = new BlogDemo();
        b.follow(myId,friendId);
        b.follow(myId,p1);
        b.follow(myId,p2);
        b.follow(myId,p3);
        b.follow(friendId,p1);
        b.follow(friendId,p2);
        long myFollowCount = b.followCount(myId);
        System.out.println("我的关注量：" + myFollowCount);
        System.out.println("我的粉丝:" + b.getFollowUsers(myId));
        System.out.println("朋友的粉丝：" + b.getFollowUsers(friendId));
        System.out.println("我和朋友相同的粉丝:" + b.getSameFollowUsers(myId,friendId));
        System.out.println("系统匹配可以推荐给朋友的粉丝：" + b.recommondUser(myId,friendId));
    }



    private Jedis jedis = new Jedis("39.106.39.129",6379);

    @Override
    public Set<String> getFollowUsers(long userId) {
        return jedis.smembers(String.format(BlogEnum.FOLLOW.getCode(),userId));
    }

    @Override
    public Set<String> getSameFollowUsers(long userId, long friendId) {
        return jedis.sinter(String.format(BlogEnum.FOLLOW.getCode(),userId),String.format(BlogEnum.FOLLOW.getCode(),friendId));
    }

    @Override
    public long follow(long userId, long friendId) {
        return jedis.sadd(String.format(BlogEnum.FOLLOW.getCode(),userId), String.valueOf(friendId));
    }

    @Override
    public long followCount(long userId) {
        return jedis.scard(String.format(BlogEnum.FOLLOW.getCode(),userId));
    }

    @Override
    public Set<String> recommondUser(long userId, long friendId) {
        Set<String> set = jedis.sdiff(String.format(BlogEnum.FOLLOW.getCode(), userId),String.format(BlogEnum.FOLLOW.getCode(), friendId));
        if (set == null || set.size() == 0){
            return null;
        }
        set.remove(String.valueOf(friendId));
        return set;
    }

}
enum BlogEnum{

    FOLLOW("followUsers:%s"),
    RECOMMEND("recommend:%s:%s"),
    ;

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    BlogEnum(String code) {
        this.code = code;
    }
}
interface Blog{
    Set<String> getFollowUsers(long userId);

    Set<String> getSameFollowUsers(long userId,long friendId);

    long follow(long userId,long friendId);

    long followCount(long userId);

    Set<String> recommondUser(long userId,long friendId);
}