package com.brewalgo.exception;

import com.brewalgo.domain.exception.BusinessException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BusinessExceptionTest {
    
    @Test
    void notFound_WithId() {
        // When
        BusinessException exception = BusinessException.notFound("User", 123L);
        
        // Then
        assertEquals(404, exception.getHttpStatus());
        assertEquals("NOT_FOUND", exception.getErrorCode());
        assertTrue(exception.getMessage().contains("User"));
        assertTrue(exception.getMessage().contains("123"));
    }
    
    @Test
    void notFound_WithString() {
        // When
        BusinessException exception = BusinessException.notFound("Problem", "two-sum");
        
        // Then
        assertEquals(404, exception.getHttpStatus());
        assertEquals("NOT_FOUND", exception.getErrorCode());
        assertTrue(exception.getMessage().contains("Problem"));
        assertTrue(exception.getMessage().contains("two-sum"));
    }
    
    @Test
    void badRequest() {
        // When
        BusinessException exception = BusinessException.badRequest("Invalid input");
        
        // Then
        assertEquals(400, exception.getHttpStatus());
        assertEquals("BAD_REQUEST", exception.getErrorCode());
        assertEquals("Invalid input", exception.getMessage());
    }
    
    @Test
    void unauthorized() {
        // When
        BusinessException exception = BusinessException.unauthorized("Not authenticated");
        
        // Then
        assertEquals(401, exception.getHttpStatus());
        assertEquals("UNAUTHORIZED", exception.getErrorCode());
        assertEquals("Not authenticated", exception.getMessage());
    }
    
    @Test
    void forbidden() {
        // When
        BusinessException exception = BusinessException.forbidden("Access denied");
        
        // Then
        assertEquals(403, exception.getHttpStatus());
        assertEquals("FORBIDDEN", exception.getErrorCode());
        assertEquals("Access denied", exception.getMessage());
    }
    
    @Test
    void customException() {
        // When
        BusinessException exception = new BusinessException("Custom error", "CUSTOM_001", 500);
        
        // Then
        assertEquals(500, exception.getHttpStatus());
        assertEquals("CUSTOM_001", exception.getErrorCode());
        assertEquals("Custom error", exception.getMessage());
    }
}
