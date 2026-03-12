package com.smarttaskmanager.user_service;

import com.smarttaskmanager.user_service.dto.LoginRequest;
import com.smarttaskmanager.user_service.entity.User;
import com.smarttaskmanager.user_service.exception.AuthenticationException;
import com.smarttaskmanager.user_service.repository.UserRepository;
import com.smarttaskmanager.user_service.service.JwtService;
import com.smarttaskmanager.user_service.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceApplicationTests {

	@Mock private UserRepository userRepository;
	@Mock private PasswordEncoder passwordEncoder;
	@Mock private JwtService jwtService; // Mock this too!

	@InjectMocks
	private UserService userService;

	@Test
	void loginUser_ShouldThrowException_WhenPasswordIsIncorrect() {

		String username = "Anish";
		String wrongPassword = "password123";
		String correctHashedPassword = "encoded_password_123";

		LoginRequest loginRequest = new LoginRequest(username, wrongPassword);

		User mockUser = new User();
		mockUser.setId(UUID.randomUUID());
		mockUser.setUsername(username.toLowerCase());
		mockUser.setPassword(correctHashedPassword);

		when(userRepository.findByUsername(username.toLowerCase()))
				.thenReturn(Optional.of(mockUser));
		when(passwordEncoder.matches(wrongPassword, correctHashedPassword))
				.thenReturn(false);

		AuthenticationException exception = assertThrows(AuthenticationException.class, () -> {
			userService.loginUser(loginRequest);
		});

		assertEquals("Invalid password!", exception.getMessage());

		// 3. Verification (The Pro Touch)
		verify(jwtService, never()).generateToken(any());
	}

}
