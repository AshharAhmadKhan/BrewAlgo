package com.brewalgo.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionResult {
    private String status; // ACCEPTED, WRONG_ANSWER, TIME_LIMIT_EXCEEDED, etc.
    private String output;
    private String errorMessage;
    private Long executionTimeMs;
    private Long memoryUsedKb;
    private Integer passedTestCases;
    private Integer totalTestCases;
}