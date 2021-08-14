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
@MapperScan(basePackages = "cn.syl.dt.ct.mapper.credit",
			sqlSessionFactoryRef = "creditSqlSessionFactory")
public class CreditDataSourceConfig {

    @Value("${credit.datasource.url}")
    private String dbUrl;
    @Value("${credit.datasource.username}")
    private String username;
    @Value("${credit.datasource.password}")
    private String password;
    @Value("${credit.datasource.driverClassName}")
    private String driverClassName;
    @Value("${credit.datasource.initialSize}")
    private int initialSize;
    @Value("${credit.datasource.minIdle}")
    private int minIdle;
    @Value("${credit.datasource.maxActive}")
    private int maxActive;
    @Value("${credit.datasource.maxWait}")
    private int maxWait;
    @Value("${credit.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${credit.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;
    @Value("${credit.datasource.validationQuery}")
    private String validationQuery;
    @Value("${credit.datasource.testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${credit.datasource.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${credit.datasource.testOnReturn}")
    private boolean testOnReturn;
    @Value("${credit.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;
    @Value("${credit.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;
    @Value("${credit.datasource.filters}")
    private String filters;
    @Value("${credit.datasource.connectionProperties}")
    private String connectionProperties;

    /**
     * 创建druid数据库连接池bean
     * @return
     */
    @Bean(name = "creditDataSource")
    public DataSource creditDataSource(){
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

    @Bean(name = "creditSqlSessionFactory")
    public SqlSessionFactory creditSqlSessionFactory(
    		@Qualifier("creditDataSource") DataSource creditDataSource) throws Exception {
    	final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    	sessionFactory.setDataSource(creditDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/ct/credit/*.xml"));
    	return sessionFactory.getObject();
    }

}