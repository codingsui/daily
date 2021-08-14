package cn.syl.test.service;

import feign.Logger;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "serviceA" ,configuration = FeignConfig.class,fallbackFactory = FeignFallbackFactory.class)
public interface FeignService {

    @GetMapping("/get/{msg}")
    String get(@PathVariable("msg")String msg);

}
@Component
class FeignConfig{

    @Bean
    Logger.Level level(){
        return Logger.Level.FULL;
    }
}
@Component
@Slf4j
class FeignFallbackFactory implements FallbackFactory<FeignService> {
    static FeignService feignService = new FeignService() {
            @Override
            public String get(String msg) {
                log.warn("降级 get方法");
                return "fallback";
            }
        };
    @Override
    public FeignService create(Throwable cause) {
        return feignService;
    }
}
