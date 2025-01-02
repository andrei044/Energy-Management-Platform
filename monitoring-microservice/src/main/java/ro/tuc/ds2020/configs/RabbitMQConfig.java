package ro.tuc.ds2020.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_MEASUREMENT = "queue_measurement";
    public static final String QUEUE_DEVICE = "queue_device";
//    public static final String MONITORING_TOPIC = "monitoring";
//    public static final String DEVICES_TOPIC = "devices";
    public static final String TOPIC_EXCHANGE= "topic_exchange";
    public static final String MEASUREMENT_BINDING ="monitoring.measurement";
    public static final String DEVICE_BINDING="device.changes";
//    @Bean
//    public Queue deviceQueue() {
//        return new Queue(QUEUE_DEVICE, true);
//    }
//    @Bean
//    public Queue measurementQueue() {
//        return new Queue(QUEUE_MEASUREMENT, true);
//    }

//    @Bean
//    public TopicExchange exchange() {
//        return new TopicExchange(TOPIC_EXCHANGE);
//    }
//    @Bean
//    public TopicExchange deviceChangesExchange() {
//        return new TopicExchange(MONITORING_TOPIC);
//    }

//    @Bean
//    public Binding binding(Queue queue, TopicExchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with(MEASUREMENT_BINDING);
//    }
//    @Bean
//    public Binding deviceChangesBinding(Queue queue, TopicExchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with("device.#");
//    }
}
