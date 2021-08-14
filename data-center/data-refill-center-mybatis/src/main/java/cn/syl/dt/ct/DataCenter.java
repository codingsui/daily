package cn.syl.dt.ct;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
public class DataCenter {

    public static void main(String[] args) {
        SpringApplication.run(DataCenter.class,args);
    }
}
