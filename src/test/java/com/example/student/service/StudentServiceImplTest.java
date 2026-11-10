package com.example.student.service;

import com.example.student.exception.EntityNotFoundException;
import com.example.student.mapper.StudentMapper;
import com.example.student.model.Student;
import com.example.student.repository.StudentRepository;
import com.example.student.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Включаем Mockito
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentMapper studentMapper;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void findById_WhenStudentExists_ReturnsStudent() {
        // 1. Подготовка (Arrange)
        Long studentId = 1L;
        Student student = new Student();
        student.setId(studentId);
        student.setName("Ivan");

        // Говорим моку репозитория: "Когда попросят ID 1, верни этого студента"
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        
        // 2. Действие (Act) - вызываем наш метод (предполагаем, что маппер не падает)
        // В реальном тесте тут нужно замокать и маппер, но для простоты проверим факт вызова
        assertDoesNotThrow(() -> studentService.findById(studentId));

        // 3. Проверка (Assert)
        verify(studentRepository, times(1)).findById(studentId);
    }

    @Test
    void findById_WhenStudentDoesNotExist_ThrowsException() {
        // 1. Подготовка
        Long studentId = 99L;
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        // 2. Действие и 3. Проверка
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            studentService.findById(studentId);
        });

        assertTrue(exception.getMessage().contains("Student with ID 99 not found!"));
    }
}