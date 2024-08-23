package com.teachmall.linsener;

import lombok.Data;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: 陈靖国
 * @Description: redis接收信息
 * @Version: 1.0
 */

@Component
public class RedisLinsener {

    @RabbitListener(queues = "redis.queue")
    public void listenRedisQueueMessage(String msg) throws InterruptedException {
        //ToDo 用死信交换机做个延迟消息保证在没有的情况下间隔3分钟后再次激活此函数
        boolean exist = false;
        if(!exist){

        }
        System.out.println("spring 消费者接收到消息：【" + msg + "】");
    }
    @RabbitListener(queues = "elast.queue")
    public void listenElastQueueMessage(String msg) throws InterruptedException {
        System.out.println("spring 消费者接收到消息：【" + msg + "】");
    }
    @RabbitListener(queues = "minio.queue")
    public void listenMinioQueueMessage(String msg) throws InterruptedException {
        System.out.println("spring 消费者接收到消息：【" + msg + "】");
    }

}
