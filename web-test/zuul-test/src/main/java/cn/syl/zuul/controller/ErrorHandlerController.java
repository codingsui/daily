package cn.syl.zuul.controller;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ErrorHandlerController implements ErrorController {

    @GetMapping("error")
    public Object error(){
        System.out.println("异常拦截类");
        Map<String,Object> map = new HashMap<>();
        map.put("date",new Date());
        map.put("msg","统一异常拦截");
        return map;
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
