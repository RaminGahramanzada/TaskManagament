package com.example.taskmanagament.service.impl;

import com.example.taskmanagament.dao.entity.User;
import com.example.taskmanagament.exception.ExceptionHandler;
import com.example.taskmanagament.repository.UserRepository;
import com.example.taskmanagament.service.OtpService;
import com.example.taskmanagament.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OtpService otpService;

    public UserServiceImpl(UserRepository userRepository, OtpService otpService) {
        this.userRepository = userRepository;
        this.otpService = otpService;
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void createUser(User user) throws ExceptionHandler {
        try {
            userRepository.save(user);
            String email = user.getEmail();
            otpService.sendOtpByEmail(email);
        } catch (Exception e) {
          //  throw new ExceptionHandler("Error creating user: " + e.getMessage(), errorCode, status);
        }
    }

    @Override
    public void updateUser(User user) throws ExceptionHandler {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            //throw new ExceptionHandler("Error updating user: " + e.getMessage(), errorCode, status);
        }
    }

    @Override
    public void deleteUser(Long userId) throws ExceptionHandler {
        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
          //  throw new ExceptionHandler("Error deleting user: " + e.getMessage(), errorCode, status);
        }
    }
}
