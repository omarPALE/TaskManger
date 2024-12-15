package com.Taskmanger.omar;

import jakarta.persistence.*;
import lombok.Data;

@Table
@Data
@Entity
public class users {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE )
    private int id ;
    private String username ;
    private String password;
    private String email;
    private String role;

}
