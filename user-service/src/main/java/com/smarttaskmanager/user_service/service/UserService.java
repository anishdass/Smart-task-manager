package com.smarttaskmanager.user_service.service;

import com.smarttaskmanager.user_service.dto.LoginRequest;
import com.smarttaskmanager.user_service.dto.LoginResponse;
import com.smarttaskmanager.user_service.entity.User;
import com.smarttaskmanager.user_service.exception.AuthenticationException;
import com.smarttaskmanager.user_service.repository.UserRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder1, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder1;
        this.jwtService = jwtService;
    }

    //    Register User
    public ResponseEntity<Map<String, String>> registerUser(@RequestBody User user) {
        Map<String, String> message=new HashMap<>();
        if (userRepository.existsByUsername(user.getUsername())) {
            message.put("message", "Username is already taken");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        }

        user.setUsername(user.getUsername().toLowerCase());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        message.put("message", "User Registered Successfully!");
        return ResponseEntity.status(HttpStatus.CREATED).body(message);

    }

    //    Login User
    public ResponseEntity<LoginResponse> loginUser(LoginRequest loginRequest) {

        User user = userRepository.findByUsername(loginRequest.username().toLowerCase())
                .orElseThrow(() -> new AuthenticationException("User not found!"));

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new AuthenticationException("Invalid password!");
        }

        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(new LoginResponse(token, "Bearer", user.getUsername(), user.getId()));
    }

    //    Get user by Id
    public Optional<User> getUserById(UUID userId) {
        return userRepository.findById(userId);
    }

    //    Get all users
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    //    Delete users
    public void deleteUser(UUID userId) throws Exception {
        User user=userRepository.findById(userId)
                .orElseThrow(()->new Exception("Task not found"));

        userRepository.delete(user);
    }

    //    Check user by Id
    public boolean existsById(UUID userId) {
        return userRepository.existsById(userId);
    }
}
