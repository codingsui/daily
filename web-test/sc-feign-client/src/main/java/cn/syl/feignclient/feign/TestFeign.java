package cn.syl.feignclient.feign;

import cn.syl.feignclient.conf.FeignConf;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "serviceA",configuration = FeignConf.class)
public interface TestFeign {

    @GetMapping("/get/{msg}")
    String ribbonA(@PathVariable("msg")String msg);
}
