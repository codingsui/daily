package cn.syl.dt.ct.conf;

import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 * MybatisConfiguration
 *
 * @author hewei
 * @version [1.0.0, 2020/5/14]
 */
@Configuration
@MapperScan(basePackages = {"cn.syl.dt.ct.mapper.*","cn.syl.dt.ct.mapper.*.*"})
@EnableTransactionManagement
public class MybatisConfig {

    @Bean(name = "xatx")
    public JtaTransactionManager jtaTransactionManager(){
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        UserTransaction userTransaction = new UserTransactionImp();
        return new JtaTransactionManager(userTransaction,userTransactionManager);
    }
    @Bean
    public UserTransactionImp userTransactionImp() throws SystemException {
        UserTransactionImp userTransactionImp = new UserTransactionImp();
        userTransactionImp.setTransactionTimeout(5000);
        return userTransactionImp;
    }

    @Bean
    public UserTransactionManager userTransactionManager() {
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        userTransactionManager.setForceShutdown(true);
        return userTransactionManager;
    }



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
