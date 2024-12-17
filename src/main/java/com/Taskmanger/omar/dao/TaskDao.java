package com.Taskmanger.omar.dao;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Taskmanger.omar.Task;

import java.util.List;

@Repository
public interface TaskDao extends JpaRepository<Task, Integer> {
    // Your methods for interacting with the Task entity
    List<Task> findByUser_Id(Long userId);

}
