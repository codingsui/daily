package cn.syl.feignclient.controller;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/feign", produces = "application/json;charset=UTF-8")
public class TestController {

    @Autowired
    private TestService testService;


    @GetMapping("/test")
    public JSONObject get(@RequestParam("type")Integer type){
        return testService.get(type);
    }

}
