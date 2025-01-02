package ro.tuc.ds2020.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SessionPayloadDTO {
    private String sessionId;
    private String userId;
    private UserDTO.RoleDTO role;
}
