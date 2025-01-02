package ro.tuc.ds2020.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin
public class IndexController {

//    private final SessionRegistry sessionRegistry;
//
//    @Autowired
//    public IndexController(SessionRegistry sessionRegistry) {
//        this.sessionRegistry = sessionRegistry;
//    }

    @GetMapping(value = "/")
    public ResponseEntity<String> getStatus() {
        return new ResponseEntity<>("Users MicroService is running...", HttpStatus.OK);
    }
    @GetMapping(value = "/home")
    public ResponseEntity<String> getTEST() {
        return new ResponseEntity<>("TEST", HttpStatus.OK);
    }
//    @GetMapping(value = "/session")
//    public ResponseEntity<List<String>> getTEST2() {
//        List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
//        List<String> allSessions = new ArrayList<>();
//        for (Object principal : allPrincipals) {
//            List<SessionInformation> sessions = sessionRegistry.getAllSessions(principal, false);
//            for (SessionInformation session : sessions) {
//                allSessions.add(session.getSessionId());
//            }
//        }
//        return new ResponseEntity<>(allSessions, HttpStatus.OK);
//    }
}
