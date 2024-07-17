package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

@Component
public class UsersAdding {
    private final UserService userService;
    private final UserDetailService userDetailService;

    public UsersAdding(UserService userService, UserDetailService userDetailService) {
        this.userService = userService;
        this.userDetailService = userDetailService;
    }

    @PostConstruct
    @Transactional
    public void addUser() {
        User user1 = new User();
        user1.setName("Oleg");
        user1.setUsername("user");
        user1.setAge(20);
        user1.setPassword("user");
        user1.setRoles(Set.of(new Role("ROLE_USER")));
        try {
            userDetailService.loadUserByUsername(user1.getUsername());
        } catch (UsernameNotFoundException ignored) {
            userService.addUser(user1);
        }
        User user = new User();
        user.setName("Admin");
        user.setUsername("admin");
        user.setAge(20);
        user.setPassword("admin");
        user.setRoles(Set.of(new Role("ROLE_ADMIN")));
        try {
            userDetailService.loadUserByUsername(user.getUsername());
        } catch (UsernameNotFoundException ignored) {
            userService.addUser(user);
        }
    }
}
