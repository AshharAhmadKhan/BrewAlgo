package com.brewalgo.service;

import com.brewalgo.application.dto.ProblemDTO;
import com.brewalgo.application.service.ProblemService;
import com.brewalgo.domain.entity.Problem;
import com.brewalgo.domain.exception.BusinessException;
import com.brewalgo.domain.repository.ProblemRepository;
import com.brewalgo.infrastructure.persistence.ProblemServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProblemServiceTest {
    
    @Mock
    private ProblemRepository problemRepository;
    
    private ProblemService problemService;
    
    @BeforeEach
    void setUp() {
        problemService = new ProblemServiceImpl(problemRepository);
    }
    
    @Test
    void createProblem_Success() {
        // Given
        ProblemDTO request = new ProblemDTO();
        request.setTitle("Two Sum");
        request.setDescription("Find two numbers that add up to target");
        request.setDifficulty("EASY");
        request.setBaseScore(100);
        
        Problem savedProblem = Problem.builder()
            .id(1L)
            .slug("two-sum")
            .title("Two Sum")
            .description("Find two numbers that add up to target")
            .difficulty(Problem.Difficulty.EASY)
            .baseScore(100)
            .acceptanceRate(0)
            .totalSubmissions(0)
            .successfulSubmissions(0)
            .createdAt(LocalDateTime.now())
            .build();
        
        when(problemRepository.save(any(Problem.class))).thenReturn(savedProblem);
        
        // When
        ProblemDTO result = problemService.createProblem(request);
        
        // Then
        assertNotNull(result);
        assertEquals("Two Sum", result.getTitle());
        assertEquals("two-sum", result.getSlug());
        verify(problemRepository).save(any(Problem.class));
    }
    
    @Test
    void getProblemById_Success() {
        // Given
        Problem problem = Problem.builder()
            .id(1L)
            .slug("two-sum")
            .title("Two Sum")
            .difficulty(Problem.Difficulty.EASY)
            .baseScore(100)
            .build();
        
        when(problemRepository.findById(1L)).thenReturn(Optional.of(problem));
        
        // When
        ProblemDTO result = problemService.getProblemById(1L);
        
        // Then
        assertNotNull(result);
        assertEquals("Two Sum", result.getTitle());
        assertEquals("two-sum", result.getSlug());
    }
    
    @Test
    void getProblemById_NotFound_ThrowsException() {
        // Given
        when(problemRepository.findById(999L)).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(BusinessException.class, () -> problemService.getProblemById(999L));
    }
    
    @Test
    void getProblemBySlug_Success() {
        // Given
        Problem problem = Problem.builder()
            .id(1L)
            .slug("two-sum")
            .title("Two Sum")
            .difficulty(Problem.Difficulty.EASY)
            .build();
        
        when(problemRepository.findBySlug("two-sum")).thenReturn(Optional.of(problem));
        
        // When
        ProblemDTO result = problemService.getProblemBySlug("two-sum");
        
        // Then
        assertNotNull(result);
        assertEquals("Two Sum", result.getTitle());
    }
    
    @Test
    void getAllProblemsPageable_Success() {
        // Given
        List<Problem> problems = Arrays.asList(
            Problem.builder().id(1L).title("Problem 1").difficulty(Problem.Difficulty.EASY).build(),
            Problem.builder().id(2L).title("Problem 2").difficulty(Problem.Difficulty.MEDIUM).build()
        );
        Page<Problem> problemPage = new PageImpl<>(problems);
        
        when(problemRepository.findAll(any(Pageable.class))).thenReturn(problemPage);
        
        // When
        Page<ProblemDTO> result = problemService.getAllProblemsPageable(PageRequest.of(0, 10));
        
        // Then
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
    }
    
    @Test
    void getProblemsByDifficulty_Success() {
        // Given
        List<Problem> problems = Arrays.asList(
            Problem.builder().id(1L).title("Easy 1").difficulty(Problem.Difficulty.EASY).build(),
            Problem.builder().id(2L).title("Easy 2").difficulty(Problem.Difficulty.EASY).build()
        );
        
        when(problemRepository.findByDifficulty(Problem.Difficulty.EASY)).thenReturn(problems);
        
        // When
        List<ProblemDTO> result = problemService.getProblemsByDifficulty(Problem.Difficulty.EASY);
        
        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
    }
    
    @Test
    void updateProblem_Success() {
        // Given
        Problem existingProblem = Problem.builder()
            .id(1L)
            .slug("old-slug")
            .title("Old Title")
            .difficulty(Problem.Difficulty.EASY)
            .build();
        
        ProblemDTO updateRequest = new ProblemDTO();
        updateRequest.setTitle("New Title");
        updateRequest.setDifficulty("MEDIUM");
        
        when(problemRepository.findById(1L)).thenReturn(Optional.of(existingProblem));
        when(problemRepository.save(any(Problem.class))).thenReturn(existingProblem);
        
        // When
        ProblemDTO result = problemService.updateProblem(1L, updateRequest);
        
        // Then
        assertNotNull(result);
        verify(problemRepository).save(any(Problem.class));
    }
    
    @Test
    void deleteProblem_Success() {
        // Given
        when(problemRepository.existsById(1L)).thenReturn(true);
        
        // When
        problemService.deleteProblem(1L);
        
        // Then
        verify(problemRepository).deleteById(1L);
    }
    
    @Test
    void deleteProblem_NotFound_ThrowsException() {
        // Given
        when(problemRepository.existsById(999L)).thenReturn(false);
        
        // When & Then
        assertThrows(BusinessException.class, () -> problemService.deleteProblem(999L));
    }
    
    @Test
    void updateProblemStats_AcceptedSubmission() {
        // Given
        Problem problem = Problem.builder()
            .id(1L)
            .totalSubmissions(10)
            .successfulSubmissions(5)
            .acceptanceRate(50)
            .build();
        
        when(problemRepository.findById(1L)).thenReturn(Optional.of(problem));
        when(problemRepository.save(any(Problem.class))).thenReturn(problem);
        
        // When
        problemService.updateProblemStats(1L, true);
        
        // Then
        verify(problemRepository).save(argThat(p -> 
            p.getTotalSubmissions() == 11 && 
            p.getSuccessfulSubmissions() == 6
        ));
    }
    
    @Test
    void updateProblemStats_RejectedSubmission() {
        // Given
        Problem problem = Problem.builder()
            .id(1L)
            .totalSubmissions(10)
            .successfulSubmissions(5)
            .acceptanceRate(50)
            .build();
        
        when(problemRepository.findById(1L)).thenReturn(Optional.of(problem));
        when(problemRepository.save(any(Problem.class))).thenReturn(problem);
        
        // When
        problemService.updateProblemStats(1L, false);
        
        // Then
        verify(problemRepository).save(argThat(p -> 
            p.getTotalSubmissions() == 11 && 
            p.getSuccessfulSubmissions() == 5
        ));
    }
    
    @Test
    void getProblemBySlug_NotFound_ThrowsException() {
        // Given
        when(problemRepository.findBySlug("nonexistent")).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(BusinessException.class, () -> problemService.getProblemBySlug("nonexistent"));
    }
    
    @Test
    void getAllProblems_Success() {
        // Given
        List<Problem> problems = Arrays.asList(
            Problem.builder().id(1L).title("Problem 1").difficulty(Problem.Difficulty.EASY).build(),
            Problem.builder().id(2L).title("Problem 2").difficulty(Problem.Difficulty.MEDIUM).build()
        );
        
        when(problemRepository.findAll()).thenReturn(problems);
        
        // When
        List<ProblemDTO> result = problemService.getAllProblems();
        
        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
    }
    
    @Test
    void getProblemsByTag_Success() {
        // Given
        List<Problem> problems = Arrays.asList(
            Problem.builder().id(1L).title("Array Problem").difficulty(Problem.Difficulty.EASY).build()
        );
        
        when(problemRepository.findByTag("array")).thenReturn(problems);
        
        // When
        List<ProblemDTO> result = problemService.getProblemsByTag("array");
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
    }
    
    @Test
    void getMostAttemptedProblems_Success() {
        // Given
        List<Problem> problems = Arrays.asList(
            Problem.builder().id(1L).title("Popular").totalSubmissions(1000).difficulty(Problem.Difficulty.EASY).build()
        );
        
        when(problemRepository.findMostAttemptedProblems(any(PageRequest.class))).thenReturn(problems);
        
        // When
        List<ProblemDTO> result = problemService.getMostAttemptedProblems(10);
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
    }
    
    @Test
    void updateProblem_NotFound_ThrowsException() {
        // Given
        when(problemRepository.findById(999L)).thenReturn(Optional.empty());
        
        ProblemDTO updateRequest = new ProblemDTO();
        updateRequest.setTitle("New Title");
        
        // When & Then
        assertThrows(BusinessException.class, () -> problemService.updateProblem(999L, updateRequest));
    }
    
    @Test
    void getRecommendedProblems_Success() {
        // Given
        List<Problem> problems = Arrays.asList(
            Problem.builder().id(1L).title("Recommended").difficulty(Problem.Difficulty.EASY).build()
        );
        
        when(problemRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(problems));
        
        // When
        List<ProblemDTO> result = problemService.getRecommendedProblems(1L, 10);
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
    }
}

