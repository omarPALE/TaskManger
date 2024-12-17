package com.Taskmanger.omar.controller;


import com.Taskmanger.omar.User;
import com.Taskmanger.omar.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class
UserController {
    @Autowired
    userService userservice;
    @PostMapping("adduser")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        try {
            System.out.println("Received Task: " + user);
            String response = userservice.addUser(user);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Log the error message for debugging purposes (optional)
            e.printStackTrace();

            // Return an error response with the appropriate status code
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding task: " + e.getMessage());
        }
    }


    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authentication: " + authentication); // Debug line

        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        User currentUser = (User) authentication.getPrincipal();
        return ResponseEntity.ok(currentUser);
    }



    @GetMapping("/")
    public ResponseEntity<List<User>> allUsers() {
        List <User> users = userservice.allUsers();

        return ResponseEntity.ok(users);
    }
}
