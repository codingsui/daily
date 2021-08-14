package com.zhss.data.refill.center.db;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * druid数据库连接池配置类
 * @author zhonghuashishan
 *
 */
@Configuration  
@MapperScan(basePackages = "com.zhss.data.refill.center.mapper.datapackage", 
			sqlSessionFactoryRef = "packageSqlSessionFactory")
public class PackageDataSourceConfig {  
   
    @Value("${package.datasource.url}")  
    private String dbUrl;  
    @Value("${package.datasource.username}")  
    private String username;  
    @Value("${package.datasource.password}")  
    private String password;  
    @Value("${package.datasource.driverClassName}")  
    private String driverClassName;  
    @Value("${package.datasource.initialSize}")  
    private int initialSize;  
    @Value("${package.datasource.minIdle}")  
    private int minIdle;  
    @Value("${package.datasource.maxActive}")  
    private int maxActive;  
    @Value("${package.datasource.maxWait}")  
    private int maxWait;  
    @Value("${package.datasource.timeBetweenEvictionRunsMillis}")  
    private int timeBetweenEvictionRunsMillis;  
    @Value("${package.datasource.minEvictableIdleTimeMillis}")  
    private int minEvictableIdleTimeMillis;  
    @Value("${package.datasource.validationQuery}")  
    private String validationQuery;  
    @Value("${package.datasource.testWhileIdle}")  
    private boolean testWhileIdle;  
    @Value("${package.datasource.testOnBorrow}")  
    private boolean testOnBorrow;  
    @Value("${package.datasource.testOnReturn}")  
    private boolean testOnReturn;  
    @Value("${package.datasource.poolPreparedStatements}")  
    private boolean poolPreparedStatements;  
    @Value("${package.datasource.maxPoolPreparedStatementPerConnectionSize}")  
    private int maxPoolPreparedStatementPerConnectionSize;  
    @Value("${package.datasource.filters}")  
    private String filters;  
    @Value("${package.datasource.connectionProperties}")  
    private String connectionProperties;  
    
    /**
     * 创建druid数据库连接池bean
     * @return
     */
    @Bean(name = "packageDataSource")     
    public DataSource packageDataSource(){  
        DruidDataSource datasource = new DruidDataSource();  
        datasource.setUrl(this.dbUrl);  
        datasource.setUsername(username);  
        datasource.setPassword(password);  
        datasource.setDriverClassName(driverClassName);  
        datasource.setInitialSize(initialSize);  
        datasource.setMinIdle(minIdle);  
        datasource.setMaxActive(maxActive);  
        datasource.setMaxWait(maxWait);          
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);  
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);  
        datasource.setValidationQuery(validationQuery);  
        datasource.setTestWhileIdle(testWhileIdle);  
        datasource.setTestOnBorrow(testOnBorrow);  
        datasource.setTestOnReturn(testOnReturn);  
        datasource.setPoolPreparedStatements(poolPreparedStatements);  
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);  
        
        try {  
            datasource.setFilters(filters);  
        } catch (SQLException e) {  
            e.printStackTrace();
        }  
        
        datasource.setConnectionProperties(connectionProperties);  
          
        return datasource;  
    }
    
    @Bean(name = "packageTransactionManager")
    public DataSourceTransactionManager packageTransactionManager() {
    	return new DataSourceTransactionManager(packageDataSource());
    }

    @Bean(name = "packageSqlSessionFactory")
    public SqlSessionFactory packageSqlSessionFactory(
    		@Qualifier("packageDataSource") DataSource packageDataSource) throws Exception {
    	final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    	sessionFactory.setDataSource(packageDataSource);
    	return sessionFactory.getObject();
    }   
    
}