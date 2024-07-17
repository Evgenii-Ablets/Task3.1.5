package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserById(long id) throws EntityNotFoundException {
        User user = userRepository.findById(id).orElse(null);
        if(user == null) {
            throw new EntityNotFoundException("User not found");
        }
        return user;
    }

    @Transactional
    @Override
    public void updateUser(User user) throws EntityNotFoundException {
        User userFromDB = userRepository.findById(user.getId()).orElse(null);
        if(userFromDB == null) {
            throw new EntityNotFoundException("User not found");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteUser(User user) throws EntityNotFoundException {
        User userFromDB = userRepository.findById(user.getId()).orElse(null);
        if(userFromDB == null) {
            throw new EntityNotFoundException("User not found");
        }
        userRepository.delete(userFromDB);
    }

    @Transactional
    @Override
    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void save(User user, Set<Long> selectedRoles) {
        User savedUser = userRepository.save(user);
        savedUser.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>(roleRepository.findAllById(selectedRoles));
        savedUser.setRoles(roles);
        userRepository.save(savedUser);
    }

}
