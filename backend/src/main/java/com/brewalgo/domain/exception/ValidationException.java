package com.brewalgo.domain.exception;

public class ValidationException extends BusinessException {
    
    public ValidationException(String message) {
        super(message, "VALIDATION_ERROR", 400);
    }
    
    public ValidationException(String field, String message) {
        super(String.format("%s: %s", field, message), "VALIDATION_ERROR", 400);
    }
}
