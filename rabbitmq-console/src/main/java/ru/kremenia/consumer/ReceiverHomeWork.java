package ru.kremenia.consumer;

import com.rabbitmq.client.*;

import java.util.Scanner;

public class ReceiverHomeWork {
    private final static String EXCHANGER_NAME = "home_work";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        Scanner scanner = new Scanner(System.in);

        channel.exchangeDeclare(EXCHANGER_NAME, BuiltinExchangeType.FANOUT);

        String queueName = scanner.nextLine();
        channel.queueBind(queueName, EXCHANGER_NAME, queueName);
        System.out.println("***waiting message");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("Received: " + message);
        };
        channel.basicConsume(queueName, false, deliverCallback, consumerTag -> { });
    }
}
