package com.Taskmanger.omar;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "users") // Explicitly naming the table
@Data
@NoArgsConstructor
@Entity
public class User { // Class name should be singular and start with uppercase
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE) // Keep sequence strategy
    private Long id; // Use Long for compatibility with databases using BIGINT

    @Column(nullable = false, unique = true) // Enforce constraints at the database level
    private String username;

    @Column(nullable = false) // Password should not be null
    private String password;

    @Column(nullable = false, unique = true) // Email should also be unique and not null
    private String email;

    @Column(nullable = false) // Role should not be null
    private String role;
}
