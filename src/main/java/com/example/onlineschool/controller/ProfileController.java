package com.example.onlineschool.controller;

import com.example.onlineschool.model.User;
import com.example.onlineschool.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {

    private final UserRepository userRepository;

    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/profile")
    public String profile(Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);
        model.addAttribute("user", user);
        return "profile/profile";
    }

    @PostMapping("/profile/avatar")
    public String updateAvatar(@RequestParam String avatarUrl,
                               Authentication authentication) {

        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElseThrow();

        user.setAvatarUrl(avatarUrl);
        userRepository.save(user);

        return "redirect:/profile";
    }
}