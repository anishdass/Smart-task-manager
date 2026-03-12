package com.smarttaskmanager.user_service.controller;

import com.smarttaskmanager.user_service.dto.LoginRequest;
import com.smarttaskmanager.user_service.dto.LoginResponse;
import com.smarttaskmanager.user_service.entity.User;
import com.smarttaskmanager.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Map<String, String>> registerUser(@Valid  @RequestBody User user){
        return userService.registerUser(user);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return userService.loginUser(loginRequest);
    }

    @GetMapping(value = "all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //    Get User By Id
    @GetMapping("/{userId}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable UUID userId) {
        Optional<User> user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

//    Delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) throws Exception {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/exists")
    public ResponseEntity<Boolean> checkUserExists(@PathVariable UUID userId) {
        // Assuming you have a userService with a method to check existence
        boolean exists = userService.existsById(userId);
        return ResponseEntity.ok(exists);
    }
}
