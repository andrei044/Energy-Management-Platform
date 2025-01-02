package ro.tuc.ds2020.components;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ro.tuc.ds2020.dtos.DeviceSimpleDTO;
import ro.tuc.ds2020.services.DeviceService;

import java.util.List;
@Slf4j
@Component
public class StartupRunner implements ApplicationRunner {

    private final DeviceService deviceService;

    public StartupRunner(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("Creating channels for all devices at startup:");
        List<DeviceSimpleDTO> devices = deviceService.getAll();
        for (DeviceSimpleDTO device : devices) {
            WsChannelManager.addChannel(device.getId().toString());
        }
    }
}