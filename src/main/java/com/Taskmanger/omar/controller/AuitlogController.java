package com.Taskmanger.omar.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.Taskmanger.omar.service.auditlogService;
@RestController
@RequestMapping("api/log")
@CrossOrigin(origins = "http://localhost:5173")
public class AuitlogController {

    @Autowired
    auditlogService auitlogservice;

    @GetMapping("/logs")
    public String getAllLogs(){
        return auitlogservice.getAllLogs();
    }
}
