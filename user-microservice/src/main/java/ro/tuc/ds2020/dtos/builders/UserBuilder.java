package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.UserDetailsDTO;
import ro.tuc.ds2020.entities.User;

public class UserBuilder {
    public static UserDTO toUserDTO(User user){
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .password(user.getPassword())
                .role(RoleBuilder.toRoleDTO(user.getRole()))
                .build();
    }
    public static User toEntity(UserDTO userDTO){
        return User.builder()
                .id(userDTO.getId())
                .name(userDTO.getName())
                .password(userDTO.getPassword())
                .role(RoleBuilder.toEntity(userDTO.getRole()))
                .build();
    }
    public static UserDetailsDTO toUserDetailsDTO(User user){
        return UserDetailsDTO.builder()
                .name(user.getName())
                .role(RoleBuilder.toRoleDTO(user.getRole()))
                .build();
    }
    public static class RoleBuilder {
        public static UserDTO.RoleDTO toRoleDTO(User.Role role){
            return UserDTO.RoleDTO.valueOf(role.toString());
        }
        public static User.Role toEntity(UserDTO.RoleDTO roleDTO){
            return User.Role.valueOf(roleDTO.toString());
        }
    }
}
