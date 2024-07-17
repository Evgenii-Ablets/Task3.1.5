package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
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
//@RequestMapping("/users")
public class UsersController {
    private final UserService userService;
    private final UserDetailService userDetailService;
    private final RoleService roleService;
    private final UserValidator userValidator;


    public UsersController(UserService userService, UserDetailService userDetailService, RoleService roleService, UserValidator userValidator) {
        this.userService = userService;
        this.userDetailService = userDetailService;
        this.roleService = roleService;
        this.userValidator = userValidator;
    }


    @GetMapping("/admin/users")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "users";
    }

    @GetMapping("/user/show")
    public String show(Principal principal, Model model) {
        model.addAttribute("user", userDetailService.loadUserByUsername(principal.getName()));
        return "show";
    }

    @GetMapping("/admin/show")
    public String show(@RequestParam("id") long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "show";
    }

    @GetMapping("/admin/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("allRoles", roleService.findAll());
        return "new";
    }

    @PostMapping("/admin/new")
    public String createNewUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                                @RequestParam("selectedRoles") Set<Long> selectedRoles, Model model) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleService.findAll());
            return "new";
        }
        userService.save(user, selectedRoles);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/edit")
    public String edit(Model model, @RequestParam("id") long id) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("allRoles", roleService.findAll());
        return "edit";
    }

    @PostMapping("/admin/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                         @RequestParam("selectedRoles")Set<Long> selectedRoles) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        userService.save(user, selectedRoles);
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/delete")
    public String delete(@RequestParam("id") long id) {
        userService.deleteUser(userService.getUserById(id));
        return "redirect:/admin/users";
    }
}
