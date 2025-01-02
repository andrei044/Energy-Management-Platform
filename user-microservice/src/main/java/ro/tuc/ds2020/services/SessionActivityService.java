//package ro.tuc.ds2020.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import ro.tuc.ds2020.entities.SessionActivity;
//import ro.tuc.ds2020.repositories.SessionActivityRepository;
//
//@Service
//public class SessionActivityService {
//    private final SessionActivityRepository sessionActivityRepository;
//
//    @Autowired
//    public SessionActivityService(SessionActivityRepository sessionActivityRepository) {
//        this.sessionActivityRepository = sessionActivityRepository;
//    }
//
//    public Long insert(SessionActivity sessionActivity) {
//        SessionActivity byUsername = sessionActivityRepository.findByUsername(sessionActivity.getUsername());
//        if (byUsername != null) {
//            sessionActivityRepository.deleteById(byUsername.getId());
//        }
//        return sessionActivityRepository.save(sessionActivity).getId();
//    }
//
//    public void delete(Long id) {
//        sessionActivityRepository.deleteById(id);
//    }
//
//    public void deleteAll() {
//        sessionActivityRepository.deleteAll();
//    }
//
//    public void deleteBySessionId(String sessionId) {
//        SessionActivity bySessionId = sessionActivityRepository.findBySessionId(sessionId);
//        if (bySessionId != null) {
//            sessionActivityRepository.deleteById(bySessionId.getId());
//        }
//    }
//    public SessionActivity findBySessionId(String sessionId) {
//        return sessionActivityRepository.findBySessionId(sessionId);
//    }
//}
