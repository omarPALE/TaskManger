package com.Taskmanger.omar.controller;

import com.Taskmanger.omar.Task;
import com.Taskmanger.omar.User;

import com.Taskmanger.omar.dto.TaskSearchDTO;
import com.Taskmanger.omar.service.taskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/task")
@CrossOrigin(origins = "http://localhost:5173")  // Allowing CORS for your frontend
public class TaskController {

    @Autowired
    taskService taskservice;
    @Autowired
    UserController usercontroller;



    @GetMapping("/tasks")
    @CrossOrigin(origins = "http://localhost:5173")  // Allowing CORS for your frontend
    public List<Task> getAllTasks(){
        return taskservice.getAllTasks();
    }

    @GetMapping("/usertasks")
    @CrossOrigin(origins = "http://localhost:5173")  // Allowing CORS for your frontend
    public ResponseEntity<List<Task>> getTaskByUserID(){
        // Get the authenticated user
        ResponseEntity<User> userResponse = usercontroller.authenticatedUser();

        if (userResponse.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            System.out.println( ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("User is not authenticated."));
        }

        User authenticatedUser = userResponse.getBody();
        System.out.println("Authenticated user from get all tasks: " + authenticatedUser);

        if (authenticatedUser == null) {
            System.out.println(  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Could not retrieve authenticated user."));
        }
            System.out.println("user id is:"+authenticatedUser.getId());
             List<Task> tasks = taskservice.getTaskByUserId(authenticatedUser.getId());

        if (!tasks.isEmpty()) {
            return ResponseEntity.ok(tasks); // Return the found task
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if not found
        }
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:5173")  // Allowing CORS for your frontend
    public ResponseEntity<Optional<Task>> getTaskById(@PathVariable int id){

            Optional<Task> task = taskservice.getTaskByid(id);
        if (task.isPresent()) {
            return ResponseEntity.ok(task); // Return the found task
        } else {
            return ResponseEntity.notFound().build(); // Return 404 if not found
        }
    }

    @DeleteMapping("/delete/{id}")
    @CrossOrigin(origins = "http://localhost:5173")  // Allowing CORS for your frontend
    public String DeleteTask(@PathVariable int id){
        taskservice.DeleteTaskById(id);
        return "success";
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> UpdateTask(@PathVariable int id, @RequestBody Task task) {
        boolean isUpdated = taskservice.updateTask(task, id);  // Call service to update task

        if (isUpdated) {
            return ResponseEntity.ok("Task updated successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        }
    }

    @PostMapping("/addtask")
    @CrossOrigin(origins = "http://localhost:5173")  // Allowing CORS for your frontend
    public ResponseEntity<String> addTask(@RequestBody Task task) {
        try {
            System.out.println("Added Task: " + task);

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
    @GetMapping("/search")
    public ResponseEntity<Page<Task>> searchTasks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String priority,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            Pageable pageable) {

        TaskSearchDTO searchDTO = new TaskSearchDTO(title, category, status, priority, startDate, endDate);

        Page<Task> tasks = taskservice.searchTasks(searchDTO, pageable);

        return tasks.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(tasks);
    }



}

