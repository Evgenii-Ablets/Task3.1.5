package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class UsersAdding {
    private final RoleService roleSerivce;
    private final UserService userService;
    private final UserDetailService userDetailService;

    public UsersAdding(RoleService roleSerivce, UserService userService, UserDetailService userDetailService) {
        this.roleSerivce = roleSerivce;
        this.userService = userService;
        this.userDetailService = userDetailService;
    }

    @PostConstruct
    @Transactional
    public void addUser() {
        Role roleUser = new Role(1, "ROLE_USER");
        Role roleAdmin = new Role(2, "ROLE_ADMIN");
        roleSerivce.addRole(roleUser);
        roleSerivce.addRole(roleAdmin);
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
