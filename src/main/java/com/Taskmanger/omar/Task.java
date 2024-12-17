package com.Taskmanger.omar;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate; // For handling dates properly

@Table(name = "tasks") // Explicitly naming the table
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id; // Use Long for compatibility with databases using BIGINT

    @Column(name="title",nullable = false)
    private String title;

    @Column(name="description",nullable = false)
    private String description;

    @Column(name="status",nullable = false)
    private String status; // You might consider using an enum for status

    @Column(name="priority",nullable = false)
    private String priority; // You might consider using an enum for priority

    @Column(name="category",nullable = false)
    private String category; // You might consider using an enum for category

    @Column(name = "due_date", nullable = false) // Renaming to match database convention
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate; // Use LocalDate for handling dates properly

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Foreign key to the User entity

    // Getters and setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public Long getUserId() {
        return user != null ? user.getId() : null; // Assuming User has a getId() method
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id =  id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

//    public int getUserId() {
//        return user;
//    }
//
//    public void setUserId(int userId) {
//        this.user = userId;
//    }
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", priority='" + priority + '\'' +
                ", category='" + category + '\'' +
                ", dueDate=" + dueDate +
                ", userId=" + user +
                '}';
    }


}
