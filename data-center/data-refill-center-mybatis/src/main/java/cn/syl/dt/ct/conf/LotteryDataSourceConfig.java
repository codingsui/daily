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
@MapperScan(basePackages = "cn.syl.dt.ct.mapper.lottery",
			sqlSessionFactoryRef = "lotterySqlSessionFactory")
public class LotteryDataSourceConfig {

    @Value("${lottery.datasource.url}")
    private String dbUrl;
    @Value("${lottery.datasource.username}")
    private String username;
    @Value("${lottery.datasource.password}")
    private String password;
    @Value("${lottery.datasource.driverClassName}")
    private String driverClassName;
    @Value("${lottery.datasource.initialSize}")
    private int initialSize;
    @Value("${lottery.datasource.minIdle}")
    private int minIdle;
    @Value("${lottery.datasource.maxActive}")
    private int maxActive;
    @Value("${lottery.datasource.maxWait}")
    private int maxWait;
    @Value("${lottery.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${lottery.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;
    @Value("${lottery.datasource.validationQuery}")
    private String validationQuery;
    @Value("${lottery.datasource.testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${lottery.datasource.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${lottery.datasource.testOnReturn}")
    private boolean testOnReturn;
    @Value("${lottery.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;
    @Value("${lottery.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;
    @Value("${lottery.datasource.filters}")
    private String filters;
    @Value("${lottery.datasource.connectionProperties}")
    private String connectionProperties;

    /**
     * 创建druid数据库连接池bean
     * @return
     */
    @Bean(name = "lotteryDataSource")
    public DataSource lotteryDataSource(){
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


    @Bean(name = "lotterySqlSessionFactory")
    public SqlSessionFactory lotterySqlSessionFactory(
    		@Qualifier("lotteryDataSource") DataSource lotteryDataSource) throws Exception {
    	final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    	sessionFactory.setDataSource(lotteryDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/ct/lottery/*.xml"));
    	return sessionFactory.getObject();
    }

}