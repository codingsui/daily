package cn.syl.rabbitmq.mq.consumer;

import cn.syl.rabbitmq.Contast;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MoreQueueConsumer {

    @RabbitListener(queues = Contast.MORE_QUEUE)
    public void comsumet1(String obj){
        log.info("消费者:{},队列：{}，接收到消息：{}",1,Contast.MORE_QUEUE,obj);
    }

    @RabbitListener(queues = Contast.MORE_QUEUE)
    public void comsumet2(String obj){
        log.info("消费者:{},队列：{}，接收到消息：{}",2,Contast.MORE_QUEUE,obj);
    }

    @RabbitListener(queues = Contast.MORE_QUEUE)
    public void comsumet3(String obj){
        log.info("消费者:{},队列：{}，接收到消息：{}",3,Contast.MORE_QUEUE,obj);
    }
}
