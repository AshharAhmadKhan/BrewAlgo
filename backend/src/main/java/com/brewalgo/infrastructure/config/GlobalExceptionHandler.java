package com.brewalgo.infrastructure.config;

import com.brewalgo.application.dto.ApiResponse;
import com.brewalgo.domain.exception.BusinessException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException ex) {
        String requestId = MDC.get("requestId");
        log.error("Business exception: {} - RequestId: {}", ex.getMessage(), requestId);
        
        ApiResponse<Void> response = ApiResponse.error(
            ex.getMessage(),
            ex.getErrorCode(),
            requestId
        );
        
        return ResponseEntity
            .status(ex.getHttpStatus())
            .body(response);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(
            MethodArgumentNotValidException ex) {
        String requestId = MDC.get("requestId");
        log.error("Validation exception - RequestId: {}", requestId);
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        ApiResponse<Map<String, String>> response = ApiResponse.<Map<String, String>>builder()
            .success(false)
            .message("Validation failed")
            .data(errors)
            .errorCode("VALIDATION_ERROR")
            .requestId(requestId)
            .build();
        
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(response);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConstraintViolation(ConstraintViolationException ex) {
        String requestId = MDC.get("requestId");
        log.error("Constraint violation: {} - RequestId: {}", ex.getMessage(), requestId);
        
        ApiResponse<Void> response = ApiResponse.error(
            ex.getMessage(),
            "CONSTRAINT_VIOLATION",
            requestId
        );
        
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(response);
    }
    
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadCredentials(BadCredentialsException ex) {
        String requestId = MDC.get("requestId");
        log.error("Bad credentials - RequestId: {}", requestId);
        
        ApiResponse<Void> response = ApiResponse.error(
            "Invalid username or password",
            "INVALID_CREDENTIALS",
            requestId
        );
        
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(response);
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDenied(AccessDeniedException ex) {
        String requestId = MDC.get("requestId");
        log.error("Access denied: {} - RequestId: {}", ex.getMessage(), requestId);
        
        ApiResponse<Void> response = ApiResponse.error(
            "Access denied",
            "FORBIDDEN",
            requestId
        );
        
        return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(response);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        String requestId = MDC.get("requestId");
        log.error("Unexpected error - RequestId: {}", requestId, ex);
        
        ApiResponse<Void> response = ApiResponse.error(
            "An unexpected error occurred",
            "INTERNAL_SERVER_ERROR",
            requestId
        );
        
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(response);
    }
}
