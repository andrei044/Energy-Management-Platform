package ro.tuc.ds2020.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.SessionPayloadDTO;
import ro.tuc.ds2020.dtos.UserDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin
public class SessionController {
//    private final SessionRegistry sessionRegistry;
//
//    @Autowired
//    public SessionController(SessionRegistry sessionRegistry) {
//        this.sessionRegistry = sessionRegistry;
//    }
//    @GetMapping("/debug")
//    public ResponseEntity<List<String>> getSessions() {
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
//
//
//    @PostMapping("/session")
//    public ResponseEntity<String> registerSession(@RequestBody SessionPayloadDTO sessionPayloadDTO) {
////        String userId = sessionPayloadDTO.getUserId();
////        UserDTO.RoleDTO role = sessionPayloadDTO.getRole(); // Assuming this is the user's role
////        String sessionId = sessionPayloadDTO.getSessionId();
////
////        // Create an Authentication object (this should include authorities)
//////        Authentication authentication = new UsernamePasswordAuthenticationToken(userId, null, getAuthorities(role));
////        Authentication authentication = new UsernamePasswordAuthenticationToken(userId, userId, Collections.singleton(new SimpleGrantedAuthority(role.name())));
////
////        // Set the authentication in the SecurityContext
////        SecurityContextHolder.getContext().setAuthentication(authentication);
////
////        // Register the new session with the SessionRegistry
////        sessionRegistry.registerNewSession(sessionId, authentication.getPrincipal());
//
//        return ResponseEntity.ok("Session registered successfully");
//    }
//    @DeleteMapping("/session/{sessionId}")
//    public ResponseEntity<String> unregisterSession(@PathVariable String sessionId) {
//////        sessionRegistry.removeSessionInformation(sessionId);
////        sessionRegistry.getAllPrincipals().forEach(principal -> {
////            sessionRegistry.getAllSessions(principal, false).forEach(sessionInformation -> {
////                if(sessionInformation.getSessionId().equals(sessionId)) {
////                    sessionRegistry.removeSessionInformation(sessionId);
////                }
////            });
////        });
//        return ResponseEntity.ok("Session unregistered successfully");
//    }
//    // Convert role to a list of GrantedAuthorities
//    private Collection<GrantedAuthority> getAuthorities(UserDTO.RoleDTO role) {
//        if(role.name().equals("ROLE_ADMIN"))
//            return List.of(new SimpleGrantedAuthority("ADMIN"));
//        else
//            return List.of(new SimpleGrantedAuthority("CLIENT"));
//    }
//
//    @GetMapping("/checkSession")
//    public ResponseEntity<String> checkSession(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            return ResponseEntity.ok("Session is valid for user: " + principal);
//        }
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Session not found");
//    }
}
