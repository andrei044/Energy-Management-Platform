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
    private String description;
    private String address;
    private Integer maximumHourlyEnergyConsumption;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "devices_users",
            joinColumns = @JoinColumn(name = "device_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private Set<User> users;

    @PrePersist
    public void generateUUID() {
//        if (id == null) {
            id = UUID.randomUUID();
//        }
    }
}
