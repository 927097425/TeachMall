package com.teachmall.content.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: 陈靖国
 * @Description: 广播mq的配置类
 * @Version: 1.0
 */

@Configuration
public class FanoutConfig {

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("coursepub.fanout");
    }
    @Bean
    public Queue elastQueue(){
        return new Queue("elast.queue");
    }
    @Bean
    public Queue redisQueue(){
        return new Queue("redis.queue");
    }
    @Bean
    public Queue minioQueue(){
        return new Queue("minio.queue");
    }

    /**
     * 绑定队列和交换机
     */
    @Bean
    public Binding bindingQueue1(Queue elastQueue, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(elastQueue).to(fanoutExchange);
    }


    @Bean
    public Binding bindingQueue2(Queue redisQueue, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(redisQueue).to(fanoutExchange);
    }

    @Bean
    public Binding bindingQueue3(Queue minioQueue, FanoutExchange fanoutExchange){
        return BindingBuilder.bind(minioQueue).to(fanoutExchange);
    }

}
