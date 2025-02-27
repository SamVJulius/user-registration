package com.samvjulius.user_registration.service;

import com.samvjulius.user_registration.model.User;
import com.samvjulius.user_registration.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return "Email already in use!";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(false);
        user.setVerificationToken(UUID.randomUUID().toString());

        userRepository.save(user);

        // Send email verification link
        String verificationLink = "http://localhost:8080/api/auth/verify?token=" + user.getVerificationToken();
        emailService.sendEmail(user.getEmail(), "Verify Your Account", "Click the link to verify: " + verificationLink);

        return "Registration successful! Check your email for verification.";
    }

    public String verifyUser(String token) {
        Optional<User> optionalUser = userRepository.findByVerificationToken(token);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEnabled(true);
            user.setVerificationToken(null);
            userRepository.save(user);
            return "Email verified successfully!";
        }
        return "Invalid verification link!";
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public String updateUser(User updatedUser) {
        Optional<User> existingUserOpt = userRepository.findByEmail(updatedUser.getEmail());
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            existingUser.setFirstName(updatedUser.getFirstName());
            existingUser.setMiddleName(updatedUser.getMiddleName());
            existingUser.setLastName(updatedUser.getLastName());
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
            existingUser.setAddress(updatedUser.getAddress());
            userRepository.save(existingUser);
            return "User details updated successfully!";
        }
        return "User not found!";
    }
}