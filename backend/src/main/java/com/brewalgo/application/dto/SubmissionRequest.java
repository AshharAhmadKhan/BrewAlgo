package com.brewalgo.application.dto;

import com.brewalgo.infrastructure.validation.ValidLanguage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SubmissionRequest {
    
    @NotNull(message = "Problem ID is required")
    private Long problemId;
    
    @NotBlank(message = "Code cannot be empty")
    @Size(max = 50000, message = "Code too long (max 50KB)")
    private String code;
    
    @NotBlank(message = "Language is required")
    @ValidLanguage
    private String language;
    
    private Long contestId; // Optional for contest submissions
}
