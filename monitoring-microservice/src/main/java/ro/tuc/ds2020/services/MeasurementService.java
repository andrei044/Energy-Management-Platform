package ro.tuc.ds2020.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.HourlyMeasurementDTO;
import ro.tuc.ds2020.dtos.builders.HourlyMeasurementBuilder;
import ro.tuc.ds2020.entities.HourlyMeasurement;
import ro.tuc.ds2020.repositories.DeviceRepository;
import ro.tuc.ds2020.repositories.HourlyMeasurementRepository;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Service
public class MeasurementService {
    private final HourlyMeasurementRepository measurementRepository;
    private final DeviceRepository deviceRepository;

    @Autowired
    public MeasurementService(HourlyMeasurementRepository measurementRepository, DeviceRepository deviceRepository) {
        this.measurementRepository = measurementRepository;
        this.deviceRepository = deviceRepository;
    }
    @Transactional
    public void storeValue(Long timestamp, UUID deviceId, Double value) {
        measurementRepository.findByDeviceIdAndTimestamp(deviceId, timestamp)
                .ifPresentOrElse(
                        measurement -> measurement.setEnergyConsumed(measurement.getEnergyConsumed()+value),
                        () -> {
                            var device = deviceRepository.findById(deviceId).orElseThrow();
                            var measurement = new HourlyMeasurement(device, timestamp, value);
                            measurementRepository.save(measurement);
                        }
                );
    }
    public List<HourlyMeasurementDTO> getMeasurementsForDay(UUID deviceId, long timestamp) {
        // Convert timestamp to LocalDate to isolate the day
//        LocalDate date = Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.UTC).toLocalDate();
//        LocalDate date = Instant.ofEpochMilli(timestamp).atOffset(ZoneOffset.ofHours(0)).toLocalDate();

        // Calculate start and end of the day in milliseconds
//        long startOfDay = date.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
//        long endOfDay = date.atTime(23, 59, 59, 999999999).toInstant(ZoneOffset.UTC).toEpochMilli();

        // Retrieve all hourly measurements for the given day and device
        return measurementRepository.findAllByDeviceIdAndDay(deviceId, timestamp, timestamp+86400000).stream().map(HourlyMeasurementBuilder::toHourlyMeasurementDTO).toList();
    }
}
