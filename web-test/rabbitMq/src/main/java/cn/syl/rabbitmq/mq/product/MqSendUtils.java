package cn.syl.rabbitmq.mq.product;

import cn.syl.rabbitmq.Contast;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class MqSendUtils {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void testQueue(JSONObject obj){
        if (obj == null){
            return;
        }
        log.info("发送消息：{}，至队列：{}",obj.toString(),Contast.TEST_QUEUE);
        rabbitTemplate.convertAndSend(Contast.TEST_QUEUE,obj);
    }


}
