package com.cc.rabbitmq.api.limit;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.159.130");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("test");
        connectionFactory.setPassword("123456");
        connectionFactory.setVirtualHost("/");
        Connection connection=connectionFactory.newConnection();
        String exchangeName="test_qos_exchange";
        String routingKey="qos.save";
        String msg="return masg";
        Channel channel = connection.createChannel();
        for(int i=0;i<10;i++){
            //mandatory设置为true如果这条消息错误则不会删除
            channel.basicPublish(exchangeName,routingKey,true,null,msg.getBytes());
        }
    }
}
