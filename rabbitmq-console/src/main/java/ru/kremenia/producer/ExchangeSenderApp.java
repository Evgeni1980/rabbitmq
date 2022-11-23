package ru.kremenia.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ExchangeSenderApp {
    private static final String EXCHANGE_NAME = "directExchange";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            String message = "Hello World";

//            channel.basicPublish(EXCHANGE_NAME, "programing.best-practices.java", null, message.getBytes());
            channel.basicPublish(EXCHANGE_NAME, "php", null, message.getBytes("UTF-8"));
            System.out.println(" [*] Sent '" + message+ "'");
        }
    }
}
