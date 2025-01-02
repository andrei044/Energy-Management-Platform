package ro.tuc.ds2020.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Types;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;
    private String name;
    private String password;
    private Role role;

    @PrePersist
    public void generateUUID() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(()->role.name());
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public enum Role {
        ROLE_CLIENT("ROLE_CLIENT"),
        ROLE_ADMIN("ROLE_ADMIN");

        Role(String role_client) {
        }
    }
}
