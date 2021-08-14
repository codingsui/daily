package com.rong360.mofun.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MybatisConfiguration
 *
 * @author hewei
 * @version [1.0.0, 2020/5/14]
 */
@Configuration
@MapperScan("com.rong360.mofun.module.*.dao")
@EnableTransactionManagement
public class MybatisConfig {

    @Bean
    @ConditionalOnMissingBean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor interceptor = new PaginationInterceptor();
        interceptor.setLimit(-1);
        interceptor.setOverflow(false);
        return interceptor;
    }

    @Bean
    @ConditionalOnMissingBean
    public MetaObjectHandler metaObjectHandler() {
        return new MetaObjectHandler() {

            @Override
            public void insertFill(MetaObject metaObject) {
            }

            @Override
            public void updateFill(MetaObject metaObject) {
            }
        };
    }
}
