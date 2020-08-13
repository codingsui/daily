package cn.syl.rabbitmq.mq.consumer;

import cn.syl.rabbitmq.Contast;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.impl.AMQImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TopicConsumer {

    @RabbitListener(queues = Contast.TOPIC_QUEUE_A)
    public void comsumetA(String obj){
        log.info("队列：{}，接收到消息：{}",Contast.TOPIC_QUEUE_A,obj);
    }
    @RabbitListener(queues = Contast.TOPIC_QUEUE_B)
    public void comsumetB(String obj){
        log.info("队列：{}，接收到消息：{}",Contast.TOPIC_QUEUE_B,obj);
    }
    @RabbitListener(queues = Contast.TOPIC_QUEUE_C)
    public void comsumetC(String obj){
        log.info("队列：{}，接收到消息：{}",Contast.TOPIC_QUEUE_C,obj);
    }
}
