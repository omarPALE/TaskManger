package com.Taskmanger.omar.service;

import com.Taskmanger.omar.Task;
import com.Taskmanger.omar.dao.TaskDao;
import com.Taskmanger.omar.dao.UserDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Taskmanger.omar.dto.TaskSearchDTO;
import java.util.List;
import java.util.Optional;

@Service
public class taskService {
    @Autowired
    TaskDao taskDao;
    @Autowired
    private UserDao userDao;

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

    public List<Task> getTaskByUserId(Long userId) {
        return taskDao.findByUser_Id(userId);
    }


    public void DeleteTaskById(int id) {
          taskDao.deleteById(id);
    }

    public boolean updateTask(Task task, int id) {
        Optional<Task> existingTask = taskDao.findById(id); // Fetch task by id
        if (existingTask.isPresent()) {
            Task updatedTask = existingTask.get();
            updatedTask.setTitle(task.getTitle());
            updatedTask.setDescription(task.getDescription());
            updatedTask.setStatus(task.getStatus());
            updatedTask.setPriority(task.getPriority());
            updatedTask.setCategory(task.getCategory());
            updatedTask.setDueDate(task.getDueDate());

            taskDao.save(updatedTask); // Save the updated task
            return true;
        } else {
            return false; // Task not found
        }
    }


    public Page<Task> searchTasks(TaskSearchDTO searchDTO, Pageable pageable) {
        return taskDao.findByFilters(
                searchDTO.getTitle(),
                searchDTO.getCategory(),
                searchDTO.getStatus(),
                searchDTO.getPriority(),
                searchDTO.getStartDate(),
                searchDTO.getEndDate(),
                pageable
        );
    }
}
