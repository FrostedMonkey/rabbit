package com.cc.rabbit;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
class SpringRabbitApplicationTests {

    @Test
    void contextLoads() {
    }
    @Autowired
    private RabbitAdmin rabbitAdmin;
    @Test
    public void testAdmin()throws Exception{
        rabbitAdmin.declareExchange(new DirectExchange("test.direct",false,false));
        rabbitAdmin.declareExchange(new TopicExchange("test.topic",false,false));
        rabbitAdmin.declareExchange(new FanoutExchange("test.fanout",false,false));
        rabbitAdmin.declareQueue(new Queue("test.direct.queue",false));
        rabbitAdmin.declareQueue(new Queue("test.topic.queue",false));
        rabbitAdmin.declareQueue(new Queue("test.fanout.queue",false));
        rabbitAdmin.declareBinding(new Binding("test.direct.queue",Binding.DestinationType.QUEUE
        ,"test.direct","direct",new HashMap<>()));
        rabbitAdmin.declareBinding(BindingBuilder
                        .bind((new Queue("test.topic.queue",false)))//直接常见队列
                        .to(new TopicExchange("test.topic",false,false))//直接创建交换机建立关联关系
                        .with("user.#"));//指定路由key

        rabbitAdmin.declareBinding(BindingBuilder
                .bind((new Queue("test.fanout.queue",false)))//直接常见队列
                .to(new FanoutExchange("test.fanout",false,false))//直接创建交换机建立关联关系
                 );//fanout是直连的所以不需要with
        //清空队列
        rabbitAdmin.purgeQueue("test.topic.queue",false);

    }

}
