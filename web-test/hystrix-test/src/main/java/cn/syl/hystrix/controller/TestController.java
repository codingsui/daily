package cn.syl.hystrix.controller;

import cn.syl.hystrix.entity.Product;
import cn.syl.hystrix.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product/get")
    public String getOne(@RequestParam("id") Long id){
        return productService.getOne(id).toString();
    }

    @GetMapping("/product/cache/get")
    public String cacheGet(@RequestParam("id") Long id){
        return productService.cacheOne(id).toString();
    }

    @GetMapping("/product/getCollapser")
    public String getCollapser(@RequestParam("id") Long id){
        return productService.getCollapser(id).toString();
    }
}
