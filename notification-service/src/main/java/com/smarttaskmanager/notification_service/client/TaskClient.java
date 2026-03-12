package com.smarttaskmanager.notification_service.client;

import com.smarttaskmanager.notification_service.dto.TaskDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "task-service", url = "http://localhost:8081/api/tasks")
public interface TaskClient {
    @GetMapping("/due-tomorrow")
    List<TaskDTO> getTasksDueTomorrow();
}
