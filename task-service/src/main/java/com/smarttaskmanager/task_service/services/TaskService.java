package com.smarttaskmanager.task_service.services;

import com.smarttaskmanager.task_service.clients.UserClient;
import com.smarttaskmanager.task_service.dto.TaskPatchDTO;
import com.smarttaskmanager.task_service.dto.TaskUpdateDTO;
import com.smarttaskmanager.task_service.entity.Priority;
import com.smarttaskmanager.task_service.entity.Status;
import com.smarttaskmanager.task_service.entity.Task;
import com.smarttaskmanager.task_service.exception.UserNotFoundException;
import com.smarttaskmanager.task_service.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserClient userClient;

    public TaskService(TaskRepository taskRepository, UserClient userClient) {
        this.taskRepository = taskRepository;
        this.userClient = userClient;
    }

    public Task createTask(Task task, String username) {

        // Check if user exists via Feign/Rest Client
        Boolean userExists = userClient.checkUserExists(task.getUserId());

        if (!Boolean.TRUE.equals(userExists)) {
            throw new UserNotFoundException("User with ID " + task.getUserId() + " not found!");
        }

        return taskRepository.save(task);
    }

    public List<Task> getTasksByUserId(UUID userId) {
        return taskRepository.findByUserId(userId);
    }

    public void deleteTask(UUID taskId) throws Exception {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(()->new Exception("Task not found"));

        taskRepository.delete(task);
    }

    public Task getTaskById(UUID taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found with ID: " + taskId));
    }

    public List<Task> getTasksByPriority(String priorityString) {
        Priority priority=Priority.valueOf(priorityString.toUpperCase());
        return taskRepository.findByPriority(priority);
    }

    public List<Task> getTasksByStatus(String statusString) {
        Status status=Status.valueOf(statusString.toUpperCase());
        return taskRepository.findByStatus(status);
    }

    public Task updateTask(UUID id, TaskUpdateDTO updateDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (updateDTO.title() != null) task.setTitle(updateDTO.title());
        if (updateDTO.description() != null) task.setDescription(updateDTO.description());

        if (updateDTO.status() != null) {
            task.setStatus(Status.valueOf(updateDTO.status().toUpperCase()));
        }
        if (updateDTO.priority() != null) {
            task.setPriority(Priority.valueOf(updateDTO.priority().toUpperCase()));
        }

        if (updateDTO.dueDate() != null) {
            task.setDueDate(LocalDate.parse(updateDTO.dueDate()));
        }
        if (updateDTO.userId() != null) {
            task.setUserId(UUID.fromString(updateDTO.userId()));
        }

        return taskRepository.save(task);
    }


    public Task patchTask(UUID id, TaskPatchDTO patchDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (patchDTO.status() != null) {
            task.setStatus(Status.valueOf(patchDTO.status().toUpperCase()));
        }

        if (patchDTO.priority() != null) {
            task.setPriority(Priority.valueOf(patchDTO.priority().toUpperCase()));
        }

        return taskRepository.save(task);
    }

    public List<Task> getTasksDueTomorrow(){
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        return taskRepository.findByDueDate(tomorrow);
    }
}
