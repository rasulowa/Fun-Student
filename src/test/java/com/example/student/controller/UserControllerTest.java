package com.example.student.controller;

import com.example.student.dto.user.UserRequest;
import com.example.student.dto.user.UserResponse;
import com.example.student.dto.user.UserResponseList;
import com.example.student.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    void getAllUsers_ReturnsList() {
        UserResponseList responseList = new UserResponseList(List.of(), 0);
        when(userService.findAll()).thenReturn(responseList);

        UserResponseList result = userController.getAllUsers();

        assertNotNull(result);
        verify(userService, times(1)).findAll();
    }

    @Test
    void saveUser_ReturnsSavedUser() {
        UserRequest request = new UserRequest();
        UserResponse response = new UserResponse();
        response.setEmail("test@test.com");
        
        when(userService.save(any(UserRequest.class))).thenReturn(response);

        UserResponse result = userController.save(request);

        assertNotNull(result);
        assertEquals("test@test.com", result.getEmail());
        verify(userService, times(1)).save(any(UserRequest.class));
    }

    @Test
    void deleteUser_CallsService() {
        userController.deleteById(1L);
        verify(userService, times(1)).deleteById(1L);
    }
}