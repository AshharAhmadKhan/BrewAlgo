package com.brewalgo.application.service;

import com.brewalgo.application.dto.ProblemDTO;
import com.brewalgo.domain.entity.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ProblemService {
    
    ProblemDTO createProblem(ProblemDTO problemDTO);
    
    ProblemDTO getProblemById(Long id);
    
    ProblemDTO getProblemBySlug(String slug);
    
    List<ProblemDTO> getAllProblems();
    
    Page<ProblemDTO> getAllProblemsPageable(Pageable pageable);
    
    List<ProblemDTO> getProblemsByDifficulty(Problem.Difficulty difficulty);
    
    List<ProblemDTO> getProblemsByTag(String tag);
    
    List<ProblemDTO> getMostAttemptedProblems(int limit);
    
    ProblemDTO updateProblem(Long id, ProblemDTO problemDTO);
    
    void deleteProblem(Long id);
    
    void updateProblemStats(Long problemId, boolean isAccepted);
    
    List<ProblemDTO> getRecommendedProblems(Long userId, int limit);
}