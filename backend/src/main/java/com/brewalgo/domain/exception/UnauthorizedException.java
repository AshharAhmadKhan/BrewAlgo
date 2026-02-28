package com.brewalgo.domain.exception;

public class UnauthorizedException extends BusinessException {
    
    public UnauthorizedException(String message) {
        super(message, "UNAUTHORIZED", 401);
    }
    
    public UnauthorizedException() {
        super("Unauthorized access", "UNAUTHORIZED", 401);
    }
}
