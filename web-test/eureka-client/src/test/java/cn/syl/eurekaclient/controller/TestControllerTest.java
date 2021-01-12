package cn.syl.eurekaclient.controller;

import com.netflix.discovery.util.RateLimiter;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class TestControllerTest {

    @Test
    void discoveryClient() {
        RateLimiter rateLimiter = new RateLimiter(TimeUnit.MINUTES);
        while (true){
            boolean a = rateLimiter.acquire(2,4);
            System.out.println(a);
        }

    }
}