package com.example.student.service;

import com.example.student.dto.user.UserRequest;
import com.example.student.dto.user.UserResponse;
import com.example.student.mapper.UserMapperImpl;
import com.example.student.model.Role;
import com.example.student.model.User;
import com.example.student.repository.UserRepository;
import com.example.student.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExtraServicesTest {

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private PasswordEncoder passwordEncoder;

    @Spy
    private UserMapperImpl userMapper = new UserMapperImpl();

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void userService_findAll_ReturnsList() {
        User user = new User();
        user.setId(1L); // ВАЖНО: задаем ID, иначе маппер упадет с NullPointerException
        user.setEmail("test@test.com");
        when(userRepository.findAll()).thenReturn(List.of(user));

        var result = userService.findAll();

        assertNotNull(result);
        assertEquals(1, result.getTotalCount());
    }

    @Test
    void userService_updateUser_UpdatesEmailAndRoles() {
        UserRequest request = new UserRequest("new@test.com", "pass", Set.of(Role.ADMIN));
        User existingUser = new User();
        existingUser.setId(2L);
        existingUser.setEmail("old@test.com");
        
        when(userRepository.findByEmail("new@test.com")).thenReturn(Optional.of(existingUser));

        // Метод updateUser в вашем коде возвращает null, поэтому просто проверяем, что он не падает
        assertDoesNotThrow(() -> userService.updateUser(request));
        assertEquals("new@test.com", existingUser.getEmail());
    }
    
    @Test
    void userService_register_ReturnsTrueWhenSuccess() {
        UserRequest request = new UserRequest("reg@test.com", "pass", Set.of(Role.TEACHER));
        
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded");
        
        boolean result = userService.register(request);
        
        assertTrue(result);
        verify(userRepository, times(1)).save(any(User.class));
    }
    
    @Test
    void userService_register_ReturnsFalseWhenEmailExists() {
        UserRequest request = new UserRequest("reg@test.com", "pass", Set.of(Role.TEACHER));
        
        when(userRepository.existsByEmail(anyString())).thenReturn(true);
        
        boolean result = userService.register(request);
        
        assertFalse(result);
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void userMapper_ToEntityAndToResponse_Works() {
        UserRequest request = new UserRequest("test@test.com", "pass", Set.of(Role.ADMIN));
        
        // Тестируем toEntity
        User user = userMapper.toEntity(request);
        assertNotNull(user);
        assertEquals("test@test.com", user.getEmail());
        
        // ВАЖНО: Задаем ID перед конвертацией в Response
        user.setId(3L);

        // Тестируем toResponse
        UserResponse response = userMapper.toResponse(user);
        assertNotNull(response);
        assertEquals("test@test.com", response.getEmail());
        assertEquals(3L, response.getId());
    }
}