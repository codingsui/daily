
package cn.syl.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;

@Configuration
@Slf4j
public class RabbitMQConfig {

    /**
     *  * 1. 如果消息没有到exchange,则confirm回调,ack=false
     *  * 2. 如果消息到达exchange,则confirm回调,ack=true
     *  * 3. exchange到queue成功,则不回调return
     *  * 4. exchange到queue失败,则回调return
     * @param connectionFactory
     * @return
     */
    @Bean(name = "rabbitTemplate")
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        /**
         * 当mandatory标志位设置为true时
         * 如果exchange根据自身类型和消息routingKey无法找到一个合适的queue存储消息
         * 那么broker会调用basic.return方法将消息返还给生产者
         * 当mandatory设置为false时，出现上述情况broker会直接将消息丢弃
         */
        template.setMandatory(true);
        template.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {
                if (b) {
                    log.info("消息发送成功" + correlationData);
                } else {
                    log.info("消息发送失败:" + s);
                }
            }
        });
        template.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int i, String s, String s1, String s2) {
                // 反序列化对象输出
                log.info("消息主体: " + new String(message.getBody()));
                log.info("应答码: " + i);
                log.info("描述：" + s);
                log.info("消息使用的交换器 exchange : " + s1);
                log.info("消息使用的路由键 routing : " + s2);
            }
        });
        return template;
    }
}
