package cn.syl;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.WeightedResponseTimeRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RibbonAConf {
    @Bean
    public IRule iRule(){
        return new WeightedResponseTimeRule();
    }
}
