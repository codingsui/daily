package cn.syl.ribbon.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ribbon")
@Configuration
public class TestController {

    @GetMapping("/hello/{msg}")
    public String hello(@PathVariable("msg")String msg){
        System.out.println(msg);
        return "hello," + msg;
    }

}
