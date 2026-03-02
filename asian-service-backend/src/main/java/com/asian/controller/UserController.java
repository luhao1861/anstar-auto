package com.asian.controller;

import com.asian.entity.User;
import com.asian.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;
    
    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody User user) {
        user.setRole("CUSTOMER");
        user.setCreatedAt(java.time.LocalDateTime.now());
        user = userRepository.save(user);
        return Map.of("success", true, "userId", user.getId(), "name", user.getName());
    }
    
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> credentials) {
        String phone = credentials.get("phone");
        String password = credentials.get("password");
        
        Optional<User> user = userRepository.findByPhone(phone);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return Map.of("success", true, "userId", user.get().getId(), "name", user.get().getName(), "role", user.get().getRole());
        }
        return Map.of("success", false, "message", "Invalid credentials");
    }
    
    @GetMapping("/{id}")
    public Optional<User> getUser(@PathVariable Long id) {
        return userRepository.findById(id);
    }
}
