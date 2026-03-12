package com.smarttaskmanager.task_service.repositories;

import com.smarttaskmanager.task_service.entity.Priority;
import com.smarttaskmanager.task_service.entity.Status;
import com.smarttaskmanager.task_service.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findByUserId(UUID userId);
    List<Task> findByPriority(Priority priority);
    List<Task> findByStatus(Status status);
    List<Task> findByDueDate(LocalDate date);
}
