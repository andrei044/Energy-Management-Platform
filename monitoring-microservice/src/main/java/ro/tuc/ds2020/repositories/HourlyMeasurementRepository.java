package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ro.tuc.ds2020.entities.HourlyMeasurement;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HourlyMeasurementRepository extends JpaRepository<HourlyMeasurement, Long> {
    Optional<HourlyMeasurement> findByDeviceIdAndTimestamp(UUID deviceId, Long timestamp);
    @Query("SELECT hm FROM HourlyMeasurement hm WHERE hm.device.id = :deviceId " +
            "AND hm.timestamp >= :startOfDay AND hm.timestamp < :endOfDay")
    List<HourlyMeasurement> findAllByDeviceIdAndDay(@Param("deviceId") UUID deviceId,
                                                    @Param("startOfDay") Long startOfDay,
                                                    @Param("endOfDay") Long endOfDay);
}
