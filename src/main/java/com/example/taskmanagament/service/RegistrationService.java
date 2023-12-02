package com.example.taskmanagament.service;

import com.example.taskmanagament.dao.entity.User;
import com.example.taskmanagament.exception.ExceptionHandler;
import com.example.taskmanagament.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public RegistrationService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void registerUser(User user) {
        validateUser(user);
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
    }
    private void validateUser(User user) {
        if (!isValidEmail(user.getEmail())) {
            throw new ExceptionHandler("Invalid email format", "EMAIL_FORMAT_INVALID", HttpStatus.BAD_REQUEST);
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ExceptionHandler("Username is already taken", "USERNAME_ALREADY_TAKEN", HttpStatus.CONFLICT);
        }
    }

    private boolean isValidEmail(String email) {
        return email.contains("@");
    }
}