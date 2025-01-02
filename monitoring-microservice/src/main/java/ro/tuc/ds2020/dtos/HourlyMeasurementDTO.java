package ro.tuc.ds2020.dtos;

import lombok.*;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HourlyMeasurementDTO {
    private Double energyConsumed;
    private Long timestamp;
    private UUID deviceId;
}
