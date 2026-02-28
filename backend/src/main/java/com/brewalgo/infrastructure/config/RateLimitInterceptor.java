package com.brewalgo.infrastructure.config;

import com.brewalgo.application.dto.ApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class RateLimitInterceptor implements HandlerInterceptor {
    
    private final RateLimitConfig rateLimitConfig;
    private final ObjectMapper objectMapper;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
            throws Exception {
        
        String key = getClientKey(request);
        String path = request.getRequestURI();
        
        Bucket bucket;
        if (path.contains("/submissions")) {
            bucket = rateLimitConfig.resolveSubmissionBucket(key);
        } else {
            bucket = rateLimitConfig.resolveBucket(key);
        }
        
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        
        if (probe.isConsumed()) {
            response.addHeader("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));
            return true;
        } else {
            long waitForRefill = probe.getNanosToWaitForRefill() / 1_000_000_000;
            
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.addHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(waitForRefill));
            
            ApiResponse<Void> apiResponse = ApiResponse.error(
                "Rate limit exceeded. Please try again in " + waitForRefill + " seconds.",
                "RATE_LIMIT_EXCEEDED"
            );
            
            response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
            
            log.warn("Rate limit exceeded for client: {} on path: {}", key, path);
            return false;
        }
    }
    
    private String getClientKey(HttpServletRequest request) {
        // Use IP address as key (in production, consider using user ID if authenticated)
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
