package com.Taskmanger.omar.service;

import com.Taskmanger.omar.Task;
import com.Taskmanger.omar.dao.TaskDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class taskService {
    @Autowired

    TaskDao taskDao;
    public List<Task> getAllTasks() {

        return taskDao.findAll();
    }

//    public List<Task> getTaskByid(int id) {
//        return taskDao.findById(id);
//    }

    public Optional<Task> getTaskByid(int id) {
        return taskDao.findById(id);

    }

    public String addTask(Task task) {
         taskDao.save(task);
         return "success";
    }
}
