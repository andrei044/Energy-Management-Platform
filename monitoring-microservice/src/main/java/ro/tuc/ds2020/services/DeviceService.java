package ro.tuc.ds2020.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.components.WsChannelManager;
import ro.tuc.ds2020.dtos.DeviceSimpleDTO;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.repositories.DeviceRepository;

import java.util.List;
import java.util.UUID;

@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }
    public UUID insert(DeviceSimpleDTO deviceDTO) {
        Device device = DeviceBuilder.toEntity(deviceDTO);
        device = deviceRepository.save(device);
        WsChannelManager.addChannel(device.getId().toString());
        return device.getId();
    }
    public List<DeviceSimpleDTO> getAll() {
        List<Device> devices = deviceRepository.findAll();
        return devices.stream().map(DeviceBuilder::toSimpleDTO).toList();
    }
    public int getMaximumHourlyEnergyConsumption(UUID deviceId) {
        return deviceRepository.findById(deviceId).orElseThrow().getMaximumHourlyEnergyConsumption();
    }
}
