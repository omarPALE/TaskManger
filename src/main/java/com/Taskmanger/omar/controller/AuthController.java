package com.Taskmanger.omar.controller;

import com.Taskmanger.omar.component.JwtUtil;
import com.Taskmanger.omar.dao.UserDao;
import com.Taskmanger.omar.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.Taskmanger.omar.User;

    @RestController
    @RequestMapping("/api/auth")
    public  class AuthController {

        @Autowired
        private UserDao userRepository;

        @Autowired
        private JwtUtil jwtUtil;

        @Autowired
        private UserDetailsServiceImpl userDetailsService;

        // Register user
        @PostMapping("/register")
        public ResponseEntity<String> registerUser(@RequestBody User user) {
            // Check if username exists
            if (userRepository.findByUsername(user.getUsername()).isPresent()) {
                return ResponseEntity.badRequest().body("Username is already taken");
            }

            // Save the new user (password should be encrypted)
            userRepository.save(user);
            return ResponseEntity.ok("User registered successfully");
        }

        // Login user and generate JWT
        @PostMapping("/login")
        public ResponseEntity<?> loginUser(@RequestBody User user) {
            // Authenticate user (you can implement password verification here)
            try {
                UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
                if (userDetails.getPassword().equals(user.getPassword())) {
                    String token = jwtUtil.generateToken(user.getUsername());
                    return ResponseEntity.ok("Bearer " + token);
                }
            } catch (UsernameNotFoundException e) {
                return ResponseEntity.status(401).body("Invalid credentials");
            }
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }


