package com.smarttaskmanager.notification_service.repository;

import com.smarttaskmanager.notification_service.entity.EventStatus;
import com.smarttaskmanager.notification_service.entity.NotificationEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<NotificationEvent, UUID> {
    List<NotificationEvent> findByStatus(EventStatus status);
    boolean existsByTaskId(UUID id);
}
