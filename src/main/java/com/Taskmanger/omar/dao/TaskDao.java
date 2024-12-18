package com.Taskmanger.omar.dao;

import com.Taskmanger.omar.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

@Repository
public interface TaskDao extends JpaRepository<Task, Integer> {
    // Your methods for interacting with the Task entity
    List<Task> findByUser_Id(Long userId);

        @Query("SELECT t FROM Task t WHERE " +
                "(:title IS NULL OR t.title LIKE %:title%) AND " +
                "(:category IS NULL OR t.category LIKE %:category%) AND " +
                "(:status IS NULL OR t.status = :status) AND " +
                "(:priority IS NULL OR t.priority = :priority) AND " +
                "(:startDate IS NULL OR t.dueDate >= :startDate) AND " +
                "(:endDate IS NULL OR t.dueDate <= :endDate)")
        Page<Task> findByFilters(String title, String category, String status, String priority,
                                 String startDate, String endDate, Pageable pageable);
    }


