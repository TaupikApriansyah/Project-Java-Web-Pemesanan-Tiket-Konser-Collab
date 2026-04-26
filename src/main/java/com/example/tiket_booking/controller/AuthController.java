package com.example.tiket_booking.controller;

import com.example.tiket_booking.model.User;
import com.example.tiket_booking.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {
    private final UserService userService;
    public AuthController(UserService userService){ this.userService = userService; }

    @GetMapping("/login")
    public String login(){ return "auth/login"; }

    @GetMapping("/register")
    public String registerForm(Model model){ model.addAttribute("user", new User()); return "auth/register"; }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model){
        try { userService.register(user); return "redirect:/login?registered"; }
        catch (RuntimeException e) { model.addAttribute("error", e.getMessage()); model.addAttribute("user", user); return "auth/register"; }
    }
}
