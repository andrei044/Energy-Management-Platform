package ro.tuc.ds2020.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.tuc.ds2020.dtos.AuthRequestDTO;
import ro.tuc.ds2020.dtos.DeviceUserDTO;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.builders.DeviceUserBuilder;
import ro.tuc.ds2020.dtos.builders.UserBuilder;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.services.JwtService;
import ro.tuc.ds2020.services.UserService;

@RestController
@CrossOrigin
@Slf4j
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService service;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequestDTO authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        if (authentication.isAuthenticated()) {
            log.info("User: " + authRequest.getUsername() + " has been authenticated!");
            return ResponseEntity.ok(jwtService.generateToken(authRequest.getUsername(), ((User) authentication.getPrincipal()).getRole().name()));
        } else {
            log.error("Authentication failed for user: " + authentication);
            log.error("Could not authenticate user. Invalid user request!"+ authRequest.getUsername() + " " + authRequest.getPassword());
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }

    @PostMapping("/token")
    public ResponseEntity<DeviceUserDTO> token(@RequestBody String token) {
        String username = jwtService.extractUsername(token);
        UserDTO user = service.getUserByName(username);
        User userDetails = UserBuilder.toEntity(user);

        if (username != null && jwtService.validateToken(token, userDetails)) {
            log.info("User: " + username + " has been validated!");
            return ResponseEntity.ok(DeviceUserBuilder.toUserDTO(user));
        } else {
            log.error("Could not validate token. Invalid user request!");
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }
}
