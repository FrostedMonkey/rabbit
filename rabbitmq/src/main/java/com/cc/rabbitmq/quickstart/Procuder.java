package com.cc.rabbitmq.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Procuder {
    public static void main(String[] args) throws IOException, TimeoutException {
        //1、创建connectionFactory并进行设置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.159.128");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("test");
        connectionFactory.setPassword("123456");
        connectionFactory.setVirtualHost("/");
        //2、通过链接工厂创建一个连接
        Connection connection = connectionFactory.newConnection();
        //3、通过connection创建一个Channel
        Channel channel=connection.createChannel();
        //4、通过channel发送数据
        for(int i=0;i<5;i++){
            String msg="hello chenchen";
            /**
             * 这里需要参数exchange 和routingKey 但是我们没有指定exchange
             * 如果不指定会走第一个exchange第一个exchange(AMQP default)可以再网页管控台看到
             * 这个exchange的路由规则是：按照路由key（routingKey ）去找rabbitMq上有没有相同名字的队列
             * ，如果有就会自动投送
             * 但我们不指定的时候消息就会
             */
            channel.basicPublish("","chenchen",null,msg.getBytes());
        }
        //5、关闭
        channel.close();
        connection.close();

    }
}
