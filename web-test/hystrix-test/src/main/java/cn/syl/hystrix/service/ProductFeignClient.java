package cn.syl.hystrix.service;

import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "productA",fallback = ProductFeignClient.ProductFallBack.class)
public interface ProductFeignClient {

    static class ProductFallBack {

    }
}
