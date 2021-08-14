package cn.syl.test.controller;

import cn.syl.test.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {

    @Autowired
    private FeignService feignService;

    @GetMapping("/get/{msg}")
    public String get(@PathVariable("msg") String msg){
        return feignService.get(msg);
    }
}
