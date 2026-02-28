package com.brewalgo.domain.exception;

public class RateLimitExceededException extends BusinessException {
    
    private final long retryAfterSeconds;
    
    public RateLimitExceededException(long retryAfterSeconds) {
        super(
            "Rate limit exceeded. Please try again in " + retryAfterSeconds + " seconds.",
            "RATE_LIMIT_EXCEEDED",
            429
        );
        this.retryAfterSeconds = retryAfterSeconds;
    }
    
    public long getRetryAfterSeconds() {
        return retryAfterSeconds;
    }
}
