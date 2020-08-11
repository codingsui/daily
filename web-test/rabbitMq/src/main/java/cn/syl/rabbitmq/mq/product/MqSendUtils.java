package cn.syl.rabbitmq.mq.product;

import cn.syl.rabbitmq.Contast;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


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


    public void fanoutQueue(JSONObject obj){
        if (obj == null){
            return;
        }
        log.info("fanoutQueue--发送消息：{}，至交换机：{}",obj.toString(),Contast.FANOUT_EXCHANGE);
        rabbitTemplate.convertAndSend(Contast.FANOUT_EXCHANGE,"",obj);
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void topicQueue(){
        log.info("topicQueue--发送消息，至交换机：{}",Contast.TOPIC_EXCHANGE);
        rabbitTemplate.convertAndSend(Contast.TOPIC_EXCHANGE,"a.b.c","a.b.c");
        rabbitTemplate.convertAndSend(Contast.TOPIC_EXCHANGE,"a.b","a.b");
        rabbitTemplate.convertAndSend(Contast.TOPIC_EXCHANGE,"a.","a.");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
