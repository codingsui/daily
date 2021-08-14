package cn.syl.feignclient.conf;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConf {
    @Bean
    public Logger.Level level(){
        return Logger.Level.HEADERS;
    }
}
