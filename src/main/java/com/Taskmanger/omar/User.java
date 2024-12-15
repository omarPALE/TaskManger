package com.Taskmanger.omar;

import jakarta.persistence.*;

@Table(name = "users") // Explicitly naming the table
@Entity
public class User {

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

    // Getters
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // toString method
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
