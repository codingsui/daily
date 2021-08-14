package cn.syl.feignclient.controller;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

@Service
public class TestService {


    @HystrixCommand(fallbackMethod = "fallbackMethod",commandKey = "test",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "50"),},
            threadPoolProperties ={

            }
            )
    public JSONObject get(int type){
        JSONObject o = new JSONObject();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("中断了～～");
        }
        System.out.println("真正代码");
        o.put("success","success");
        return o;
    }

    public JSONObject fallbackMethod(int type){
        JSONObject o = new JSONObject();
        o.put("fail","fail");
        System.out.println("fallback");
        return o;
    }
}
