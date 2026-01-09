package com.brewalgo.presentation.controller;

import com.brewalgo.application.dto.ProblemDTO;
import com.brewalgo.application.service.ProblemService;
import com.brewalgo.domain.entity.Problem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/problems")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class ProblemController {
    
    private final ProblemService problemService;
    
    @PostMapping
    public ResponseEntity<ProblemDTO> createProblem(@RequestBody ProblemDTO problemDTO) {
        log.info("POST /api/problems - title: {}", problemDTO.getTitle());
        ProblemDTO created = problemService.createProblem(problemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @GetMapping
    public ResponseEntity<List<ProblemDTO>> getAllProblems() {
        log.info("GET /api/problems");
        List<ProblemDTO> problems = problemService.getAllProblems();
        return ResponseEntity.ok(problems);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ProblemDTO> getProblemById(@PathVariable Long id) {
        log.info("GET /api/problems/{}", id);
        ProblemDTO problem = problemService.getProblemById(id);
        return ResponseEntity.ok(problem);
    }
    
    @GetMapping("/slug/{slug}")
    public ResponseEntity<ProblemDTO> getProblemBySlug(@PathVariable String slug) {
        log.info("GET /api/problems/slug/{}", slug);
        ProblemDTO problem = problemService.getProblemBySlug(slug);
        return ResponseEntity.ok(problem);
    }
    
    @GetMapping("/difficulty/{difficulty}")
    public ResponseEntity<List<ProblemDTO>> getProblemsByDifficulty(@PathVariable String difficulty) {
        log.info("GET /api/problems/difficulty/{}", difficulty);
        Problem.Difficulty diff = Problem.Difficulty.valueOf(difficulty.toUpperCase());
        List<ProblemDTO> problems = problemService.getProblemsByDifficulty(diff);
        return ResponseEntity.ok(problems);
    }
    
    @GetMapping("/tag/{tag}")
    public ResponseEntity<List<ProblemDTO>> getProblemsByTag(@PathVariable String tag) {
        log.info("GET /api/problems/tag/{}", tag);
        List<ProblemDTO> problems = problemService.getProblemsByTag(tag);
        return ResponseEntity.ok(problems);
    }
    
    @GetMapping("/most-attempted")
    public ResponseEntity<List<ProblemDTO>> getMostAttemptedProblems(
            @RequestParam(defaultValue = "10") int limit) {
        log.info("GET /api/problems/most-attempted?limit={}", limit);
        List<ProblemDTO> problems = problemService.getMostAttemptedProblems(limit);
        return ResponseEntity.ok(problems);
    }
    
    @GetMapping("/recommended")
    public ResponseEntity<List<ProblemDTO>> getRecommendedProblems(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "10") int limit) {
        log.info("GET /api/problems/recommended?userId={}&limit={}", userId, limit);
        List<ProblemDTO> problems = problemService.getRecommendedProblems(userId, limit);
        return ResponseEntity.ok(problems);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ProblemDTO> updateProblem(
            @PathVariable Long id,
            @RequestBody ProblemDTO problemDTO) {
        log.info("PUT /api/problems/{}", id);
        ProblemDTO updated = problemService.updateProblem(id, problemDTO);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProblem(@PathVariable Long id) {
        log.info("DELETE /api/problems/{}", id);
        problemService.deleteProblem(id);
        return ResponseEntity.noContent().build();
    }
}