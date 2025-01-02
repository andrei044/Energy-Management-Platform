package ro.tuc.ds2020.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@Slf4j
public class IndexController {

    @GetMapping(value = "/")
    public ResponseEntity<String> getStatus() {
        log.info("Monitoring Service is running...");
        return new ResponseEntity<>("Monitoring Service is running...", HttpStatus.OK);
    }
}
