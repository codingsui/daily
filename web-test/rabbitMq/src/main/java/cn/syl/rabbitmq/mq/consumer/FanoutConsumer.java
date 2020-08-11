package cn.syl.rabbitmq.mq.consumer;

import cn.syl.rabbitmq.Contast;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class FanoutConsumer {

    @RabbitListener(queues = Contast.FANOUT_QUEUE_A)
    public void comsumetA(JSONObject obj){
        log.info("队列：{}，接收到消息：{}",Contast.FANOUT_QUEUE_A,obj);
    }
    @RabbitListener(queues = Contast.FANOUT_QUEUE_B)
    public void comsumetB(JSONObject obj){
        log.info("队列：{}，接收到消息：{}",Contast.FANOUT_QUEUE_B,obj);
    }
    @RabbitListener(queues = Contast.FANOUT_QUEUE_C)
    public void comsumetC(JSONObject obj){
        log.info("队列：{}，接收到消息：{}",Contast.FANOUT_QUEUE_C,obj);
    }
}
