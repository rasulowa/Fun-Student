package com.example.student.mapper;

import com.example.student.dto.StudentFullResponse;
import com.example.student.dto.StudentRequest;
import com.example.student.dto.StudentResponse;
import com.example.student.model.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentMapperTest {

    private final StudentMapperImpl mapper = new StudentMapperImpl();

    @Test
    void requestToStudent_ShouldMapCorrectly() {
        StudentRequest request = new StudentRequest("Ivan", "Ivanov", "+7999", "ivan@test.com");
        Student student = mapper.requestToStudent(request);
        
        assertNotNull(student);
        assertEquals("Ivan", student.getName());
        assertEquals("Ivanov", student.getSurname());
        assertEquals("+7999", student.getPhoneNumber());
        assertEquals("ivan@test.com", student.getEmail());
    }

    @Test
    void studentToFullResponse_ShouldMapCorrectly() {
        Student student = new Student();
        student.setId(1L);
        student.setName("Petr");
        student.setSurname("Petrov");
        
        StudentFullResponse response = mapper.studentToFullResponse(student);
        
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Petr", response.getName());
    }

    @Test
    void studentToResponse_ShouldMapCorrectly() {
        Student student = new Student();
        student.setId(2L);
        student.setName("Anna");
        
        StudentResponse response = mapper.studentToResponse(student);
        
        assertNotNull(response);
        assertEquals(2L, response.getId());
        assertEquals("Anna", response.getName());
    }
}