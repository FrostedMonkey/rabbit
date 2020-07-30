package com.cc.rabbitmq.api.returnlistener;


import com.rabbitmq.client.*;

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
        String exchangeName="test_return_exchange";
        String routingKey="return.save";
        String routingKeyError="abc.save";
        String msg="return masg";
        Channel channel = connection.createChannel();
        channel.addReturnListener(new ReturnListener() {
            /**
             *
             * @param replyCode 响应码
             * @param replyText
             * @param exchange 路由exchange
             * @param routingKey
             * @param properties
             * @param body
             * @throws IOException
             */
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.err.println("---------handler return-----------");
                System.err.println("replyCode:"+replyCode);
                System.err.println("replyText:"+replyText);
                System.err.println("exchange:"+exchange);
                System.err.println("routingKey:"+routingKey);
                System.err.println("properties:"+properties);
                System.err.println("body:"+new String(body));
            }
        });
        //mandatory设置为true如果这条消息错误则不会删除
        //channel.basicPublish(exchangeName,routingKey,true,null,msg.getBytes());
        channel.basicPublish(exchangeName,routingKeyError,true,null,msg.getBytes());
    }
}
