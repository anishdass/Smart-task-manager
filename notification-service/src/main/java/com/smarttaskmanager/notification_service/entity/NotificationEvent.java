package com.smarttaskmanager.notification_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notification_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID taskId;

    @Column(nullable = false)
    private UUID recipientUserId;

    @Column(nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    private EventStatus status;

    private LocalDateTime createdAt = LocalDateTime.now();


}
