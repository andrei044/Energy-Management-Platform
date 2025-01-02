package ro.tuc.ds2020.components;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ro.tuc.ds2020.dtos.UserDTO;
import ro.tuc.ds2020.services.UserService;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
//    @Autowired
//    private SessionActivityService sessionActivityService;
    @Transactional
    public void loadInitialData() {
        try{
            userService.loadUserByUsername("admin");
        } catch (Exception e) {
            userService.insert(UserDTO.builder().name("admin").password("admin").role(UserDTO.RoleDTO.ROLE_ADMIN).build());
        }
        try{
            userService.loadUserByUsername("client");
        } catch (Exception e) {
            userService.insert(UserDTO.builder().name("client").password("client").role(UserDTO.RoleDTO.ROLE_CLIENT).build());
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        sessionActivityService.deleteAll();
        loadInitialData();
    }
}
