package cn.syl.rabbitmq.service;

import cn.syl.core.ResponseDto;
import cn.syl.core.utils.CoreUtils;
import cn.syl.rabbitmq.mq.product.MqSendUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    private MqSendUtils sendUtils;

    public ResponseDto sendTest(){
        JSONObject obj = new JSONObject();
        obj.put("uid",CoreUtils.getUUId());
        sendUtils.testQueue(obj);
        return ResponseDto.ok();
    }
}
