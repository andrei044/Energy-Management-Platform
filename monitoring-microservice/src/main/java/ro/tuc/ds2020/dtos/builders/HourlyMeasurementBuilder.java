package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.HourlyMeasurementDTO;
import ro.tuc.ds2020.entities.HourlyMeasurement;

public class HourlyMeasurementBuilder {
    public static HourlyMeasurementDTO toHourlyMeasurementDTO(HourlyMeasurement hourlyMeasurement) {
        return HourlyMeasurementDTO.builder()
                .deviceId(hourlyMeasurement.getDevice().getId())
                .energyConsumed(hourlyMeasurement.getEnergyConsumed())
                .timestamp(hourlyMeasurement.getTimestamp()).build();
    }
}
