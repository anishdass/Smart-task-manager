package com.smarttaskmanager.notification_service.controller;

import com.smarttaskmanager.notification_service.dto.TaskDTO;
import com.smarttaskmanager.notification_service.entity.NotificationEvent;
import com.smarttaskmanager.notification_service.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//Creating this class just to test the notification service without the cron job

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping("/trigger-daily-reminders")
    public ResponseEntity<String> getDailyReminders(){
        notificationService.getDailyReminders();
        return ResponseEntity.ok("Cron Job Triggered");
    }

    @GetMapping("/pending")
    public ResponseEntity<List<NotificationEvent>> getAllPendingNotifications() {
        // 1. Get the raw List from the service
        List<NotificationEvent> notifications = notificationService.getAllPendingNotifications();

        // 2. Return the data wrapped in a ResponseEntity
        return ResponseEntity.ok(notifications);
    }
}
