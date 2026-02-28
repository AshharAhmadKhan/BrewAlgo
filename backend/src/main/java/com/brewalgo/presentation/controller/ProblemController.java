package com.brewalgo.presentation.controller;

import com.brewalgo.application.dto.ProblemDTO;
import com.brewalgo.application.service.ProblemService;
import com.brewalgo.domain.entity.Problem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/problems")
@RequiredArgsConstructor
@Slf4j
public class ProblemController {
    
    private final ProblemService problemService;
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProblemDTO> createProblem(@RequestBody ProblemDTO problemDTO) {
        log.info("POST /api/problems - title: {}", problemDTO.getTitle());
        ProblemDTO created = problemService.createProblem(problemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllProblems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        log.info("GET /api/problems?page={}&size={}&sortBy={}&sortDir={}", page, size, sortBy, sortDir);
        
        Sort sort = sortDir.equalsIgnoreCase("desc") 
            ? Sort.by(sortBy).descending() 
            : Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<ProblemDTO> problemPage = problemService.getAllProblemsPageable(pageable);
        
        Map<String, Object> response = new HashMap<>();
        response.put("problems", problemPage.getContent());
        response.put("currentPage", problemPage.getNumber());
        response.put("totalItems", problemPage.getTotalElements());
        response.put("totalPages", problemPage.getTotalPages());
        response.put("hasNext", problemPage.hasNext());
        response.put("hasPrevious", problemPage.hasPrevious());
        
        return ResponseEntity.ok(response);
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProblemDTO> updateProblem(
            @PathVariable Long id,
            @RequestBody ProblemDTO problemDTO) {
        log.info("PUT /api/problems/{}", id);
        ProblemDTO updated = problemService.updateProblem(id, problemDTO);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProblem(@PathVariable Long id) {
        log.info("DELETE /api/problems/{}", id);
        problemService.deleteProblem(id);
        return ResponseEntity.noContent().build();
    }
}