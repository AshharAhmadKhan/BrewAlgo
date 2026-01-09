package com.brewalgo.presentation.controller;

import com.brewalgo.application.dto.ContestDTO;
import com.brewalgo.application.dto.LeaderboardEntryDTO;
import com.brewalgo.application.service.ContestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contests")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class ContestController {
    
    private final ContestService contestService;
    
    @PostMapping
    public ResponseEntity<ContestDTO> createContest(@RequestBody ContestDTO contestDTO) {
        log.info("POST /api/contests - title: {}", contestDTO.getTitle());
        ContestDTO created = contestService.createContest(contestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @GetMapping
    public ResponseEntity<List<ContestDTO>> getAllContests() {
        log.info("GET /api/contests");
        List<ContestDTO> contests = contestService.getAllContests();
        return ResponseEntity.ok(contests);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<ContestDTO> getContestById(@PathVariable Long id) {
        log.info("GET /api/contests/{}", id);
        ContestDTO contest = contestService.getContestById(id);
        return ResponseEntity.ok(contest);
    }
    
    @GetMapping("/slug/{slug}")
    public ResponseEntity<ContestDTO> getContestBySlug(@PathVariable String slug) {
        log.info("GET /api/contests/slug/{}", slug);
        ContestDTO contest = contestService.getContestBySlug(slug);
        return ResponseEntity.ok(contest);
    }
    
    @GetMapping("/upcoming")
    public ResponseEntity<List<ContestDTO>> getUpcomingContests() {
        log.info("GET /api/contests/upcoming");
        List<ContestDTO> contests = contestService.getUpcomingContests();
        return ResponseEntity.ok(contests);
    }
    
    @GetMapping("/live")
    public ResponseEntity<List<ContestDTO>> getLiveContests() {
        log.info("GET /api/contests/live");
        List<ContestDTO> contests = contestService.getLiveContests();
        return ResponseEntity.ok(contests);
    }
    
    @GetMapping("/completed")
    public ResponseEntity<List<ContestDTO>> getCompletedContests() {
        log.info("GET /api/contests/completed");
        List<ContestDTO> contests = contestService.getCompletedContests();
        return ResponseEntity.ok(contests);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ContestDTO> updateContest(
            @PathVariable Long id,
            @RequestBody ContestDTO contestDTO) {
        log.info("PUT /api/contests/{}", id);
        ContestDTO updated = contestService.updateContest(id, contestDTO);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContest(@PathVariable Long id) {
        log.info("DELETE /api/contests/{}", id);
        contestService.deleteContest(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/{id}/start")
    public ResponseEntity<Void> startContest(@PathVariable Long id) {
        log.info("POST /api/contests/{}/start", id);
        contestService.startContest(id);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/{id}/end")
    public ResponseEntity<Void> endContest(@PathVariable Long id) {
        log.info("POST /api/contests/{}/end", id);
        contestService.endContest(id);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/{contestId}/join")
    public ResponseEntity<Map<String, Boolean>> joinContest(
            @PathVariable Long contestId,
            @RequestBody Map<String, Long> body) {
        log.info("POST /api/contests/{}/join - userId: {}", contestId, body.get("userId"));
        Long userId = body.get("userId");
        boolean joined = contestService.joinContest(contestId, userId);
        return ResponseEntity.ok(Map.of("joined", joined));
    }
    
    @GetMapping("/{id}/leaderboard")
    public ResponseEntity<List<LeaderboardEntryDTO>> getContestLeaderboard(@PathVariable Long id) {
        log.info("GET /api/contests/{}/leaderboard", id);
        List<LeaderboardEntryDTO> leaderboard = contestService.getContestLeaderboard(id);
        return ResponseEntity.ok(leaderboard);
    }
    
    @PostMapping("/{id}/update-status")
    public ResponseEntity<Void> updateContestStatus(@PathVariable Long id) {
        log.info("POST /api/contests/{}/update-status", id);
        contestService.updateContestStatus(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{id}/active")
    public ResponseEntity<Map<String, Boolean>> isContestActive(@PathVariable Long id) {
        log.info("GET /api/contests/{}/active", id);
        boolean active = contestService.isContestActive(id);
        return ResponseEntity.ok(Map.of("active", active));
    }
    
    @GetMapping("/{contestId}/can-join")
    public ResponseEntity<Map<String, Boolean>> canUserJoin(
            @PathVariable Long contestId,
            @RequestParam Long userId) {
        log.info("GET /api/contests/{}/can-join?userId={}", contestId, userId);
        boolean canJoin = contestService.canUserJoin(contestId, userId);
        return ResponseEntity.ok(Map.of("canJoin", canJoin));
    }
}