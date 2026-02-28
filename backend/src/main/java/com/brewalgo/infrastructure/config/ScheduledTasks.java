package com.brewalgo.infrastructure.config;

import com.brewalgo.domain.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTasks {
    
    private final AuditLogRepository auditLogRepository;
    
    /**
     * Clean up old audit logs (older than 90 days)
     * Runs daily at 2 AM
     */
    @Scheduled(cron = "0 0 2 * * *")
    @Transactional
    public void cleanupOldAuditLogs() {
        log.info("Starting cleanup of old audit logs");
        
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(90);
        var oldLogs = auditLogRepository.findByTimestampBetween(
            LocalDateTime.of(2000, 1, 1, 0, 0),
            cutoffDate
        );
        
        if (!oldLogs.isEmpty()) {
            auditLogRepository.deleteAll(oldLogs);
            log.info("Deleted {} old audit logs", oldLogs.size());
        } else {
            log.info("No old audit logs to delete");
        }
    }
    
    /**
     * Log system health metrics
     * Runs every hour
     */
    @Scheduled(fixedRate = 3600000) // 1 hour
    public void logSystemHealth() {
        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        
        log.info("System Health - Memory: {}MB used / {}MB total", 
            usedMemory / 1024 / 1024, 
            totalMemory / 1024 / 1024
        );
    }
}
