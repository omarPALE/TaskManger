package com.Taskmanger.omar.controller;

import com.Taskmanger.omar.Task;
import com.Taskmanger.omar.service.taskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/task")
public class TaskController {

    @Autowired
    taskService taskservice;

    @GetMapping("tasks")
    public List<Task> getAllTasks(){
        return taskservice.getAllTasks();
    }

    @GetMapping("{id}")
    public ResponseEntity<Optional<Task>> getTaskById(@PathVariable int id){

        Optional<Task> task = taskservice.getTaskByid(id);
        if (task.isPresent()) {
            return ResponseEntity.ok(task); // Return the found task
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if not found
        }
    }

    @PostMapping("addtask")
    public ResponseEntity<String> addTask(@RequestBody Task task) {
        try {
            String response = taskservice.addTask(task);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Log the error message for debugging purposes (optional)
            e.printStackTrace();

            // Return an error response with the appropriate status code
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding task: " + e.getMessage());
        }
    }

}

