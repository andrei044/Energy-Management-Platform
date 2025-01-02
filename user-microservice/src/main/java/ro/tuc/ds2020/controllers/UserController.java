package ro.tuc.ds2020.controllers;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.services.DeviceUserService;
import ro.tuc.ds2020.services.UserService;

import java.util.List;
import java.util.UUID;
@CrossOrigin
@RestController
@RequestMapping(value = "/user")
public class UserController {
    private final UserService userService;
    private final DeviceUserService deviceService;
    @Autowired
    public UserController(UserService userService, DeviceUserService deviceService) {
        this.userService = userService;
        this.deviceService = deviceService;
    }
    @GetMapping()
    public ResponseEntity<List<UserDTO>> getUsers() {
        return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
    }
//    @GetMapping(value = "/{name}")
//    public ResponseEntity<UserDTO> getUser(@PathVariable("name") String name) {
//        return new ResponseEntity<>(userService.getUserByName(name), HttpStatus.OK);
//    }
    @GetMapping(value = "/id/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") UUID userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @PostMapping()
    @Transactional
    public ResponseEntity<UUID> insertUser(@RequestBody UserDTO userDTO) {
        UUID insert = userService.insert(userDTO);
        userDTO.setId(insert);
        deviceService.addUser(userDTO);
        return new ResponseEntity<>(insert, HttpStatus.CREATED);
    }
    @PutMapping()
    public ResponseEntity<UUID> updateUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.update(userDTO), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<UUID> deleteUser(@PathVariable("id") UUID id) {
        userService.delete(id);
        deviceService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
