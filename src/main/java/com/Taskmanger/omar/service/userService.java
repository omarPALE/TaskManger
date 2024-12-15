package com.Taskmanger.omar.service;

import com.Taskmanger.omar.dao.UserDao;
import com.Taskmanger.omar.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class userService {

    @Autowired

    UserDao userdao;
    public String addUser(User user) {
        userdao.save(user);
        return "success";
    }
}
