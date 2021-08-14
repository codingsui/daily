package cn.syl.ribbon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/ribbon")
@Configuration
public class TestController {


    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @GetMapping("/hello/{msg}")
    public String hello(@PathVariable("msg")String msg) throws InterruptedException {
        System.out.println(msg);
        Thread.sleep(300000);
        return "hello," + msg;
    }

    @GetMapping("/a/{msg}")
    public String ribbon(@PathVariable("msg")String msg){

        return restTemplate.getForEntity("http://serviceA/get/" + msg,String.class).getBody();
    }

    @GetMapping("/b/{msg}")
    public String ribbonB(@PathVariable("msg")String msg){
        return restTemplate.getForEntity("http://ribbonB/ribbon/hello/" + msg,String.class).getBody();
    }

}
