package com.example.student.controller;

import com.example.student.dto.AuthResponse;
import com.example.student.dto.LoginRequest;
import com.example.student.model.Role;
import com.example.student.security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthController authController;

    @Test
    void login_ReturnsAuthResponse() {
        // 1. Подготовка
        LoginRequest request = new LoginRequest();
        request.setEmail("admin@example.com");
        request.setPassword("admin");

        UserDetails userDetails = new User(
                "admin@example.com",
                "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );

        // Мокаем AuthenticationManager
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        
        // Мокаем JwtService
        when(jwtService.generateToken(anyString(), anySet())).thenReturn("fake-jwt-token");

        // 2. Действие
        AuthResponse response = authController.login(request);

        // 3. Проверка
        assertNotNull(response);
        assertEquals("fake-jwt-token", response.getToken());
        assertEquals("Bearer", response.getType());
        assertTrue(response.getRoles().contains("ROLE_ADMIN"));
    }
}