package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.DeviceSimpleDTO;
import ro.tuc.ds2020.entities.Device;

public class DeviceBuilder {
    public static Device toEntity(DeviceSimpleDTO deviceDTO) {
        return Device.builder()
                .id(deviceDTO.getId())
                .maximumHourlyEnergyConsumption(deviceDTO.getMaximumHourlyEnergyConsumption())
                .build();
    }

    public static DeviceSimpleDTO toSimpleDTO(Device device) {
        return DeviceSimpleDTO.builder().
                id(device.getId())
                .maximumHourlyEnergyConsumption(device.getMaximumHourlyEnergyConsumption())
                .build();

    }
}
