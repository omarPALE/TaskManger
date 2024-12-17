package com.Taskmanger.omar.service;

import com.Taskmanger.omar.dao.UserDao;
import com.Taskmanger.omar.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDao userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserDao userRepository) {
        this.userRepository = userRepository;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // Attempt to load the user by email (username) from the database
//        User user = findByUsername(username);
//
//        // Log user details for debugging purposes
//        logUserDetails(user);
//
//        // Return a Spring Security User object with authorities
//        return buildUserDetails(user);
//    }
    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        System.out.println("Authenticating user with identifier: " + identifier);

        // Try to find the user by email first; fallback to username if not found
        User user = userRepository.findByEmail(identifier)  // Try email first
                .or(() -> userRepository.findByUsername(identifier))  // Then username
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email or username: " + identifier));

        System.out.println("User found: " + user);

        // Return UserDetails to Spring Security
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>()  // Add roles/authorities here if applicable
        );
    }


    private User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " not found"));

    }

    private User findUserByEmail(String username) {
        // Fetch the user from the repository based on email (username)
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + username + " not found"));
    }

    private void logUserDetails(User user) {
        // Logging user details (ensure sensitive information is not logged)
        if (user != null) {
            System.out.println("User found: " + user.getUsername());  // Username logging
        }
    }

    private UserDetails buildUserDetails(User user) {
        // Construct and return UserDetails object from the User entity
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
