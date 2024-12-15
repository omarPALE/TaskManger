package com.Taskmanger.omar;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate; // For handling dates properly

@Table(name = "tasks") // Explicitly naming the table
@Data
@NoArgsConstructor
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id; // Use Long for compatibility with databases using BIGINT

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String status; // You might consider using an enum for status

    @Column(nullable = false)
    private String priority; // You might consider using an enum for priority

    @Column(nullable = false)
    private String category; // You might consider using an enum for category

    @Column(name = "due_date", nullable = false) // Renaming to match database convention
    private LocalDate dueDate; // Use LocalDate for handling dates properly

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Foreign key to the User entity
}
