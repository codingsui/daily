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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * druid数据库连接池配置类
 * @author zhonghuashishan
 *
 */
@Configuration
@MapperScan(basePackages = "cn.syl.dt.ct.mapper.activity",
			sqlSessionFactoryRef = "activitySqlSessionFactory")
public class ActivityDataSourceConfig {

    @Value("${activity.datasource.url}")
    private String dbUrl;
    @Value("${activity.datasource.username}")
    private String username;
    @Value("${activity.datasource.password}")
    private String password;
    @Value("${activity.datasource.driverClassName}")
    private String driverClassName;
    @Value("${activity.datasource.initialSize}")
    private int initialSize;
    @Value("${activity.datasource.minIdle}")
    private int minIdle;
    @Value("${activity.datasource.maxActive}")
    private int maxActive;
    @Value("${activity.datasource.maxWait}")
    private int maxWait;
    @Value("${activity.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${activity.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;
    @Value("${activity.datasource.validationQuery}")
    private String validationQuery;
    @Value("${activity.datasource.testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${activity.datasource.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${activity.datasource.testOnReturn}")
    private boolean testOnReturn;
    @Value("${activity.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;
    @Value("${activity.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;
    @Value("${activity.datasource.filters}")
    private String filters;
    @Value("${activity.datasource.connectionProperties}")
    private String connectionProperties;

    /**
     * 创建druid数据库连接池bean
     * @return
     */
    @Bean(name = "activityDataSource")
    @Primary
    public DataSource activityDataSource(){
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


    @Bean(name = "activitySqlSessionFactory")
    @Primary
    public SqlSessionFactory activitySqlSessionFactory(
    		@Qualifier("activityDataSource") DataSource activityDataSource) throws Exception {
    	final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    	sessionFactory.setDataSource(activityDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources("classpath:mapper/ct/activity/*.xml"));
    	return sessionFactory.getObject();
    }

}