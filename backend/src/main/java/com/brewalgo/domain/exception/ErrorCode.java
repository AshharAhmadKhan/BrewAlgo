package com.brewalgo.domain.exception;

public enum ErrorCode {
    // General
    INTERNAL_SERVER_ERROR("ERR_001", "Internal server error"),
    VALIDATION_ERROR("ERR_002", "Validation error"),
    RESOURCE_NOT_FOUND("ERR_003", "Resource not found"),
    
    // Authentication & Authorization
    UNAUTHORIZED("AUTH_001", "Unauthorized access"),
    INVALID_CREDENTIALS("AUTH_002", "Invalid username or password"),
    TOKEN_EXPIRED("AUTH_003", "Authentication token expired"),
    INSUFFICIENT_PERMISSIONS("AUTH_004", "Insufficient permissions"),
    
    // User
    USER_NOT_FOUND("USER_001", "User not found"),
    USERNAME_ALREADY_EXISTS("USER_002", "Username already exists"),
    EMAIL_ALREADY_EXISTS("USER_003", "Email already exists"),
    
    // Problem
    PROBLEM_NOT_FOUND("PROB_001", "Problem not found"),
    PROBLEM_SLUG_EXISTS("PROB_002", "Problem slug already exists"),
    INVALID_DIFFICULTY("PROB_003", "Invalid difficulty level"),
    
    // Submission
    SUBMISSION_NOT_FOUND("SUB_001", "Submission not found"),
    INVALID_LANGUAGE("SUB_002", "Invalid programming language"),
    CODE_EXECUTION_FAILED("SUB_003", "Code execution failed"),
    COMPILATION_ERROR("SUB_004", "Compilation error"),
    
    // Contest
    CONTEST_NOT_FOUND("CONT_001", "Contest not found"),
    CONTEST_NOT_STARTED("CONT_002", "Contest has not started yet"),
    CONTEST_ENDED("CONT_003", "Contest has ended"),
    
    // Rate Limiting
    RATE_LIMIT_EXCEEDED("RATE_001", "Rate limit exceeded");
    
    private final String code;
    private final String message;
    
    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getMessage() {
        return message;
    }
}
