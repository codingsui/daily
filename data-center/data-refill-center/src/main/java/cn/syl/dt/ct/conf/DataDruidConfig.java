//package cn.syl.dt.ct.conf;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.sql.SQLException;
//
//@Configuration
//public class DataDruidConfig {
//    @Value("${wwwwwaa.datasource.url}")
//    private String dbUrl;
//    @Value("${wwwwwaa.datasource.username}")
//    private String username;
//    @Value("${wwwwwaa.datasource.password}")
//    private String password;
//    @Value("${wwwwwaa.datasource.driverClassName}")
//    private String driverClassName;
//    @Value("${wwwwwaa.datasource.initialSize}")
//    private int initialSize;
//    @Value("${wwwwwaa.datasource.minIdle}")
//    private int minIdle;
//    @Value("${wwwwwaa.datasource.maxActive}")
//    private int maxActive;
//    @Value("${wwwwwaa.datasource.maxWait}")
//    private int maxWait;
//    @Value("${wwwwwaa.datasource.timeBetweenEvictionRunsMillis}")
//    private int timeBetweenEvictionRunsMillis;
//    @Value("${wwwwwaa.datasource.minEvictableIdleTimeMillis}")
//    private int minEvictableIdleTimeMillis;
//    @Value("${wwwwwaa.datasource.validationQuery}")
//    private String validationQuery;
//    @Value("${wwwwwaa.datasource.testWhileIdle}")
//    private boolean testWhileIdle;
//    @Value("${wwwwwaa.datasource.testOnBorrow}")
//    private boolean testOnBorrow;
//    @Value("${wwwwwaa.datasource.testOnReturn}")
//    private boolean testOnReturn;
//    @Value("${wwwwwaa.datasource.poolPreparedStatements}")
//    private boolean poolPreparedStatements;
//    @Value("${wwwwwaa.datasource.maxPoolPreparedStatementPerConnectionSize}")
//    private int maxPoolPreparedStatementPerConnectionSize;
//    @Value("${wwwwwaa.datasource.filters}")
//    private String filters;
//    @Value("{wwwwwaa.datasource.connectionProperties}")
//    private String connectionProperties;
//    @Bean
//    public DruidDataSource dataSource(){
//        DruidDataSource datasource = new DruidDataSource();
//        datasource.setUrl(dbUrl);
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
//        try {
//            datasource.setFilters(filters);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        datasource.setConnectionProperties(connectionProperties);
//
//        return datasource;
//    }
//}
