package com.ruhuna.campuscaresystem.service;

import com.ruhuna.campuscaresystem.model.User;
import com.ruhuna.campuscaresystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    private UserRepository userRepository;
    private AuthService authService;
    private BCryptPasswordEncoder encoder;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        encoder = new BCryptPasswordEncoder();
        authService = new AuthService(userRepository, encoder);
    }

    @Test
    void authenticateUser_shouldFail_whenEmailNotFound() {
        when(userRepository.findByEmail("a@b.com")).thenReturn(Optional.empty());
        assertThatThrownBy(() -> authService.authenticateUser("a@b.com", "x"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Invalid credentials");
    }

    @Test
    void authenticateUser_shouldFail_whenPasswordWrong() {
        User u = new User("a@b.com", encoder.encode("secret"), "A");
        when(userRepository.findByEmail("a@b.com")).thenReturn(Optional.of(u));
        assertThatThrownBy(() -> authService.authenticateUser("a@b.com", "wrong"))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Invalid credentials");
    }

    @Test
    void authenticateUser_shouldReturnUser_whenCredentialsOk() {
        User u = new User("a@b.com", encoder.encode("secret"), "A");
        when(userRepository.findByEmail("a@b.com")).thenReturn(Optional.of(u));
        User res = authService.authenticateUser("a@b.com", "secret");
        assertThat(res.getEmail()).isEqualTo("a@b.com");
    }
}