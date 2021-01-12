package cn.syl.java.limit;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class RateLimiter {

    private AtomicLong count;

    private AtomicLong lastTime;

    public RateLimiter() {
        count = new AtomicLong(0);
        lastTime = new AtomicLong(0);
    }

    public boolean acquire(int size,int rate){
        if (size <= 0 || rate <= 0){
            log.info("");
            return false;
        }
        refillToken(size,rate);
        return consumer(size);
    }

    private void refillToken(int size, int rate) {
        long now = System.currentTimeMillis();//当前时间
        long lastTim = lastTime.get(); // 最后一次获取时间
        long deltaTime = now - lastTim; //增量时间
        long delatCount = deltaTime * rate / 1000; //一定时间内，增量新token
        if (delatCount > 0){
            System.out.println("增量令牌生成,count:"+delatCount + "，当前已使用"+count.get());
            long newFillTime = lastTim == 0 ? now : lastTim + deltaTime;
            if (lastTime.compareAndSet(lastTim,newFillTime)){
                while (true){
                    long currentCount = count.get();
                    long adjust = Math.min(currentCount,size);
                    long newCount = Math.max(0,adjust-delatCount);
                    if (count.compareAndSet(currentCount,newCount)){
                        return;
                    }
                }
            }
        }else {
            System.out.println("改时间段内无增量token,已使用："+count.get());
        }

    }

    private boolean consumer(int size) {
        while (true){
            long currentCount = count.get();
            if (currentCount >= size){
                return false;
            }else {
                if (count.compareAndSet(currentCount,currentCount + 1)){
                    return true;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        RateLimiter r = new RateLimiter();
        while (true){
            Thread.sleep(90);
            System.out.println(r.acquire(10,10));
        }
    }
}
