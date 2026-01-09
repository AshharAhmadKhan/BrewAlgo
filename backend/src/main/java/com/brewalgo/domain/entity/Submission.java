package com.brewalgo.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "submissions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Submission {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contest_id")
    private Contest contest;
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String code;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Language language;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;
    
    @Column
    private Integer executionTimeMs;
    
    @Column
    private Integer memoryUsedKb;
    
    @Column
    private Integer scoreAwarded = 0;
    
    @Column(nullable = false)
    private LocalDateTime submittedAt = LocalDateTime.now();
    
    @Column(columnDefinition = "TEXT")
    private String errorMessage;
    
    public enum Language {
        JAVA, PYTHON, JAVASCRIPT, CPP
    }
    
    public enum Status {
        PENDING, ACCEPTED, WRONG_ANSWER, TIME_LIMIT_EXCEEDED, 
        MEMORY_LIMIT_EXCEEDED, RUNTIME_ERROR, COMPILATION_ERROR
    }
}