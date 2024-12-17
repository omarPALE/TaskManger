package com.Taskmanger.omar.service;

import com.Taskmanger.omar.LoginUserDto;
import com.Taskmanger.omar.dao.UserDao;
import com.Taskmanger.omar.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class AuthenticationService {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    @Autowired
    private final UserDao userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserDao userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(RegisterUserDto input) {
        User user = new User();
        user.setUsername(input.getUsername());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setRole(input.getRole());
        logger.info("Received request to register user: {}", input);
        logger.info("Before saving user: {}", user);
        User savedUser = userRepository.save(user);
        logger.info("User saved: {}", savedUser);
        return savedUser;
    }


    public User authenticate(LoginUserDto input) {
        System.out.println("before sign in email is :"+input.getEmail());
        System.out.println("before data base email :"+userRepository.findByEmail(input.getEmail()));
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getEmail(),
                        input.getPassword()
                )
        );
        System.out.println("sign in email is :"+input.getEmail());
        System.out.println("data base email :"+userRepository.findByEmail(input.getEmail()));

        return userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}