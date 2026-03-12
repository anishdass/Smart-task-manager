package com.smarttaskmanager.task_service.controllers;

import com.smarttaskmanager.task_service.dto.TaskPatchDTO;
import com.smarttaskmanager.task_service.dto.TaskUpdateDTO;
import com.smarttaskmanager.task_service.entity.Task;
import com.smarttaskmanager.task_service.repositories.TaskRepository;
import com.smarttaskmanager.task_service.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskRepository taskRepository;
    private final TaskService taskService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createTask(@RequestBody Task task){
        String userName= SecurityContextHolder.getContext().getAuthentication().getName();
        taskService.createTask(task, userName);
        Map<String, String>response = new HashMap<>();
        response.put("message", "Task created!");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> getTasksByUser(@PathVariable UUID userId){
        return ResponseEntity.ok(taskRepository.findByUserId(userId));
    }

    //    Get all tasks
    @GetMapping("/all")
    public  ResponseEntity<List<Task>> getAllTasks(){
        return ResponseEntity.ok(taskRepository.findAll());
    }

    //    Deleting a task
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteTask(@PathVariable UUID id) throws Exception {
        taskService.deleteTask(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Task deleted successfully");

        return ResponseEntity.ok(response);
    }

    //    Get task by id
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable UUID id) {
        Task task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    //    Get tasks filtered by priority
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Task>> getTasksByPriority(@PathVariable String priority) {
        return ResponseEntity.ok(taskService.getTasksByPriority(priority));
    }

    //    Get tasks filtered by Status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> getTasksByStatus(@PathVariable String status) {
        return ResponseEntity.ok(taskService.getTasksByStatus(status));
    }

    //    Update task
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable UUID id, @RequestBody TaskUpdateDTO updateDTO) {
        return ResponseEntity.ok(taskService.updateTask(id, updateDTO));
    }

    //    Patch task
    @PatchMapping("/{id}")
    public ResponseEntity<Map<String, String>> patchTask(@PathVariable UUID id, @RequestBody TaskPatchDTO patchDTO) {
        taskService.patchTask(id, patchDTO);
        Map<String, String> response=new HashMap<>();
        response.put("message", "Task Updated");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-tasks")
    public ResponseEntity<List<Task>> getMyTasks(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        // Use fromString() instead of valueOf()
        UUID userId = UUID.fromString(authentication.getDetails().toString());
        List<Task> userTasks= taskService.getTasksByUserId(userId);
        return ResponseEntity.ok(userTasks);
    }

    @GetMapping("/due-tomorrow")
    public ResponseEntity<List<Task>> getTasksDueTomorrow(){
        return ResponseEntity.ok(taskService.getTasksDueTomorrow());
    }
}
