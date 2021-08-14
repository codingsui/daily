package cn.syl.dt.ct.conf;


import com.atomikos.icatch.jta.UserTransactionImp;
import com.atomikos.icatch.jta.UserTransactionManager;
import org.apache.ibatis.reflection.MetaObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.transaction.SystemException;
import javax.transaction.UserTransaction;


@Configuration
@MapperScan(basePackages = {"cn.syl.dt.ct.mapper.*.*"})
@EnableTransactionManagement
public class MybatisConfig {


    @Bean(name = "xatx")
    public JtaTransactionManager jtaTransactionManager(){
        UserTransactionManager userTransactionManager = new UserTransactionManager();
        UserTransaction userTransaction = new UserTransactionImp();
        userTransactionManager.setForceShutdown(true);
        return new JtaTransactionManager(userTransaction,userTransactionManager);
    }

}
