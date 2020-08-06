package cn.syl.logback;

import cn.syl.logback.filter.LogFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {

    /**
     * 将自定义拦截器作为Bean写入配置
     * @return
     */
    @Bean
    public LogFilter sysInterceptor() {
        return new LogFilter();
    }
    /**
     * 对拦截器注册
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sysInterceptor());
        super.addInterceptors(registry);
    }
}