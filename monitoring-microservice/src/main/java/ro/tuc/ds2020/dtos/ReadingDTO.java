package ro.tuc.ds2020.dtos;

import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class ReadingDTO {
    private Double measurement_value;
    private Long timestamp;
    private UUID device_id;
}
