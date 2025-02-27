package com.samvjulius.user_registration.controller;

import com.samvjulius.user_registration.model.User;
import com.samvjulius.user_registration.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @GetMapping("/verify")
    public String verifyUser(@RequestParam String token) {
        return userService.verifyUser(token);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User loginRequest) {
        Optional<User> userOptional = userService.findByEmail(loginRequest.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                return ResponseEntity.ok("Login Successful!");
            }
        }
        return ResponseEntity.badRequest().body("Invalid email or password");
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody User updatedUser) {
        return ResponseEntity.ok(userService.updateUser(updatedUser));
    }
}