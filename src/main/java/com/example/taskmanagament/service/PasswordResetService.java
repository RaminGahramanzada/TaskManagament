package com.example.taskmanagament.service;

import com.example.taskmanagament.dao.entity.User;
import com.example.taskmanagament.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class PasswordResetService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    public PasswordResetService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }
    public void sendPasswordResetEmail(String userEmail) {
        String resetToken = UUID.randomUUID().toString();
        User user = userRepository.findByEmail(userEmail);
        user.setResetToken(resetToken);
        userRepository.save(user);

        String resetLink = "http://your-app-domain.com/reset-password?token=" + resetToken;
        String emailBody = "Click the link to reset your password: " + resetLink;
        String recipientEmail = user.getEmail();
        String otp = generateOtp();
        emailService.sendOtpEmail(recipientEmail, otp);
    }

    private String generateOtp() {
        return "123456";
    }
}
