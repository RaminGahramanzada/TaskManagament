package com.example.taskmanagament.service;

import com.example.taskmanagament.dao.entity.User;
import com.example.taskmanagament.exception.ExceptionHandler;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface UserService {
    User getUserById(Long userId);
    User getUserByUsername(String username);
    User getUserByEmail(String email);
    List<User> getAllUsers();
    void createUser(User user) throws ExceptionHandler;
    void updateUser(User user) throws ExceptionHandler;
    void deleteUser(Long userId) throws ExceptionHandler;
}
