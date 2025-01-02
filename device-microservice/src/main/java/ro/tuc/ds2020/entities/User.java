package ro.tuc.ds2020.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class User {
    @Id
    private UUID id;
//    private Role role;
//    private String name;

    public enum Role {
        ROLE_CLIENT("ROLE_CLIENT"),
        ROLE_ADMIN("ROLE_ADMIN");

        Role(String role_client) {
        }
    }
}
