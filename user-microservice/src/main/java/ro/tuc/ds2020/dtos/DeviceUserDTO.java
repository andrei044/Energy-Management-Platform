package ro.tuc.ds2020.dtos;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class DeviceUserDTO {
    private UUID id;
    private String name;
    private UserDTO.RoleDTO role;
}
