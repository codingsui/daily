package cn.syl.hystrix.service;

import cn.syl.hystrix.commond.ProductCommand;
import cn.syl.hystrix.entity.Product;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {

    public static Map<Long,Product> cache;

    public Product getOne(Long id) {
        ProductCommand commond = new ProductCommand(id);
        return commond.execute();
    }

    @CacheResult(cacheKeyMethod = "getCacheKey")
    @HystrixCommand(fallbackMethod = "fallBack")
    public Product cacheOne(Long id) {
        System.out.println("查询mysql");
        long a = 1 / id;

        Product product = ProductService.cache.get(id);
        return product;
    }

    public Product fallBack(Long id){
        return Product.builder().name("降级").id(id).build();
    }

    public String getCacheKey(Long id){
        return "product"+id;
    }


    @HystrixCollapser(batchMethod = "mgetProduct",scope = com.netflix.hystrix.HystrixCollapser.Scope.GLOBAL,collapserProperties = {
            //为了模拟请求合并，将合并等待时间设置5s
            @HystrixProperty(name = "timerDelayInMilliseconds",value = "5000"),
            @HystrixProperty(name = "maxRequestsInBatch",value = "200"),
    })
    public Product getCollapser(Long id){
        return null;
    }

    @HystrixCommand
    public List<Product> mgetProduct(List<Long> ids){
        System.out.println("合并请求:"+ids.toString());
        return ids.stream().map(ProductService.cache::get).collect(Collectors.toList());
    }

    @PostConstruct
    private void afterPropertiesSet() {
        cache = new HashMap<>();
        cache.put(1L,Product.builder().id(1L).name("华为").price(2000.0).count(100).build());
        cache.put(2L,Product.builder().id(2L).name("苹果").price(3000.0).count(200).build());
        cache.put(3L,Product.builder().id(3L).name("小米").price(4000.0).count(300).build());
        cache.put(4L,Product.builder().id(4L).name("vivo").price(3000.0).count(400).build());

    }
}
