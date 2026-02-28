package com.brewalgo.presentation.controller;

import com.brewalgo.application.dto.ExecutionResult;
import com.brewalgo.application.dto.SubmissionDTO;
import com.brewalgo.application.dto.SubmissionRequest;
import com.brewalgo.application.dto.UserDTO;
import com.brewalgo.application.service.CodeExecutionService;
import com.brewalgo.application.service.SubmissionService;
import com.brewalgo.application.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
@Slf4j
public class SubmissionController {
    
    private final SubmissionService submissionService;
    private final CodeExecutionService codeExecutionService;
    private final UserService userService;
    
    @PostMapping
    public ResponseEntity<Map<String, Object>> submitSolution(@Valid @RequestBody SubmissionRequest request) {
        // Extract userId from authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserDTO user = userService.getUserByUsername(username);
        Long userId = user.getId();
        
        log.info("POST /api/submissions - userId: {}, problemId: {}", userId, request.getProblemId());
        
        // Create submission with PENDING status
        SubmissionDTO created = submissionService.submitSolution(
            userId, 
            request.getProblemId(), 
            request.getCode(), 
            request.getLanguage()
        );
        
        // Execute code
        ExecutionResult result = codeExecutionService.executeCode(
            request.getProblemId(), 
            request.getCode(), 
            request.getLanguage()
        );
        
        // Update submission with result
        submissionService.updateSubmissionStatus(
            created.getId(), 
            result.getStatus(), 
            result.getExecutionTimeMs().intValue(), 
            result.getMemoryUsedKb().intValue(), 
            result.getErrorMessage()
        );
        
        // Get updated submission
        SubmissionDTO updated = submissionService.getSubmissionById(created.getId());
        
        // Return result with execution details
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
            "submission", updated,
            "executionResult", result
        ));
    }
    
    @PostMapping("/contest")
    public ResponseEntity<Map<String, Object>> submitContestSolution(@Valid @RequestBody SubmissionRequest request) {
        // Extract userId from authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        UserDTO user = userService.getUserByUsername(username);
        Long userId = user.getId();
        
        log.info("POST /api/submissions/contest - userId: {}, problemId: {}, contestId: {}", 
            userId, request.getProblemId(), request.getContestId());
        
        if (request.getContestId() == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Contest ID is required"));
        }
        
        SubmissionDTO created = submissionService.submitContestSolution(
            userId, 
            request.getProblemId(), 
            request.getContestId(), 
            request.getCode(), 
            request.getLanguage()
        );
        
        // Execute code
        ExecutionResult result = codeExecutionService.executeCode(
            request.getProblemId(), 
            request.getCode(), 
            request.getLanguage()
        );
        
        // Update submission with result
        submissionService.updateSubmissionStatus(
            created.getId(), 
            result.getStatus(), 
            result.getExecutionTimeMs().intValue(), 
            result.getMemoryUsedKb().intValue(), 
            result.getErrorMessage()
        );
        
        SubmissionDTO updated = submissionService.getSubmissionById(created.getId());
        
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
            "submission", updated,
            "executionResult", result
        ));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SubmissionDTO> getSubmissionById(@PathVariable Long id) {
        log.info("GET /api/submissions/{}", id);
        SubmissionDTO submission = submissionService.getSubmissionById(id);
        return ResponseEntity.ok(submission);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<Map<String, Object>> getSubmissionsByUser(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        log.info("GET /api/submissions/user/{}?page={}&size={}", userId, page, size);
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("submittedAt").descending());
        Page<SubmissionDTO> submissionPage = submissionService.getSubmissionsByUserPageable(userId, pageable);
        
        Map<String, Object> response = new HashMap<>();
        response.put("submissions", submissionPage.getContent());
        response.put("currentPage", submissionPage.getNumber());
        response.put("totalItems", submissionPage.getTotalElements());
        response.put("totalPages", submissionPage.getTotalPages());
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/problem/{problemId}")
    public ResponseEntity<List<SubmissionDTO>> getSubmissionsByProblem(@PathVariable Long problemId) {
        log.info("GET /api/submissions/problem/{}", problemId);
        List<SubmissionDTO> submissions = submissionService.getSubmissionsByProblem(problemId);
        return ResponseEntity.ok(submissions);
    }
    
    @GetMapping("/contest/{contestId}")
    public ResponseEntity<List<SubmissionDTO>> getSubmissionsByContest(@PathVariable Long contestId) {
        log.info("GET /api/submissions/contest/{}", contestId);
        List<SubmissionDTO> submissions = submissionService.getSubmissionsByContest(contestId);
        return ResponseEntity.ok(submissions);
    }
    
    @GetMapping("/user/{userId}/problem/{problemId}")
    public ResponseEntity<List<SubmissionDTO>> getUserProblemSubmissions(
            @PathVariable Long userId,
            @PathVariable Long problemId) {
        log.info("GET /api/submissions/user/{}/problem/{}", userId, problemId);
        List<SubmissionDTO> submissions = submissionService.getUserProblemSubmissions(userId, problemId);
        return ResponseEntity.ok(submissions);
    }
    
    @GetMapping("/user/{userId}/accepted")
    public ResponseEntity<List<SubmissionDTO>> getAcceptedSubmissions(@PathVariable Long userId) {
        log.info("GET /api/submissions/user/{}/accepted", userId);
        List<SubmissionDTO> submissions = submissionService.getAcceptedSubmissionsByUser(userId);
        return ResponseEntity.ok(submissions);
    }
    
    @GetMapping("/user/{userId}/problem/{problemId}/solved")
    public ResponseEntity<Map<String, Boolean>> checkIfSolved(
            @PathVariable Long userId,
            @PathVariable Long problemId) {
        log.info("GET /api/submissions/user/{}/problem/{}/solved", userId, problemId);
        boolean solved = submissionService.hasUserSolvedProblem(userId, problemId);
        return ResponseEntity.ok(Map.of("solved", solved));
    }
}