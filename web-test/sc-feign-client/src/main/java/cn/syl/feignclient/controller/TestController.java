package cn.syl.feignclient.controller;

import cn.syl.feignclient.feign.TestFeign;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/f", produces = "application/json;charset=UTF-8")
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private TestFeign testFeign;

    @GetMapping("/test")
    public JSONObject get(@RequestParam("type")Integer type){
        return testService.get(type);
    }



    @GetMapping("/f/{msg}")
    public String getFeign(@PathVariable("msg") String msg){
        return testFeign.ribbonA(msg).toString();
    }

}
