package ro.tuc.ds2020.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private UUID id;
    private String name;
    private RoleDTO role;
    public enum RoleDTO {
        ROLE_CLIENT("ROLE_CLIENT"),
        ROLE_ADMIN("ROLE_ADMIN");
        RoleDTO(String role_admin) {
        }
    }
}