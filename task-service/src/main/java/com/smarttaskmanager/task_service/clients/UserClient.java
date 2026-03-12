package com.smarttaskmanager.task_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "user-service", url = "http://localhost:8080/api/auth")
public interface UserClient {
    @GetMapping("/{id}/exists")
    Boolean checkUserExists(@PathVariable("id") UUID id);
}
