package com.example.student.controller;

import com.example.student.dto.*;
import com.example.student.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @Test
    void findAll_ReturnsList() {
        // ИСПРАВЛЕНО: передаем пустой список и 0, так как нет пустого конструктора
        StudentResponseList list = new StudentResponseList(Collections.emptyList(), 0);
        when(studentService.findAll()).thenReturn(list);

        StudentResponseList result = studentController.findAll();

        assertNotNull(result);
        verify(studentService, times(1)).findAll();
    }

    @Test
    void findStudentById_ReturnsStudent() {
        StudentFullResponse response = new StudentFullResponse();
        response.setId(1L);
        when(studentService.findById(1L)).thenReturn(response);

        StudentFullResponse result = studentController.findStudentById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void createStudent_ReturnsCreated() {
        StudentRequest request = new StudentRequest();
        StudentResponse response = new StudentResponse();
        when(studentService.save(any(StudentRequest.class))).thenReturn(response);

        StudentResponse result = studentController.createStudent(request);

        assertNotNull(result);
        verify(studentService, times(1)).save(any(StudentRequest.class));
    }

    @Test
    void deleteStudentById_CallsService() {
        studentController.deleteStudentById(1L);
        verify(studentService, times(1)).deleteById(1L);
    }
}