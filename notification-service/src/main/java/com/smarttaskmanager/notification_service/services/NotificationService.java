package com.smarttaskmanager.notification_service.services;

import com.smarttaskmanager.notification_service.client.TaskClient;
import com.smarttaskmanager.notification_service.dto.TaskDTO;
import com.smarttaskmanager.notification_service.entity.EventStatus;
import com.smarttaskmanager.notification_service.entity.NotificationEvent;
import com.smarttaskmanager.notification_service.repository.NotificationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;


@Service
public class NotificationService {
    private final TaskClient taskClient;
    private final NotificationRepository notificationRepository;

    public NotificationService(TaskClient taskClient, NotificationRepository notificationRepository) {
        this.taskClient = taskClient;
        this.notificationRepository = notificationRepository;
    }

//    @Scheduled(cron = "0 0 9 * * *")
//    @Scheduled(fixedRate=1000)
    @PostMapping("/trigger-daily-reminders")
    public void getDailyReminders(){
        List<TaskDTO> tasks = taskClient.getTasksDueTomorrow();

        for(TaskDTO task:tasks){
            if(!notificationRepository.existsByTaskId(task.getId())){
                NotificationEvent event = new NotificationEvent();
                event.setTaskId(task.getId());
                event.setMessage("Alert: Task "+task.getTitle()+" is due tomorrow.");event.setStatus(EventStatus.PENDING);
                event.setRecipientUserId(UUID.fromString(task.getUserId()));
                notificationRepository.save(event);
            }
        }
    }

    public List<NotificationEvent> getAllPendingNotifications() {
        // Service should return the raw List, not an HTTP response
        return notificationRepository.findByStatus(EventStatus.PENDING);
    }


}
