package com.brewalgo.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProblemResponseDTO {
    private Long id;
    private String slug;
    private String title;
    private String description;
    private String difficulty;
    private Integer baseScore;
    private Integer acceptanceRate;
    private Integer totalSubmissions;
    private String hints;
    private String tags;
    private LocalDateTime createdAt;
    private Boolean isSolved; // For authenticated users
    private Integer userAttempts; // For authenticated users
}
