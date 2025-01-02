package ro.tuc.ds2020.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.DuplicateResourceException;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.dtos.builders.UserBuilder;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class UserService  implements UserDetailsService{
    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UUID insert(UserDTO userDTO) {
        User user = UserBuilder.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (userRepository.findByName(user.getName()) != null) {
            throw new DuplicateResourceException("User with name: " + user.getName() + " already exists!");
        }
        User saved = userRepository.save(user);
        return saved.getId();
    }

    //    public UserDetailsDTO findUser(UserDTO userDTO){
//        User user = userRepository.findByName(userDTO.getName());
//        if(user == null){
//            throw new ResourceNotFoundException("User with name: " + userDTO.getName() + " not found!");
//        }
//        return UserBuilder.toUserDetailsDTO(user);
//    }
    public void delete(UserDTO userDTO) {
        User user = userRepository.findByName(userDTO.getName());
        if (user == null) {
            throw new ResourceNotFoundException("User with name: " + userDTO.getName() + " not found!");
        }
        userRepository.delete(user);
    }

    public void delete(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " not found!"));
        userRepository.delete(user);
    }

    public UUID update(UserDTO userDTO) {
        User user = userRepository.findByName(userDTO.getName());
        if (user == null) {
            throw new ResourceNotFoundException("User with name: " + userDTO.getName() + " not found!");
        }
        if (userDTO.getName() != null) {
            user.setName(userDTO.getName());
        }
        if (userDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        if (userDTO.getRole() != null) {
            user.setRole(User.Role.valueOf(userDTO.getRole().toString()));
        }

        User save = userRepository.save(user);
        return save.getId();
    }

    public List<UserDTO> getUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream()
                .map(UserBuilder::toUserDTO)
                .toList();
    }

    public UserDTO getUserByName(String name) {
        User user = userRepository.findByName(name);
        if (user == null) {
            throw new ResourceNotFoundException("User with name: " + name + " not found!");
        }
        return UserBuilder.toUserDTO(user);
    }

    public UserDTO getUserById(UUID userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new ResourceNotFoundException("User with id: " + userId + " not found!");
        }
        return UserBuilder.toUserDTO(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User byName = userRepository.findByName(username);
        if (byName == null) {
            throw new UsernameNotFoundException("User with name: " + username + " not found!");
        }
        return byName;
    }
}
