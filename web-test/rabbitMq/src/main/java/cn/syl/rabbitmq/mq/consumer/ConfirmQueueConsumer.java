package cn.syl.rabbitmq.mq.consumer;

import cn.syl.rabbitmq.Contast;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class ConfirmQueueConsumer {

    @RabbitListener(queues = Contast.CONFIRM_CONSUMER_QUEUE)
    public void receiveMessage01(String msg, Channel channel, Message message) throws IOException {
        try {
            // 这里模拟一个空指针异常，
            String string = null;
            string.length();

            log.info("【Consumer01成功接收到消息】>>> {}", msg);
            // 确认收到消息，只确认当前消费者的一个消息收到
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            if (message.getMessageProperties().getRedelivered()) {
                log.info("【Consumer01】消息已经回滚过，拒绝接收消息 ： {}", msg);
                // 拒绝消息，并且不再重新进入队列
                //public void basicReject(long deliveryTag, boolean requeue)
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                log.info("【Consumer01】消息即将返回队列重新处理 ：{}", msg);
                //设置消息重新回到队列处理
                // requeue表示是否重新回到队列，true重新入队
                //public void basicNack(long deliveryTag, boolean multiple, boolean requeue)
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
            e.printStackTrace();
        }
    }

}
