package cn.syl.zuul.conf;

import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


//@Configuration
public class ZuulException{

//    @Bean
    public MyException myException(ErrorAttributes errorAttributes, ErrorProperties errorProperties){
        return new MyException(errorAttributes,errorProperties);
    }

    class MyException extends BasicErrorController{
        public MyException(ErrorAttributes errorAttributes, ErrorProperties errorProperties) {
            super(errorAttributes, errorProperties);
        }
    }
}
