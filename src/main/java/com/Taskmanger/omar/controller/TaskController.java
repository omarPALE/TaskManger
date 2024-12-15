package com.Taskmanger.omar.controller;

import com.Taskmanger.omar.service.taskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("task")
public class TaskController {

    @Autowired
    taskService taskservice;

    @GetMapping("alltasks")
    public String getAllTasks(){
        return taskService.getAllTasks();
    }
}
