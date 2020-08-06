package cn.syl.logback;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class LogBackStartApplication {

	private static final Logger logger = LoggerFactory.getLogger(LogBackStartApplication.class);


	public static void main(String[] args) {
		SpringApplication.run(LogBackStartApplication.class, args);
	}



}
