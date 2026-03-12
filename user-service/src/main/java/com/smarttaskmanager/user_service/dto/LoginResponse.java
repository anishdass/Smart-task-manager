package com.smarttaskmanager.user_service.dto;

import java.util.UUID;

public record LoginResponse(String token, String tokenType, String username, UUID userId) {
}
