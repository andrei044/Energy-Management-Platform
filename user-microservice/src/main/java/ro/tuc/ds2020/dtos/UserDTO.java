package ro.tuc.ds2020.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
public class UserDTO {
    private UUID id;
    private String name;
    private String password;
    private RoleDTO role;

    public enum RoleDTO {
        ROLE_CLIENT("ROLE_CLIENT"),
        ROLE_ADMIN("ROLE_ADMIN");
        RoleDTO(String role_admin) {
        }
    }
}
