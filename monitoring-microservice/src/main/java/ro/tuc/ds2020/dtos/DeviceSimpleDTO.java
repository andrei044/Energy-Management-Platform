package ro.tuc.ds2020.dtos;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceSimpleDTO {
    private UUID id;
    private Integer maximumHourlyEnergyConsumption;
}
