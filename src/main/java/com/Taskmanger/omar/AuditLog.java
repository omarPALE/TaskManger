package com.Taskmanger.omar;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "auditlog")
@NoArgsConstructor
@Entity
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JoinColumn(name = "user_id", nullable = false)
    private Long user_id;

    @Column(nullable = false)
    private String action;

    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    // Getter and Setter for id
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Getter and Setter for user_id
    public Long getUser() {
        return user_id;
    }

    // Getter and Setter for action
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    // Getter and Setter for timestamp
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    // toString Method
    @Override
    public String toString() {
        return "AuditLog{" +
                "id=" + id +
                ", user=" + (user_id != null ? user_id: "null") + // Avoid LazyInitializationException
                ", action='" + action + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    public void setUserId(Long userId) {
        this.user_id = userId;

    }
}
