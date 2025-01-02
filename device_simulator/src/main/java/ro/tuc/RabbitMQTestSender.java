package ro.tuc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


public class RabbitMQTestSender {
    private static final Logger log = LoggerFactory.getLogger(RabbitMQTestSender.class);
    private static final String EXCHANGE_NAME = "topic_exchange";
    private static final String ROUTING_KEY = "monitoring.measurement";
    private static final String QUEUE_NAME = "queue";

    public static void main(String[] args) {
        if(args.length < 1) {
            log.error("Usage: java -jar <jar-file> <config-file>");
            return;
        }

        Properties properties = new Properties();

        // Load deviceId and fileName from configuration file
        try (FileInputStream fis = new FileInputStream(args[0])) {
            properties.load(fis);
        } catch (IOException e) {
            log.error("Failed to load configuration file", e);
            return;
        }

        String deviceId = properties.getProperty("deviceId");
        String fileName = properties.getProperty("fileName");

        if (deviceId == null || fileName == null) {
            log.error("Configuration file must contain 'deviceId' and 'fileName' properties");
            return;
        }

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("kangaroo-01.rmq.cloudamqp.com");
        factory.setPort(5672);
        factory.setUsername("avusqkmv");
        factory.setPassword("Q3sJZqE18M6BP--Xb3yuYCHxLdAzWv8v");
        factory.setVirtualHost("avusqkmv");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            // Declare the exchange and queue, and bind them
//            channel.exchangeDeclare(EXCHANGE_NAME, "topic", true);
//            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
//            channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "monitoring.changes");

            // Read measurement values from the CSV file
            List<String> lines = Files.readAllLines(Paths.get(fileName));

            // Initialize timestamp
            long timestamp = Instant.now().toEpochMilli();

            for (String line : lines) {
                double measurementValue;
                try {
                    measurementValue = Double.parseDouble(line.trim());
                } catch (NumberFormatException e) {
                    log.error("Invalid measurement value: {}", line);
                    continue; // Skip invalid values
                }

                // Create message JSON
                Map<String, Object> message = new HashMap<>();
                message.put("timestamp", timestamp);
                message.put("device_id", deviceId);
                message.put("measurement_value", measurementValue);

                // Convert to JSON
                ObjectMapper objectMapper = new ObjectMapper();
                String messageJson = objectMapper.writeValueAsString(message);

                // Send message
                channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, messageJson.getBytes());
                log.info("Sent message: {}", messageJson);

                // Increment timestamp by 10 minutes
                timestamp += 600_000; // 10 minutes in milliseconds
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}