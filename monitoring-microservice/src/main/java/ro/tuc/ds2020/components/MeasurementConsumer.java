package ro.tuc.ds2020.components;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import ro.tuc.ds2020.configs.RabbitMQConfig;
import ro.tuc.ds2020.dtos.ReadingDTO;
import ro.tuc.ds2020.services.DeviceService;
import ro.tuc.ds2020.services.MeasurementService;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class MeasurementConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<UUID, ReadingDTO> lastReadingMap = new HashMap<>();
    private final MeasurementService measurementService;
    private final DeviceService deviceService;

    public MeasurementConsumer(MeasurementService measurementService, DeviceService deviceService) {
        this.measurementService = measurementService;
        this.deviceService = deviceService;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitMQConfig.QUEUE_MEASUREMENT, durable = "true"),
            exchange = @Exchange(value = RabbitMQConfig.TOPIC_EXCHANGE, type = "topic"),
            key = RabbitMQConfig.MEASUREMENT_BINDING
    ))
    public void receiveMessage(String message) {
        try {
            // Deserialize JSON message to MeasurementDTO
            ReadingDTO measurement = objectMapper.readValue(message, ReadingDTO.class);
            log.info("Received Measurement: {}", measurement);

            UUID deviceId = measurement.getDevice_id();
            processMeasurement(deviceId, measurement);

        } catch (Exception e) {
            log.error("Failed to process message: {}", e.getMessage());
        }
    }

    private void processMeasurement(UUID deviceId, ReadingDTO newReading) {
        ReadingDTO lastReading = lastReadingMap.get(deviceId);

        if (lastReading != null) {
            // Calculate time and value difference
            long timeDiffMillis = newReading.getTimestamp() - lastReading.getTimestamp();
            double valueDiff = newReading.getMeasurement_value() - lastReading.getMeasurement_value();

            // Split the difference between hours crossed
            splitConsumptionByHour(lastReading.getTimestamp(), newReading.getTimestamp(), valueDiff, timeDiffMillis, deviceId);
        }

        // Update last reading
        lastReadingMap.put(deviceId, newReading);
    }

    private void splitConsumptionByHour(long startTimestamp, long endTimestamp, double totalConsumption, long totalMillis, UUID deviceId) {
        LocalDateTime start = LocalDateTime.ofInstant(Instant.ofEpochMilli(startTimestamp), ZoneId.systemDefault());
        LocalDateTime end = LocalDateTime.ofInstant(Instant.ofEpochMilli(endTimestamp), ZoneId.systemDefault());

        // Loop through each hour between start and end
        while (start.isBefore(end)) {
            // Calculate the next hour boundary, or end timestamp if within the same hour
            LocalDateTime nextHour = start.plusHours(1).truncatedTo(ChronoUnit.HOURS);
            if (nextHour.isAfter(end)) {
                nextHour = end;
            }

            // Calculate the milliseconds within this hour segment
            long millisInThisSegment = ChronoUnit.MILLIS.between(start, nextHour);
            double consumptionForThisSegment = totalConsumption * millisInThisSegment / totalMillis;

            // Store the proportional consumption for this segment
            long hourTimestamp = start.truncatedTo(ChronoUnit.HOURS).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            measurementService.storeValue(hourTimestamp, deviceId, consumptionForThisSegment);
            try {
                if(consumptionForThisSegment>deviceService.getMaximumHourlyEnergyConsumption(deviceId)){
                    WsChannelManager.broadcastMessage(deviceId.toString(), new TextMessage("Value exceeded for device " + deviceId + " at " + new Date(hourTimestamp) + " with value " + consumptionForThisSegment));
                }
            } catch (IOException e) {
                log.error("Failed to broadcast message to ws on channel {}: {}", deviceId, e.getMessage());
            }

            // Move to the next hour or end if within the same hour
            start = nextHour;
        }
    }
}



