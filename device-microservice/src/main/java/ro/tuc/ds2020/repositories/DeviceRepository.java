package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.tuc.ds2020.entities.Device;

import java.util.List;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {
    @Query("SELECT d FROM Device d JOIN d.users u WHERE u.id = :userId")
    List<Device> findDevicesByUserId(@Param("userId") UUID userId);
}
