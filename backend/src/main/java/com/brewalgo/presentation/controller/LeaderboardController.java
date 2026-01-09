package com.brewalgo.presentation.controller;

import com.brewalgo.application.dto.LeaderboardEntryDTO;
import com.brewalgo.application.dto.UserDTO;
import com.brewalgo.application.service.ContestService;
import com.brewalgo.application.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/leaderboard")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class LeaderboardController {
    
    private final UserService userService;
    private final ContestService contestService;
    
    @GetMapping("/global")
    public ResponseEntity<List<LeaderboardEntryDTO>> getGlobalLeaderboard(
            @RequestParam(defaultValue = "50") int limit) {
        log.info("GET /api/leaderboard/global?limit={}", limit);
        
        List<UserDTO> topUsers = userService.getTopUsersByRating(limit);
        
        List<LeaderboardEntryDTO> leaderboard = topUsers.stream()
            .map((user) -> {
                int rank = topUsers.indexOf(user) + 1;
                return LeaderboardEntryDTO.builder()
                    .rank(rank)
                    .userId(user.getId())
                    .username(user.getUsername())
                    .rating(user.getRating())
                    .problemsSolved(user.getProblemsSolved())
                    .totalScore(user.getRating())
                    .contestScore(0)
                    .build();
            })
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(leaderboard);
    }
    
    @GetMapping("/contest/{contestId}")
    public ResponseEntity<List<LeaderboardEntryDTO>> getContestLeaderboard(@PathVariable Long contestId) {
        log.info("GET /api/leaderboard/contest/{}", contestId);
        List<LeaderboardEntryDTO> leaderboard = contestService.getContestLeaderboard(contestId);
        return ResponseEntity.ok(leaderboard);
    }
    
    @GetMapping("/top-solvers")
    public ResponseEntity<List<LeaderboardEntryDTO>> getTopProblemSolvers(
            @RequestParam(defaultValue = "20") int limit) {
        log.info("GET /api/leaderboard/top-solvers?limit={}", limit);
        
        List<UserDTO> topUsers = userService.getTopUsersByRating(limit);
        
        List<LeaderboardEntryDTO> leaderboard = topUsers.stream()
            .sorted((a, b) -> b.getProblemsSolved().compareTo(a.getProblemsSolved()))
            .map((user) -> {
                int rank = topUsers.indexOf(user) + 1;
                return LeaderboardEntryDTO.builder()
                    .rank(rank)
                    .userId(user.getId())
                    .username(user.getUsername())
                    .rating(user.getRating())
                    .problemsSolved(user.getProblemsSolved())
                    .totalScore(user.getProblemsSolved())
                    .contestScore(0)
                    .build();
            })
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(leaderboard);
    }
}