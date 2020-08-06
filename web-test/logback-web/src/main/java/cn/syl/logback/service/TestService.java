package cn.syl.logback.service;

import cn.syl.core.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    private static final Logger logger = LoggerFactory.getLogger(TestService.class);

    public ResponseDto testa(){
        logger.info("这是一个测试数据");
        return ResponseDto.ok();
    }
}
