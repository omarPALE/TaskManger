package com.Taskmanger.omar;

import jakarta.persistence.*;
import lombok.Data;
@Table
@Data
@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private String category;
    private String DueDate;
    private String userId;

}
