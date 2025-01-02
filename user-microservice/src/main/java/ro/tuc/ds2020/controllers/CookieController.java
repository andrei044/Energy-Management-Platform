//package ro.tuc.ds2020.controllers;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.session.SessionInformation;
//import org.springframework.security.core.session.SessionRegistry;
//import org.springframework.web.bind.annotation.*;
//import ro.tuc.ds2020.dtos.DeviceUserDTO;
//import ro.tuc.ds2020.dtos.builders.DeviceUserBuilder;
//import ro.tuc.ds2020.entities.SessionActivity;
//import ro.tuc.ds2020.entities.User;
//import ro.tuc.ds2020.services.SessionActivityService;
//import ro.tuc.ds2020.services.UserService;
//
//@RestController
//@CrossOrigin
//@RequestMapping(value = "/cookie")
//@Slf4j
//public class CookieController {
//    private final SessionRegistry sessionRegistry;
//    private final SessionActivityService sessionActivityService;
//    private final UserService userService;
//
//    @Autowired
//    public CookieController(SessionRegistry sessionRegistry, SessionActivityService sessionActivityService, UserService userService) {
//        this.sessionRegistry = sessionRegistry;
//        this.sessionActivityService = sessionActivityService;
//        this.userService = userService;
//    }
//    @PostMapping()
//    public ResponseEntity<?> validateCookie(@RequestParam String cookie) {
//        log.info("Cookie: "+cookie);
//        SessionInformation sessionInformation = sessionRegistry.getSessionInformation(cookie);
//        if (sessionInformation == null) {
//            SessionActivity bySessionId = sessionActivityService.findBySessionId(cookie);
//            if(bySessionId!=null){
//                DeviceUserDTO deviceUserDTO = DeviceUserBuilder.toUserDTO(userService.getUserByName(bySessionId.getUsername()));
//                return new ResponseEntity<>(deviceUserDTO,HttpStatus.OK);
//            }
//
//            return new ResponseEntity<>(HttpStatus.OK);
//        }
//        DeviceUserDTO deviceUserDTO = DeviceUserBuilder.toUserDTO((User) sessionInformation.getPrincipal());
//        return new ResponseEntity<>(deviceUserDTO,HttpStatus.OK);
//    }
//}
