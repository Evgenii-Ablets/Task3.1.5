package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();


    User getUserById(long id);

    void updateUser(User user);

    void deleteUser(User user);

    void addUser(User user);
}
