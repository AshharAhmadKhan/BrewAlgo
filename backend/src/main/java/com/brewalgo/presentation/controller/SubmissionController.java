package com.brewalgo.presentation.controller;

import com.brewalgo.application.dto.SubmissionDTO;
import com.brewalgo.application.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/submissions")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class SubmissionController {
    
    private final SubmissionService submissionService;
    
    @PostMapping
    public ResponseEntity<SubmissionDTO> submitSolution(@RequestBody Map<String, Object> submission) {
        log.info("POST /api/submissions - userId: {}, problemId: {}", 
            submission.get("userId"), submission.get("problemId"));
        
        Long userId = Long.valueOf(submission.get("userId").toString());
        Long problemId = Long.valueOf(submission.get("problemId").toString());
        String code = submission.get("code").toString();
        String language = submission.get("language").toString();
        
        SubmissionDTO created = submissionService.submitSolution(userId, problemId, code, language);
        
        // Trigger evaluation asynchronously (simulate)
        // In production, this would be a background job
        try {
            SubmissionDTO evaluated = submissionService.evaluateSubmission(created.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(evaluated);
        } catch (Exception e) {
            log.error("Error evaluating submission: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        }
    }
    
    @PostMapping("/contest")
    public ResponseEntity<SubmissionDTO> submitContestSolution(@RequestBody Map<String, Object> submission) {
        log.info("POST /api/submissions/contest - userId: {}, problemId: {}, contestId: {}", 
            submission.get("userId"), submission.get("problemId"), submission.get("contestId"));
        
        Long userId = Long.valueOf(submission.get("userId").toString());
        Long problemId = Long.valueOf(submission.get("problemId").toString());
        Long contestId = Long.valueOf(submission.get("contestId").toString());
        String code = submission.get("code").toString();
        String language = submission.get("language").toString();
        
        SubmissionDTO created = submissionService.submitContestSolution(userId, problemId, contestId, code, language);
        
        // Trigger evaluation
        try {
            SubmissionDTO evaluated = submissionService.evaluateSubmission(created.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(evaluated);
        } catch (Exception e) {
            log.error("Error evaluating contest submission: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SubmissionDTO> getSubmissionById(@PathVariable Long id) {
        log.info("GET /api/submissions/{}", id);
        SubmissionDTO submission = submissionService.getSubmissionById(id);
        return ResponseEntity.ok(submission);
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<SubmissionDTO>> getSubmissionsByUser(@PathVariable Long userId) {
        log.info("GET /api/submissions/user/{}", userId);
        List<SubmissionDTO> submissions = submissionService.getSubmissionsByUser(userId);
        return ResponseEntity.ok(submissions);
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
    
    @PostMapping("/{id}/evaluate")
    public ResponseEntity<SubmissionDTO> evaluateSubmission(@PathVariable Long id) {
        log.info("POST /api/submissions/{}/evaluate", id);
        SubmissionDTO evaluated = submissionService.evaluateSubmission(id);
        return ResponseEntity.ok(evaluated);
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