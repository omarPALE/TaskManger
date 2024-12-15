package com.Taskmanger.omar.controller;


import com.Taskmanger.omar.User;
import com.Taskmanger.omar.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {
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
}
