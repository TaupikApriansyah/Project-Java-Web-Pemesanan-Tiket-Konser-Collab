package com.example.tiket_booking.controller;

import com.example.tiket_booking.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfileController {
    private final UserService userService;
    public ProfileController(UserService userService){ this.userService = userService; }

    @GetMapping("/profile")
    public String profile(Authentication auth, Model model) {
        model.addAttribute("user", userService.findByEmail(auth.getName()));
        return "user/profile";
    }

    @PostMapping("/profile")
    public String update(Authentication auth, @RequestParam String nama, @RequestParam(required = false) String password) {
        userService.updateProfile(auth.getName(), nama, password);
        return "redirect:/profile?success";
    }
}
