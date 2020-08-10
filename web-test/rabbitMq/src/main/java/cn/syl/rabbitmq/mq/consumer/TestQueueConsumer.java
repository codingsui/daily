package cn.syl.rabbitmq.mq.consumer;

import cn.syl.rabbitmq.Contast;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TestQueueConsumer {

    @RabbitListener(queues = Contast.TEST_QUEUE)
    public void comsumet(JSONObject obj){
        log.info("队列：{}，接收到消息：{}",Contast.TEST_QUEUE,obj);
    }
}
