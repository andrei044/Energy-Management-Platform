package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.DeviceUserDTO;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.UserDetailsDTO;
import ro.tuc.ds2020.entities.User;

public class DeviceUserBuilder {
    public static DeviceUserDTO toUserDTO(User user){
        return DeviceUserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .role(UserBuilder.RoleBuilder.toRoleDTO(user.getRole()))
                .build();
    }
    public static User toEntity(DeviceUserDTO deviceUserDTO){
        return User.builder()
                .id(deviceUserDTO.getId())
                .name(deviceUserDTO.getName())
                .role(UserBuilder.RoleBuilder.toEntity(deviceUserDTO.getRole()))
                .build();
    }
    public static DeviceUserDTO toUserDTO(UserDTO userDTO){
        return DeviceUserDTO.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .role(userDTO.getRole())
                .build();
    }
}
