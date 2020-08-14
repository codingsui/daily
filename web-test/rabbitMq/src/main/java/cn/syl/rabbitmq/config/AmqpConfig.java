package cn.syl.rabbitmq.config;

import cn.syl.rabbitmq.Contast;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
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
    public Queue moreQueue() {
        return new Queue(Contast.MORE_QUEUE);
    }
    @Bean
    public Binding bindMoreQueueToDirect() {
        //链式写法，绑定交换机和队列，并设置匹配键
        return BindingBuilder
                //绑定队列
                .bind(moreQueue())
                //到交换机
                .to(rabbitmqDirectExchange())
                //并设置匹配键
                .withQueueName();
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
    /*
        fanout交换机----------start
     */
    @Bean
    public Queue fanoutQueueA() {
        return new Queue(Contast.FANOUT_QUEUE_A);
    }
    @Bean
    public Queue fanoutQueueB() {
        return new Queue(Contast.FANOUT_QUEUE_B);
    }
    @Bean
    public Queue fanoutQueueC() {
        return new Queue(Contast.FANOUT_QUEUE_C);
    }
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(Contast.FANOUT_EXCHANGE, true, false);
    }
    @Bean
    public Binding bindFanoutExchangeA() {
        return BindingBuilder.bind(fanoutQueueA()).to(fanoutExchange());
    }
    @Bean
    public Binding bindFanoutExchangeB() {
        return BindingBuilder.bind(fanoutQueueB()).to(fanoutExchange());
    }
    @Bean
    public Binding bindFanoutExchangeC() {
        return BindingBuilder.bind(fanoutQueueC()).to(fanoutExchange());
    }

    /*
        fanout交换机----------end
     */


    /*
       topic交换机----------start
    */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(Contast.TOPIC_EXCHANGE, true, false);
    }
    @Bean
    public Queue topicQueueA() {
        return new Queue(Contast.TOPIC_QUEUE_A);
    }
    @Bean
    public Queue topicQueueB() {
        return new Queue(Contast.TOPIC_QUEUE_B);
    }
    @Bean
    public Queue topicQueueC() {
        return new Queue(Contast.TOPIC_QUEUE_C);
    }
    @Bean
    public Binding bindtopicExchangeA() {
        return BindingBuilder.bind(topicQueueA()).to(topicExchange()).with("a.#");
    }
    @Bean
    public Binding bindtopicExchangeB() {
        return BindingBuilder.bind(topicQueueB()).to(topicExchange()).with("a.*");
    }
    @Bean
    public Binding bindtopicExchangeC() {
        return BindingBuilder.bind(topicQueueC()).to(topicExchange()).with("a.b.*");
    }

    /*
       topic交换机----------end
    */

    /*
       消息确认交换机----------start
    */
    @Bean
    public Queue confirmQueue() {
        return new Queue(Contast.CONFIRM_CONSUMER_QUEUE);
    }
    @Bean
    public Binding bindconfirmQueue() {
        return BindingBuilder.bind(confirmQueue()).to(rabbitmqDirectExchange()).withQueueName();
    }
    /*
       消息确认交换机----------end
    */

}
