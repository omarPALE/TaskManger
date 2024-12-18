package com.Taskmanger.omar.dto;

public class TaskSearchDTO {
    private String title;
    private String category;
    private String status;
    private String priority;
    private String startDate;
    private String endDate;

    public TaskSearchDTO(String title, String category, String status, String priority, String startDate, String endDate) {
        this.title = title;
        this.category = category;
        this.status = status;
        this.priority = priority;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
