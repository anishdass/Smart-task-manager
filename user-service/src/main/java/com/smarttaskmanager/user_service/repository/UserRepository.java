package com.smarttaskmanager.user_service.repository;

import com.smarttaskmanager.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String Username);
    Optional<User> findById(UUID userId);
    boolean existsById(UUID userId);
    boolean existsByUsername(String Username);
}
