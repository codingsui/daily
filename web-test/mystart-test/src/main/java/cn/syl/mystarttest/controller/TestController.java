package cn.syl.mystarttest.controller;

import cn.syl.mystart.service.MystartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private MystartService mystartService;

    @GetMapping("/test/{param}")
    public String test(@PathVariable("param")String param){
        return mystartService.mystart(param);
    }
}
