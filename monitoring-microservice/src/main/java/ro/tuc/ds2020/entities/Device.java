package ro.tuc.ds2020.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Device {
    @Id
    private UUID id;
    private Integer maximumHourlyEnergyConsumption;

//    @PrePersist
//    public void generateUUID() {
//        id = UUID.randomUUID();
//    }
}
