package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.service.UserDetailService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;


@Controller
//@RequestMapping("/users")
public class UsersController {

    private final UserDetailService userDetailService;
    private final UserService userService;


    public UsersController(UserDetailService userDetailService, UserService userService) {
        this.userDetailService = userDetailService;
        this.userService = userService;
    }


    @GetMapping("/user")
    public String show(Principal principal, Model model) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("authUser", userDetailService.loadUserByUsername(principal.getName()));
        model.addAttribute("userName", userDetailService.loadUserByUsername(principal.getName()).getUsername());
        return "user";
    }
}

