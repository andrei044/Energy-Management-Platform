package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.entities.User;

public class UserBuilder {
    public static UserDTO toUserDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
//                .role(UserDTO.RoleDTO.valueOf(user.getRole().toString()))
//                .name(user.getName())
                .build();
    }

    public static User toEntity(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
//                .name(userDTO.getName())
//                .role(User.Role.valueOf(userDTO.getRole().toString()))
                .build();
    }
}
