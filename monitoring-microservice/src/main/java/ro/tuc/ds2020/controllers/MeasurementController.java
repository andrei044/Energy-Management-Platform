package ro.tuc.ds2020.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.HourlyMeasurementDTO;
import ro.tuc.ds2020.services.MeasurementService;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping(value = "/measurement")
@Slf4j
public class MeasurementController {
    private final MeasurementService measurementService;

    @Autowired
    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }

    @GetMapping(value = "/{id}/{value}")
    public ResponseEntity<List<HourlyMeasurementDTO>> getMeasurement(@PathVariable("id") String id, @PathVariable("value") String value) {
        log.info("Retrieving measurements for device with id {} for day {}", id, value);
        long day = Long.parseLong(value);
        UUID uuid = UUID.fromString(id);
        List<HourlyMeasurementDTO> measurementsForDay = measurementService.getMeasurementsForDay(uuid, day);
        log.info("Retrieved measurements for device with id {} for day {}", id, value);
        return new ResponseEntity<>(measurementsForDay, HttpStatus.OK);
    }
}
