package com.brewalgo.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "problems")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Problem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String slug;
    
    @Column(nullable = false)
    private String title;
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;
    
    @Column(nullable = false)
    private Integer baseScore = 100;
    
    @Column(nullable = false)
    private Integer acceptanceRate = 0;
    
    @Column(nullable = false)
    private Integer totalSubmissions = 0;
    
    @Column(nullable = false)
    private Integer successfulSubmissions = 0;
    
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(columnDefinition = "TEXT")
    private String hints;
    
    @Column(columnDefinition = "TEXT")
    private String tags;
    
    public enum Difficulty {
        EASY, MEDIUM, HARD
    }
}