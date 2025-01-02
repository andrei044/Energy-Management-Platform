package ro.tuc.ds2020.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class HourlyMeasurement {
    @Id
    @GeneratedValue
    private Long id;
    private Double energyConsumed;
    private Long timestamp;
    @ManyToOne
    private Device device;

    public HourlyMeasurement(Device device, Long timestamp, Double value) {
        this.device = device;
        this.timestamp = timestamp;
        this.energyConsumed = value;
    }
}
