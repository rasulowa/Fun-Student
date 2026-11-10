package com.example.student.security;

import com.example.student.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private final JwtService jwtService = new JwtService();

    @Test
    void generateToken_And_ExtractUsername_ShouldWork() {
        // 1. Генерируем токен
        String username = "admin@example.com";
        Set<Role> roles = Set.of(Role.ADMIN);
        String token = jwtService.generateToken(username, roles);
        
        // 2. Проверяем, что токен создался
        assertNotNull(token);
        
        // 3. Извлекаем имя пользователя из токена
        String extractedUsername = jwtService.extractUsername(token);
        assertEquals(username, extractedUsername);
    }

    @Test
    void validateToken_ShouldReturnTrueForValidToken() {
        String username = "teacher@example.com";
        Set<Role> roles = Set.of(Role.TEACHER);
        String token = jwtService.generateToken(username, roles);
        
        UserDetails userDetails = new User(username, "password", 
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_TEACHER")));
                
        assertTrue(jwtService.validateToken(token, userDetails));
    }
}