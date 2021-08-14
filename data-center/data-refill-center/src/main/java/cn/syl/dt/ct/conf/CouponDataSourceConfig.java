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
//@MapperScan(basePackages = "cn.syl.dt.ct.mapper.coupon",
//			sqlSessionFactoryRef = "couponSqlSessionFactory")
//public class CouponDataSourceConfig {
//
//    @Value("${coupon.datasource.url}")
//    private String dbUrl;
//    @Value("${coupon.datasource.username}")
//    private String username;
//    @Value("${coupon.datasource.password}")
//    private String password;
//    @Value("${coupon.datasource.driverClassName}")
//    private String driverClassName;
//    @Value("${coupon.datasource.initialSize}")
//    private int initialSize;
//    @Value("${coupon.datasource.minIdle}")
//    private int minIdle;
//    @Value("${coupon.datasource.maxActive}")
//    private int maxActive;
//    @Value("${coupon.datasource.maxWait}")
//    private int maxWait;
//    @Value("${coupon.datasource.timeBetweenEvictionRunsMillis}")
//    private int timeBetweenEvictionRunsMillis;
//    @Value("${coupon.datasource.minEvictableIdleTimeMillis}")
//    private int minEvictableIdleTimeMillis;
//    @Value("${coupon.datasource.validationQuery}")
//    private String validationQuery;
//    @Value("${coupon.datasource.testWhileIdle}")
//    private boolean testWhileIdle;
//    @Value("${coupon.datasource.testOnBorrow}")
//    private boolean testOnBorrow;
//    @Value("${coupon.datasource.testOnReturn}")
//    private boolean testOnReturn;
//    @Value("${coupon.datasource.poolPreparedStatements}")
//    private boolean poolPreparedStatements;
//    @Value("${coupon.datasource.maxPoolPreparedStatementPerConnectionSize}")
//    private int maxPoolPreparedStatementPerConnectionSize;
//    @Value("${coupon.datasource.filters}")
//    private String filters;
//    @Value("${coupon.datasource.connectionProperties}")
//    private String connectionProperties;
//
//    /**
//     * 创建druid数据库连接池bean
//     * @return
//     */
//    @Bean(name = "couponDataSource")
//    public DataSource couponDataSource(){
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
//    @Bean(name = "couponTransactionManager")
//    public DataSourceTransactionManager couponTransactionManager() {
//    	return new DataSourceTransactionManager(couponDataSource());
//    }
//
//    @Bean(name = "couponSqlSessionFactory")
//    public SqlSessionFactory couponSqlSessionFactory(
//    		@Qualifier("couponDataSource") DataSource couponDataSource) throws Exception {
//    	final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
//    	sessionFactory.setDataSource(couponDataSource);
//        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
//                .getResources("classpath:mapper/ct/coupon/*.xml"));
//    	return sessionFactory.getObject();
//    }
//
//}