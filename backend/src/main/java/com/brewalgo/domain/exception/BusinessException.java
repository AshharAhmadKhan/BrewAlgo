package com.brewalgo.domain.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    
    private final String errorCode;
    private final int httpStatus;
    
    public BusinessException(String message, String errorCode, int httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
    
    public static BusinessException notFound(String resource, Object id) {
        return new BusinessException(
            String.format("%s with id %s not found", resource, id),
            "NOT_FOUND",
            404
        );
    }
    
    public static BusinessException badRequest(String message) {
        return new BusinessException(message, "BAD_REQUEST", 400);
    }
    
    public static BusinessException unauthorized(String message) {
        return new BusinessException(message, "UNAUTHORIZED", 401);
    }
    
    public static BusinessException forbidden(String message) {
        return new BusinessException(message, "FORBIDDEN", 403);
    }
}