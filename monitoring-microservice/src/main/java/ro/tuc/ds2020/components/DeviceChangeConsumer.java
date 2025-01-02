package ro.tuc.ds2020.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ro.tuc.ds2020.configs.RabbitMQConfig;
import ro.tuc.ds2020.dtos.DeviceSimpleDTO;
import ro.tuc.ds2020.services.DeviceService;

@Component
@Slf4j
public class DeviceChangeConsumer {
    private final DeviceService deviceService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DeviceChangeConsumer(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitMQConfig.QUEUE_DEVICE, durable = "true"),
            exchange = @Exchange(value = RabbitMQConfig.TOPIC_EXCHANGE, type = "topic"),
            key = RabbitMQConfig.DEVICE_BINDING
    ))
    public void receiveDeviceChangeMessage(String message) {
        try {
            // Parse JSON message
            DeviceSimpleDTO deviceDTO = objectMapper.readValue(message, DeviceSimpleDTO.class);
            log.info("Received Device: {}", deviceDTO);

            processDeviceChange(deviceDTO);

        } catch (Exception e) {
            log.error("Failed to process device change message: " + e.getMessage());
        }
    }

    private void processDeviceChange(DeviceSimpleDTO deviceDTO) {
        deviceService.insert(deviceDTO);
    }
}
