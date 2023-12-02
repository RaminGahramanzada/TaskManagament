package com.example.taskmanagament.service;


import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpService {

    private static final int OTP_LENGTH = 6;

    private final EmailService emailService;

    public OtpService(EmailService emailService) {
        this.emailService = emailService;
    }

    public String generateOtp() {
        StringBuilder otp = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }
    public void sendOtpByEmail(String email) {
        String otp = generateOtp();
        emailService.sendOtpEmail(email, otp);
    }
}