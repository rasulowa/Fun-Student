package com.example.student.service;

import com.example.student.dto.StudentRequest;
import com.example.student.dto.StudentResponse;
import com.example.student.dto.StudentUpdateRequest;
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

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentMapper studentMapper;

    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void save_ShouldReturnSavedStudent() {
        StudentRequest request = new StudentRequest("Ivan", "Ivanov", "+7999", "ivan@test.com");
        Student student = new Student();
        student.setId(1L);
        StudentResponse response = new StudentResponse(1L, "Ivan", "Ivanov");

        when(studentMapper.requestToStudent(request)).thenReturn(student);
        when(studentRepository.save(student)).thenReturn(student);
        when(studentMapper.studentToResponse(student)).thenReturn(response);

        StudentResponse result = studentService.save(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Ivan", result.getName());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void findById_WhenStudentExists_ReturnsStudent() {
        Long studentId = 1L;
        Student student = new Student();
        student.setId(studentId);
        student.setName("Ivan");

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        
        assertDoesNotThrow(() -> studentService.findById(studentId));
        verify(studentRepository, times(1)).findById(studentId);
    }

    @Test
    void findById_WhenStudentDoesNotExist_ThrowsException() {
        Long studentId = 99L;
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            studentService.findById(studentId);
        });

        assertTrue(exception.getMessage().contains("Student with ID 99 not found!"));
    }

    @Test
    void findAll_ShouldReturnList() {
        Student s1 = new Student(); s1.setId(1L); s1.setName("Ivan");
        Student s2 = new Student(); s2.setId(2L); s2.setName("Petr");

        when(studentRepository.findAll()).thenReturn(List.of(s1, s2));

        assertDoesNotThrow(() -> studentService.findAll());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void update_WhenStudentExists_ShouldUpdateFields() {
        Long id = 1L;
        Student existingStudent = new Student();
        existingStudent.setId(id);
        existingStudent.setEmail("old@test.com");
        
        StudentUpdateRequest updateRequest = new StudentUpdateRequest("+79990000000", "new@test.com");

        when(studentRepository.findById(id)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(existingStudent)).thenReturn(existingStudent);

        studentService.update(id, updateRequest);

        assertEquals("new@test.com", existingStudent.getEmail());
        assertEquals("+79990000000", existingStudent.getPhoneNumber());
        verify(studentRepository, times(1)).save(existingStudent);
    }

    @Test
    void update_WhenStudentDoesNotExist_ThrowsException() {
        Long id = 99L;
        StudentUpdateRequest updateRequest = new StudentUpdateRequest("+7999", "new@test.com");

        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> studentService.update(id, updateRequest));
        verify(studentRepository, never()).save(any(Student.class));
    }

    @Test
    void deleteById_ShouldCallRepository() {
        Long id = 1L;
        studentService.deleteById(id);
        verify(studentRepository, times(1)).deleteById(id);
    }
}