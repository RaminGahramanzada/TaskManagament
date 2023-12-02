package com.example.taskmanagament.controller;

import com.example.taskmanagament.service.PasswordResetService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordResetController {
    private final PasswordResetService passwordResetService;

    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String sendPasswordResetEmail(@RequestParam("email") String email) {
        passwordResetService.sendPasswordResetEmail(email);
        return "redirect:/login?resetSuccess";
    }
}
