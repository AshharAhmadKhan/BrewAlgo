package com.brewalgo.domain.exception;

public class ResourceNotFoundException extends BusinessException {
    
    public ResourceNotFoundException(String resource, Object id) {
        super(
            String.format("%s with id %s not found", resource, id),
            "RESOURCE_NOT_FOUND",
            404
        );
    }
    
    public ResourceNotFoundException(String message) {
        super(message, "RESOURCE_NOT_FOUND", 404);
    }
}
