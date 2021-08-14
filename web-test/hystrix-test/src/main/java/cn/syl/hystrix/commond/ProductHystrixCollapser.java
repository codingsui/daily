package cn.syl.hystrix.commond;

import cn.syl.hystrix.entity.Product;
import cn.syl.hystrix.service.ProductService;
import com.netflix.hystrix.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ProductHystrixCollapser extends HystrixCollapser<List<Product>, Product, Long> {

    private Long id;

    public ProductHystrixCollapser(Long id) {
        super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("product"))
                .andScope(Scope.GLOBAL)
                .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(5000).withMaxRequestsInBatch(200)));
        this.id = id;
    }
    @Override
    public Long getRequestArgument() {
        return id;
    }

    @Override
    protected HystrixCommand<List<Product>> createCommand(Collection<CollapsedRequest<Product, Long>> requests) {
        StringBuilder paramsBuilder = new StringBuilder("");
        for(CollapsedRequest<Product, Long> request : requests) {
            paramsBuilder.append(request.getArgument()).append(",");
        }
        String params = paramsBuilder.toString();
        params = params.substring(0, params.length() - 1);

        System.out.println("createCommand方法执行，params=" + params);

        return new BatchCommand(requests);
    }

    @Override
    protected void mapResponseToRequests(List<Product> batchResponse, Collection<CollapsedRequest<Product, Long>> collapsedRequests) {
        int count = 0;
        for(CollapsedRequest<Product, Long> request : collapsedRequests) {
            request.setResponse(batchResponse.get(count++));
        }
    }

    private static class BatchCommand extends HystrixCommand<List<Product>>{

        public final Collection<CollapsedRequest<Product, Long>> requests;
        protected BatchCommand(Collection<CollapsedRequest<Product, Long>> requests) {
            super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("batchProduct")));
            this.requests = requests;
        }

        @Override
        protected List<Product> run() throws Exception {
            //远程获取产品
            return requests.stream().mapToLong(item->item.getArgument()).mapToObj(ProductService.cache::get).collect(Collectors.toList());
        }
    }
}
