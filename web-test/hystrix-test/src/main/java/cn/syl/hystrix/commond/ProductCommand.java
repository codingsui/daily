package cn.syl.hystrix.commond;

import cn.syl.hystrix.entity.Product;
import cn.syl.hystrix.service.ProductService;
import com.netflix.hystrix.*;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductCommond extends HystrixCommand<Product> {

    @Autowired
    private ProductService productService;

    private Long id;

    protected ProductCommond(Long id) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("库存服务"))
        .andCommandKey(HystrixCommandKey.Factory.asKey("获取库存"))
        .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("库存线程池"))
        .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                .withCoreSize(10)
                .withQueueSizeRejectionThreshold(5)));
        this.id = id;
    }

    @Override
    protected Product getFallback() {
        Product product = Product.builder().id(-1L).build();
        return product;
    }

    @Override
    protected Product run() {
        Product product = productService.getOne(id);
        return product;
    }
}
