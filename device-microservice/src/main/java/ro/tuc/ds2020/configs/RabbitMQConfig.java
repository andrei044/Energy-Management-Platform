package ro.tuc.ds2020.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String QUEUE_NAME = "queue_devices";
    public static final String TOPIC_EXCHANGE_NAME = "topic_exchange";
    public static final String DEVICE_TOPIC_PATTERN = "device.changes";
}
