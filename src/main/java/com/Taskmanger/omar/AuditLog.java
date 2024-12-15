package com.Taskmanger.omar;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime; // For storing timestamp

@Table(name = "auditlog") // Naming the table consistently
@Data
@NoArgsConstructor
@Entity
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id; // Use Long for compatibility with databases using BIGINT

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // Foreign key to the User entity
    private User user; // Link to the user who performed the action

    @Column(nullable = false)
    private String action; // Description of the action performed

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp; // Store timestamp of when the action occurred
}
