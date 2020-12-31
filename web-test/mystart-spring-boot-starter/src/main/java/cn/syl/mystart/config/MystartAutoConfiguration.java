package cn.syl.mystart.config;

import cn.syl.mystart.service.MystartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(MystartService.class)
@EnableConfigurationProperties(MystartProperties.class)
public class MystartAutoConfiguration {

    @Autowired
    private MystartProperties mystartProperties;

    @Bean
    @ConditionalOnProperty(name = "mystart.enable",havingValue = "true",matchIfMissing = true)
    public MystartService mystartService(){
        return new MystartService(mystartProperties.getPre(),mystartProperties.getSub());
    }

    @Bean
    @ConditionalOnMissingBean
    public MystartService mystartService1(){
        return new MystartService("","");
    }
}
