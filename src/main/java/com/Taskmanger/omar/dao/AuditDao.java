package com.Taskmanger.omar.dao;

import com.Taskmanger.omar.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditDao extends JpaRepository<AuditLog, Integer> {
}
