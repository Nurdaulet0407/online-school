package com.example.onlineschool.controller;

import com.example.onlineschool.model.Role;
import com.example.onlineschool.model.User;
import com.example.onlineschool.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        User user = new User();
        user.setRole(Role.STUDENT);
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        user.setAvatarUrl("https://i.pravatar.cc/150?img=5");
        userService.registerUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "auth/login";
    }
}