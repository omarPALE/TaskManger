package com.Taskmanger.omar.dao;

import com.Taskmanger.omar.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UserDao extends JpaRepository<User, Integer> {
}
