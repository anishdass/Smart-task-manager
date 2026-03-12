package com.smarttaskmanager.notification_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {
    private UUID id;

    private String title;

    private String description;

    private String status;

    private String priority;

    private String dueDate;

    private String userId;

    private String createdAt;
}
