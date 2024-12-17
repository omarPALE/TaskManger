package com.Taskmanger.omar.controller;

import com.Taskmanger.omar.Task;
import com.Taskmanger.omar.User;

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
    @Autowired
    UserController usercontroller;

    @GetMapping("/tasks")
    public List<Task> getAllTasks(){
        return taskservice.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Task>> getTaskById(@PathVariable int id){

        Optional<Task> task = taskservice.getTaskByid(id);
        if (task.isPresent()) {
            return ResponseEntity.ok(task); // Return the found task
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if not found
        }
    }


    @PostMapping("/addtask")
    @CrossOrigin(origins = "http://localhost:5173")  // Allowing CORS for your frontend
    public ResponseEntity<String> addTask(@RequestBody Task task) {
        try {
            System.out.println("Received Task: " + task);

            // Get the authenticated user
            ResponseEntity<User> userResponse = usercontroller.authenticatedUser();

            if (userResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("User is not authenticated.");
            }

            User authenticatedUser = userResponse.getBody();
            System.out.println("Authenticated user from Task controller: " + authenticatedUser);

            if (authenticatedUser == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Error: Could not retrieve authenticated user.");
            }

            // Set the authenticated user to the task
            task.setUser(authenticatedUser);
            System.out.println("Task user set: " + task.getUser());

            // Call the service to add the task
            String response = taskservice.addTask(task);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error adding task: " + e.getMessage());
        }
    }

}

