package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserDetailService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Set;

@Controller
public class AdminController {
    private final UserService userService;
    private final UserDetailService userDetailService;
    private final RoleService roleService;
    private final UserValidator userValidator;


    public AdminController(UserService userService, UserDetailService userDetailService, RoleService roleService, UserValidator userValidator) {
        this.userService = userService;
        this.userDetailService = userDetailService;
        this.roleService = roleService;
        this.userValidator = userValidator;
    }

    @GetMapping("/admin")
    public String admin(Principal principal, Model model) {
        model.addAttribute("allUsers", userService.getUsers());
        model.addAttribute("authUser", userDetailService.loadUserByUsername(principal.getName()));
        model.addAttribute("newUser", new User());
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("activeTable", "usersTable");
        return "admin";
    }

    @PostMapping("/admin/new")
    public String createNewUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                                @RequestParam("selectedRoles") Set<Long> selectedRoles, Model model) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleService.findAll());
            return "admin";
        }
        userService.save(user, selectedRoles);
        return "redirect:/admin";
    }

    @PostMapping("/admin/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @RequestParam("selectedRoles") Set<Long> selectedRoles) {
        if (bindingResult.hasErrors()) {
            return "admin";
        }
        userService.save(user, selectedRoles);
        return "redirect:/admin";
    }

    @PostMapping("/admin/delete")
    public String delete(@RequestParam("id") long id) {
        userService.deleteUser(userService.getUserById(id));
        return "redirect:/admin";
    }
}
