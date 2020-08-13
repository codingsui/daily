package cn.syl.rabbitmq.service;

import cn.syl.core.ResponseDto;
import cn.syl.core.utils.CoreUtils;
import cn.syl.rabbitmq.mq.product.MqSendUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

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


    public ResponseDto sendFanout(){
        JSONObject obj = new JSONObject();
        obj.put("uid",CoreUtils.getUUId());
        sendUtils.fanoutQueue(obj);
        return ResponseDto.ok();
    }

    public ResponseDto sendTopic(){
        sendUtils.topicQueue();
        return ResponseDto.ok();
    }

    public ResponseDto confirmQueue(String msg){
        sendUtils.confirmQueue(msg);
        return ResponseDto.ok();
    }
}
