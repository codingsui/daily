//package cn.syl.dt.ct.conf;
//
//import java.sql.SQLException;
//
//import javax.sql.DataSource;
//
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import com.alibaba.druid.pool.DruidDataSource;
//
///**
// * druid数据库连接池配置类
// * @author zhonghuashishan
// *
// */
//@Configuration
//@MapperScan(basePackages = "cn.syl.dt.ct.mapper.finance",
//			sqlSessionFactoryRef = "financeSqlSessionFactory")
//public class FinanceDataSourceConfig {
//
//    @Value("${finance.datasource.url}")
//    private String dbUrl;
//    @Value("${finance.datasource.username}")
//    private String username;
//    @Value("${finance.datasource.password}")
//    private String password;
//    @Value("${finance.datasource.driverClassName}")
//    private String driverClassName;
//    @Value("${finance.datasource.initialSize}")
//    private int initialSize;
//    @Value("${finance.datasource.minIdle}")
//    private int minIdle;
//    @Value("${finance.datasource.maxActive}")
//    private int maxActive;
//    @Value("${finance.datasource.maxWait}")
//    private int maxWait;
//    @Value("${finance.datasource.timeBetweenEvictionRunsMillis}")
//    private int timeBetweenEvictionRunsMillis;
//    @Value("${finance.datasource.minEvictableIdleTimeMillis}")
//    private int minEvictableIdleTimeMillis;
//    @Value("${finance.datasource.validationQuery}")
//    private String validationQuery;
//    @Value("${finance.datasource.testWhileIdle}")
//    private boolean testWhileIdle;
//    @Value("${finance.datasource.testOnBorrow}")
//    private boolean testOnBorrow;
//    @Value("${finance.datasource.testOnReturn}")
//    private boolean testOnReturn;
//    @Value("${finance.datasource.poolPreparedStatements}")
//    private boolean poolPreparedStatements;
//    @Value("${finance.datasource.maxPoolPreparedStatementPerConnectionSize}")
//    private int maxPoolPreparedStatementPerConnectionSize;
//    @Value("${finance.datasource.filters}")
//    private String filters;
//    @Value("${finance.datasource.connectionProperties}")
//    private String connectionProperties;
//
//    /**
//     * 创建druid数据库连接池bean
//     * @return
//     */
//    @Bean(name = "financeDataSource")
//    public DataSource financeDataSource(){
//        DruidDataSource datasource = new DruidDataSource();
//        datasource.setUrl(this.dbUrl);
//        datasource.setUsername(username);
//        datasource.setPassword(password);
//        datasource.setDriverClassName(driverClassName);
//        datasource.setInitialSize(initialSize);
//        datasource.setMinIdle(minIdle);
//        datasource.setMaxActive(maxActive);
//        datasource.setMaxWait(maxWait);
//        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
//        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
//        datasource.setValidationQuery(validationQuery);
//        datasource.setTestWhileIdle(testWhileIdle);
//        datasource.setTestOnBorrow(testOnBorrow);
//        datasource.setTestOnReturn(testOnReturn);
//        datasource.setPoolPreparedStatements(poolPreparedStatements);
//        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
//
//        try {
//            datasource.setFilters(filters);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        datasource.setConnectionProperties(connectionProperties);
//
//        return datasource;
//    }
//
//    @Bean(name = "financeTransactionManager")
//    public DataSourceTransactionManager financeTransactionManager() {
//    	return new DataSourceTransactionManager(financeDataSource());
//    }
//
//    @Bean(name = "financeSqlSessionFactory")
//    public SqlSessionFactory financeSqlSessionFactory(
//    		@Qualifier("financeDataSource") DataSource financeDataSource) throws Exception {
//    	final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
//    	sessionFactory.setDataSource(financeDataSource);
//        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
//                .getResources("classpath:mapper/ct/finance/*.xml"));
//    	return sessionFactory.getObject();
//    }
//
//}