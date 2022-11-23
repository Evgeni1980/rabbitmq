package ru.kremenia.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class TasKReceiverApp {
    private static final String TASK_QUEUE_NAME = "task_queue";
    private static final String TASK_EXCHANGE = "task_exchanger";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        channel.queueBind(TASK_QUEUE_NAME, TASK_EXCHANGE, "");
        System.out.println(" [*] Waiting for messages");

        channel.basicQos(3);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery. getBody(), "UTF-8");
            doWork(message);
            System.out.println(" [*] Received '" + message +"'");
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };
        channel.basicConsume(TASK_QUEUE_NAME, false, deliverCallback, consumeTag -> { });

    }

    private static void doWork(String message) {
        for (char c: message.toCharArray()) {
            if (c == '.') {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
