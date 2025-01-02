package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.tuc.ds2020.entities.SessionActivity;

public interface SessionActivityRepository extends JpaRepository<SessionActivity, Long> {
    SessionActivity findByUsername(String username);
    SessionActivity findBySessionId(String sessionId);
}
