package ro.tuc.ds2020.dtos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SessionPayloadDTO {
    private String sessionId;
    private String userId;
    private UserDTO.RoleDTO role;
}
