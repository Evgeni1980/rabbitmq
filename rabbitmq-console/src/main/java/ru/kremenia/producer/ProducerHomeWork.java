package ru.kremenia.producer;

import com.rabbitmq.client.*;

import java.util.Scanner;

public class ProducerHomeWork {
    private final static String EXCHANGER_NAME = "home_work";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel();
             Scanner scanner = new Scanner(System.in)) {
            String message = null;
            channel.exchangeDeclare(EXCHANGER_NAME, BuiltinExchangeType.FANOUT);
            while (true) {
                String m = scanner.nextLine();
                int i = m.indexOf(' ');
                message = m.substring(i + 1);
                channel.queueDeclare(m.substring(0, i), true, false, false, null);
                channel.basicPublish(EXCHANGER_NAME, message, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
                System.out.println("Sent: " + message);
            }
        }
    }
}
