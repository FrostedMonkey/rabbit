package com.cc.rabbitmq.api.limit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.159.130");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("test");
        connectionFactory.setPassword("123456");
        connectionFactory.setVirtualHost("/");
        Connection connection=connectionFactory.newConnection();
        String exchangeName="test_qos_exchange";
        String routingKey="qos.#";
        String queueName="test_qos_queue";
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(exchangeName,"topic",true,false,null);
        channel.queueDeclare(queueName,true,false,false,null);
        channel.queueBind(queueName,exchangeName,routingKey);
        //做限流 1、autoAck设置为false
        //2、prefetchSize消息的大小0是不做限制  prefetchCount代表可以一次处理的消息 global=false代表限流实在consumer级别
        channel.basicQos(0,1,false);
        channel.basicConsume(queueName,false,new MyConsumer(channel));

    }
}
