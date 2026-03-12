package com.smarttaskmanager.task_service.dto;

public record TaskUpdateDTO(String title, String description, String status, String priority, String dueDate, String userId) {
}
