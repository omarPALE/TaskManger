package com.Taskmanger.omar.dao;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Taskmanger.omar.Task;

@Repository
public interface TaskDao extends JpaRepository<Task, Integer> {
    // Your methods for interacting with the Task entity
}
