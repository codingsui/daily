package cn.syl.hystrix.commond;

import cn.syl.hystrix.entity.Product;
import cn.syl.hystrix.service.ProductService;
import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;

public class ProductCommand extends HystrixCommand<Product> {


    private Long id;

    public ProductCommand(Long id) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("库存服务"))
        .andCommandKey(HystrixCommandKey.Factory.asKey("获取库存"))
        .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("库存线程池"))
        .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                .withCoreSize(10)
                .withQueueSizeRejectionThreshold(5)));
        this.id = id;
    }

    @Override
    protected String getCacheKey() {
        return "product" + id;
    }

    @Override
    protected Product getFallback() {
        Product product = Product.builder().id(-1L).name("降级").build();
        return product;
    }

    @Override
    protected Product run() {
        int a = (int) (1 / id);
        System.out.println("查询mysql" + id);
        Product product = ProductService.cache.get(id);
        return product;
    }

}
