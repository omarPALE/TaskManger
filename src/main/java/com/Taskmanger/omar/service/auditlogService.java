package com.Taskmanger.omar.service;


import com.Taskmanger.omar.AuditLog;
import com.Taskmanger.omar.Task;
import com.Taskmanger.omar.dao.AuditDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class auditlogService {

    @Autowired
    private  AuditDao auditLogRpo;

    public String getAllLogs() {

        return auditLogRpo.findAll().toString();
    }

    public void recordAction(Long userId, String action, Optional<Task> task) {
        AuditLog auditLog = new AuditLog();

        // Set the user ID or user object depending on your AuditLog implementation
        auditLog.setUserId(userId);

        // Append task title to the action if the task is present
        String taskTitle = task.map(Task::getTitle).orElse("Unknown Task");
        auditLog.setAction(action + ": " + taskTitle);

        // Set the current timestamp
        auditLog.setTimestamp(LocalDateTime.now());

        // Save the audit log to the database
        auditLogRpo.save(auditLog);
    }

}
