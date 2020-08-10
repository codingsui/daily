package cn.syl.rabbitmq.config;

import cn.syl.rabbitmq.Contast;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {

    @Bean
    public ContentTypeDelegatingMessageConverter messageConverter() {
        SimpleMessageConverter simpleConverter = new SimpleMessageConverter();
        ContentTypeDelegatingMessageConverter converter = new ContentTypeDelegatingMessageConverter(simpleConverter);
        converter.addDelegate("application/json", new Jackson2JsonMessageConverter());
        return converter;
    }

    @Bean
    public DirectExchange rabbitmqDirectExchange() {
        //Direct交换机
        return new DirectExchange(Contast.TEST_EXCHANGE, true, false);
    }

    @Bean
    public DirectExchange rabbitmqDelayDirectExchange() {
        //Direct交换机
        DirectExchange directExchange = new DirectExchange(Contast.TEST_DELAY_EXCHANGE, true, false);
        directExchange.setDelayed(true);
        return directExchange;
    }

    @Bean
    public Queue testQueue() {
        return new Queue(Contast.TEST_QUEUE);
    }


    @Bean
    public Queue testDirectExchangeQueue() {
        return new Queue(Contast.TEST_EXCHANGE_QUEUE);
    }

    @Bean
    public Binding bindDirect() {
        //链式写法，绑定交换机和队列，并设置匹配键
        return BindingBuilder
                //绑定队列
                .bind(testDirectExchangeQueue())
                //到交换机
                .to(rabbitmqDirectExchange())
                //并设置匹配键
                .withQueueName();
    }


    @Bean
    public Queue testDelayDirectExchangeQueue() {
        return new Queue(Contast.TEST_DELAY_EXCHANGE_QUEUE);
    }

    @Bean
    public Binding bindDelayDirect() {
        //链式写法，绑定交换机和队列，并设置匹配键
        return BindingBuilder
                //绑定队列
                .bind(testDelayDirectExchangeQueue())
                //到交换机
                .to(rabbitmqDelayDirectExchange())
                //并设置匹配键
                .withQueueName();
    }

}
