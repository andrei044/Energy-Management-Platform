package ro.tuc.ds2020.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.builders.UserBuilder;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID insert(UserDTO userDTO) {
        User user = UserBuilder.toEntity(userDTO);
        User saved = userRepository.save(user);
        return saved.getId();
    }

    public UserDTO findUser(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("User with id: " + userDTO.getId() + " not found!"));
        return UserBuilder.toUserDTO(user);
    }

    public void delete(UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("User with id: " + userDTO.getId() + " not found!"));
        userRepository.delete(user);
    }

    public List<UserDTO> getUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(UserBuilder::toUserDTO)
                .toList();
    }

    public UserDTO getUserById(UUID userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new ResourceNotFoundException("User with id: " + userId + " not found!");
        }
        return UserBuilder.toUserDTO(user);
    }
    public void delete(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " not found!"));
        userRepository.delete(user);
    }
    public List<User> getUsersByIds(List<UUID> userIds) {
        return userRepository.findAllById(userIds);
    }
}
