package cn.syl.dt.ct.conf;

import java.sql.SQLException;

import javax.sql.DataSource;

import com.alibaba.druid.pool.xa.DruidXADataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jta.atomikos.AtomikosDataSourceBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * druid数据库连接池配置类
 * @author zhonghuashishan
 *
 */
@Configuration
@MapperScan(basePackages = "cn.syl.dt.ct.mapper.order",
			sqlSessionFactoryRef = "orderSqlSessionFactory")
public class OrderDataSourceConfig {

    @Value("${order.datasource.url}")
    private String dbUrl;
    @Value("${order.datasource.username}")
    private String username;
    @Value("${order.datasource.password}")
    private String password;
    @Value("${order.datasource.driverClassName}")
    private String driverClassName;
    @Value("${order.datasource.initialSize}")
    private int initialSize;
    @Value("${order.datasource.minIdle}")
    private int minIdle;
    @Value("${order.datasource.maxActive}")
    private int maxActive;
    @Value("${order.datasource.maxWait}")
    private int maxWait;
    @Value("${order.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${order.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;
    @Value("${order.datasource.validationQuery}")
    private String validationQuery;
    @Value("${order.datasource.testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${order.datasource.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${order.datasource.testOnReturn}")
    private boolean testOnReturn;
    @Value("${order.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;
    @Value("${order.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;
    @Value("${order.datasource.filters}")
    private String filters;
    @Value("${order.datasource.connectionProperties}")
    private String connectionProperties;

    /**
     * 创建druid数据库连接池bean
     * @return
     */
    @Bean(name = "orderDataSource",initMethod = "init")
    public DataSource orderDataSource(){
        DruidXADataSource datasource = new DruidXADataSource();
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
        AtomikosDataSourceBean atomikosDataSource = new AtomikosDataSourceBean();
        atomikosDataSource.setXaDataSource(datasource);
        return atomikosDataSource;
    }


    @Bean(name = "orderSqlSessionFactory")
    public SqlSessionFactory orderSqlSessionFactory(
    		@Qualifier("orderDataSource") DataSource orderDataSource) throws Exception {
    	final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    	sessionFactory.setDataSource(orderDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/ct/order/*.xml"));
    	return sessionFactory.getObject();
    }

}