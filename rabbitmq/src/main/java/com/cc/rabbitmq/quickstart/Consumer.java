package com.cc.rabbitmq.quickstart;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 我们应该先启动Consumer，因为只有启动了Consumer rabbitmq上才会有这个队列，生产者生产了才能进行路由
 */
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
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
        //4、生命（创建）一个队列
        String queueName="chenchen";
        channel.queueDeclare(queueName,true,false,false,null);
        //5、创建消费者
        QueueingConsumer queueingConsumer=new QueueingConsumer(channel);
        //6、设置channel
        channel.basicConsume(queueName,true,queueingConsumer);
        //7、获取消息
        while(true){
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String msg=new String(delivery.getBody());
            System.out.println("消费端："+msg);
        }
    }
}
