package cn.syl.logback.controller;

import cn.syl.core.ResponseDto;
import cn.syl.logback.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test", produces = "application/json;charset=UTF-8")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/a")
    public ResponseDto test(){
        return testService.testa();
    }
}
