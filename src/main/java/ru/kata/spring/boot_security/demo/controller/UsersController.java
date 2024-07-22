package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserDetailService;
import ru.kata.spring.boot_security.demo.service.UserService;



@Controller
//@RequestMapping("/users")
public class UsersController {

    private final UserService userService;


    public UsersController(UserDetailService userDetailService, UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/user")
    public String show(Model model,  @AuthenticationPrincipal User user) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("authUser", user);
        return "user";
    }
}

