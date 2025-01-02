package ro.tuc.ds2020.dtos;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class DeviceDTO {
    private UUID id;
    private String description;
    private String address;
    private Integer maximumHourlyEnergyConsumption;
    private List<UserDTO>users;
}
