package ro.tuc.ds2020.controllers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.services.CookieService;
import ro.tuc.ds2020.services.DeviceService;
import ro.tuc.ds2020.services.JwtService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/device")
public class DeviceController {
    private final DeviceService deviceService;
    private final CookieService cookieService;
    private final JwtService jwtService;

    @Autowired
    public DeviceController(DeviceService deviceService, CookieService cookieService, JwtService jwtService) {
        this.deviceService = deviceService;
        this.cookieService = cookieService;
        this.jwtService = jwtService;
    }
    @GetMapping()
    public ResponseEntity<List<DeviceDTO>>getDevices(){
        return ResponseEntity.ok(deviceService.getDevices());
    }
    @GetMapping(value = "/user/{id}")
    public ResponseEntity<List<DeviceDTO>> getDevicesByUserId(@PathVariable("id") UUID id,HttpServletRequest request){
//        Optional<Cookie> jsessionid = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("JSESSIONID")).findFirst();
//        if(jsessionid.isEmpty()){
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//        Cookie cookie = jsessionid.get();
//        UserDTO userDTO = cookieService.verifyCookie(cookie.getValue());
//        if(!userDTO.getId().equals(id) && !userDTO.getRole().toString().equals("ROLE_ADMIN")){
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//        return new ResponseEntity<>(deviceService.getDevicesByUser(id), HttpStatus.OK);
        String authHeader = request.getHeader("Authorization");
        String token = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // Extract token
            try{
                UserDTO userDTO = jwtService.validateToken(token);
                if(!userDTO.getId().equals(id) && !userDTO.getRole().toString().equals("ROLE_ADMIN")){
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }
                try{
                    return new ResponseEntity<>(deviceService.getDevicesByUser(id), HttpStatus.OK);
                }catch (ResourceNotFoundException e){
                    return new ResponseEntity<>(List.of(), HttpStatus.OK);
                }
            }catch (Exception e){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    @GetMapping(value = "/me")
    public ResponseEntity<List<DeviceDTO>> getDevicesLogged(HttpServletRequest request){
//        Optional<Cookie> jsessionid = Arrays.stream(request.getCookies()).filter(cookie -> cookie.getName().equals("JSESSIONID")).findFirst();
//        if(jsessionid.isEmpty()){
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//        }
//        Cookie cookie = jsessionid.get();
//        UserDTO userDTO = cookieService.verifyCookie(cookie.getValue());
//
//        return new ResponseEntity<>(deviceService.getDevicesByUser(userDTO.getId()), HttpStatus.OK);
        String authHeader = request.getHeader("Authorization");
        String token = null;
        UserDTO userDTO;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // Extract token
            try{
                userDTO = jwtService.validateToken(token);
            }catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            try{
                return new ResponseEntity<>(deviceService.getDevicesByUser(userDTO.getId()), HttpStatus.OK);
            }catch (ResourceNotFoundException e){
                return new ResponseEntity<>(List.of(), HttpStatus.OK);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
    @PostMapping()
    public ResponseEntity<UUID> insertDevice(@RequestBody DeviceDTO deviceDTO){
        return new ResponseEntity<>(deviceService.insert(deviceDTO), HttpStatus.CREATED);
    }
    @PutMapping()
    public ResponseEntity<UUID> updateDevice(@RequestBody DeviceDTO deviceDTO){
        return new ResponseEntity<>(deviceService.update(deviceDTO), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<UUID> deleteDevice(@PathVariable("id") UUID id){
        deviceService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
