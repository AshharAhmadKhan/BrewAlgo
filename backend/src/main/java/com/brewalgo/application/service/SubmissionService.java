package com.brewalgo.application.service;

import com.brewalgo.application.dto.SubmissionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface SubmissionService {
    
    SubmissionDTO submitSolution(Long userId, Long problemId, String code, String language);
    
    SubmissionDTO submitContestSolution(Long userId, Long problemId, Long contestId, String code, String language);
    
    SubmissionDTO getSubmissionById(Long id);
    
    List<SubmissionDTO> getSubmissionsByUser(Long userId);
    
    Page<SubmissionDTO> getSubmissionsByUserPageable(Long userId, Pageable pageable);
    
    List<SubmissionDTO> getSubmissionsByProblem(Long problemId);
    
    List<SubmissionDTO> getSubmissionsByContest(Long contestId);
    
    List<SubmissionDTO> getUserProblemSubmissions(Long userId, Long problemId);
    
    List<SubmissionDTO> getAcceptedSubmissionsByUser(Long userId);
    
    SubmissionDTO evaluateSubmission(Long submissionId);
    
    void updateSubmissionStatus(Long submissionId, String status, Integer executionTime, Integer memoryUsed, String errorMessage);
    
    int calculateScore(Long problemId, Integer executionTime);
    
    boolean hasUserSolvedProblem(Long userId, Long problemId);
}