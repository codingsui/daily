package cn.syl.java.redis.bit;

import redis.clients.jedis.Jedis;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserOperationLog {

    public static void main(String[] args) {
        UserOperationLog u = new UserOperationLog();
        u.addUserOperationLog("操作1",1);
        System.out.println("是否执行过「操作1」:" + u.hasOperationUserLog("操作1",1));
        System.out.println("是否执行过「操作1」:" + u.hasOperationUserLog("操作1",11));
        System.out.println("是否执行过「操作1」:" + u.hasOperationUserLog("操作1",113));
    }

    private Jedis jedis = new Jedis("39.106.39.129",6379);

    public Boolean addUserOperationLog(String operation,long uid){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = simpleDateFormat.format(new Date());
        return jedis.setbit("bit::" + simpleDateFormat + "::operation::" + operation,uid,String.valueOf(1));
    }

    public Boolean hasOperationUserLog(String operation,long uid){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String today = simpleDateFormat.format(new Date());
        return jedis.getbit("bit::" + simpleDateFormat + "::operation::" + operation,uid);
    }

}
