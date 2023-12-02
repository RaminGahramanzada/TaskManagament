package com.example.taskmanagament.controller;

import com.example.taskmanagament.dao.entity.User;
import com.example.taskmanagament.exception.ExceptionHandler;
import com.example.taskmanagament.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (ExceptionHandler e) {
            return ResponseEntity.status(e.getStatus()).body(null);
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        try {
            User user = userService.getUserByUsername(username);
            return ResponseEntity.ok(user);
        } catch (ExceptionHandler e) {
            return ResponseEntity.status(e.getStatus()).body(null);
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        try {
            User user = userService.getUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (ExceptionHandler e) {
            return ResponseEntity.status(e.getStatus()).body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (ExceptionHandler e) {
            return ResponseEntity.status(e.getStatus()).body(null);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createUser(@RequestBody User user) {
        try {
            userService.createUser(user);
            return ResponseEntity.ok().build();
        } catch (ExceptionHandler e) {
            return ResponseEntity.status(e.getStatus()).build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateUser(@RequestBody User user) {
        try {
            userService.updateUser(user);
            return ResponseEntity.ok().build();
        } catch (ExceptionHandler e) {
            return ResponseEntity.status(e.getStatus()).build();
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok().build();
        } catch (ExceptionHandler e) {
            return ResponseEntity.status(e.getStatus()).build();
        }
    }
}
