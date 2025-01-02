package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.DeviceSimpleDTO;
import ro.tuc.ds2020.entities.Device;

import java.util.stream.Collectors;

public class DeviceBuilder {
    public static DeviceDTO toDeviceDTO(Device device) {
        return DeviceDTO.builder()
                .id(device.getId())
                .description(device.getDescription())
                .address(device.getAddress())
                .maximumHourlyEnergyConsumption(device.getMaximumHourlyEnergyConsumption())
                .users(device.getUsers().stream().map(UserBuilder::toUserDTO).toList())
                .build();
    }
    public static Device toEntity(DeviceDTO deviceDTO) {
        return Device.builder()
                .id(deviceDTO.getId())
                .description(deviceDTO.getDescription())
                .address(deviceDTO.getAddress())
                .maximumHourlyEnergyConsumption(deviceDTO.getMaximumHourlyEnergyConsumption())
                .users(deviceDTO.getUsers().stream().map(UserBuilder::toEntity).collect(Collectors.toSet()))
                .build();
    }
    public static DeviceSimpleDTO toDeviceSimpleDTO(Device device) {
        return DeviceSimpleDTO.builder()
                .id(device.getId())
                .maximumHourlyEnergyConsumption(device.getMaximumHourlyEnergyConsumption())
                .build();
    }
}
