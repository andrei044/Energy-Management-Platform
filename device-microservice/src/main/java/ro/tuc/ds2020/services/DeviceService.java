package ro.tuc.ds2020.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.configs.RabbitMQConfig;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.dtos.builders.UserBuilder;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.DeviceRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final UserService userService;
    private final RabbitMQSender rabbitMQSender;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository, UserService userService, RabbitMQSender rabbitMQSender) {
        this.deviceRepository = deviceRepository;
        this.userService = userService;
        this.rabbitMQSender = rabbitMQSender;
    }
    public UUID insert(DeviceDTO deviceDTO) {
        Device device = DeviceBuilder.toEntity(deviceDTO);
        Set<User> users = new HashSet<>(userService.getUsersByIds(deviceDTO.getUsers().stream().map(UserDTO::getId).toList()));
        device.setUsers(users);
        device = deviceRepository.save(device);
        try {
            rabbitMQSender.sendMessage(RabbitMQConfig.TOPIC_EXCHANGE_NAME, RabbitMQConfig.DEVICE_TOPIC_PATTERN, DeviceBuilder.toDeviceSimpleDTO(device));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return device.getId();
    }
    public void delete(DeviceDTO deviceDTO) {
        Device device = deviceRepository.findById(deviceDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("Device with id: " + deviceDTO.getId() + " not found!"));
        deviceRepository.delete(device);
    }
    public void delete(UUID id) {
        Device device = deviceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Device with id: " + id + " not found!"));
        deviceRepository.delete(device);
    }
    public UUID update(DeviceDTO deviceDTO) {
        Device device = deviceRepository.findById(deviceDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("Device with id: " + deviceDTO.getId() + " not found!"));
        device.setAddress(deviceDTO.getAddress());
        device.setDescription(deviceDTO.getDescription());
        device.setUsers(deviceDTO.getUsers().stream().map(UserBuilder::toEntity).collect(Collectors.toSet()));
        device.setMaximumHourlyEnergyConsumption(deviceDTO.getMaximumHourlyEnergyConsumption());
        device = deviceRepository.save(device);
        try {
            rabbitMQSender.sendMessage(RabbitMQConfig.TOPIC_EXCHANGE_NAME, RabbitMQConfig.DEVICE_TOPIC_PATTERN, DeviceBuilder.toDeviceSimpleDTO(device));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return device.getId();
    }
    public DeviceDTO getDeviceById(UUID deviceId) {
        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new ResourceNotFoundException("Device with id: " + deviceId + " not found!"));
        return DeviceBuilder.toDeviceDTO(device);
    }
    public List<DeviceDTO> getDevices() {
        List<Device> devices = deviceRepository.findAll();
        return devices.stream().map(DeviceBuilder::toDeviceDTO).toList();
    }
    public List<DeviceDTO> getDevicesByUser(UUID userId) {
        List<Device> devicesByUserId = deviceRepository.findDevicesByUserId(userId);
        if(devicesByUserId.isEmpty())
            throw new ResourceNotFoundException("No devices found for user with id: " + userId + "!");
        return devicesByUserId.stream().map(DeviceBuilder::toDeviceDTO).toList();
    }

}
