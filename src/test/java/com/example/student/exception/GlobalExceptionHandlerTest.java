package com.example.student.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void catchEntityNotFoundException_ShouldReturnNotFoundStatus() {
        // Создаем ошибку
        EntityNotFoundException exception = new EntityNotFoundException("Student not found");
        
        // Вызываем обработчик
        ResponseEntity<AppError> response = handler.catchEntityNotFoundException(exception);
        
        // Проверяем
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(404, response.getBody().getStatusCode());
        assertEquals("Student not found", response.getBody().getMessage());
    }
}