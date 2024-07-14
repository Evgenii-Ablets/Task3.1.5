package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();


    User getUserById(long id);

    void updateUser(User user);

    void deleteUser(User user);

    void addUser(User user);

    public void save(User user, List<Long> selectedRoles);
}
