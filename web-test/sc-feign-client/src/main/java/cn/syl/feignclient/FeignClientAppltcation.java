package cn.syl.feignclient;

import feign.Retryer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
@Configuration
public class FeignClientAppltcation {
//    开启的话就会 3 * 2 * 5 = 30次的访问重试  http://localhost:8082/feign/f/hellp
//    @Bean
//    public Retryer feignRetryer() {
//        return new Retryer.Default();
//    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    public static void main(String[] args) {
        SpringApplication.run(FeignClientAppltcation.class, args);
    }
}
