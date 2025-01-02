package ro.tuc.ds2020.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
public class IndexController {
//    @Autowired
//    private final JwtService jwtService;
//
//    public IndexController(JwtService jwtService) {
//        this.jwtService = jwtService;
//    }

    @GetMapping(value = "/")
    public ResponseEntity<String> getStatus() {
        return new ResponseEntity<>("Devices MicroService is running...", HttpStatus.OK);
    }

//    @PostMapping(value = "/token")
//    public ResponseEntity<?> checkToken(@RequestBody String token) {
//        try {
//            UserDTO userDTO = jwtService.validateToken(token);
//            return new ResponseEntity<>(userDTO, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//    }
}
