package com.brewalgo.service;

import com.brewalgo.application.dto.SubmissionDTO;
import com.brewalgo.application.service.ProblemService;
import com.brewalgo.application.service.SubmissionService;
import com.brewalgo.application.service.UserService;
import com.brewalgo.domain.entity.Problem;
import com.brewalgo.domain.entity.Submission;
import com.brewalgo.domain.entity.User;
import com.brewalgo.domain.exception.BusinessException;
import com.brewalgo.domain.repository.ContestRepository;
import com.brewalgo.domain.repository.ProblemRepository;
import com.brewalgo.domain.repository.SubmissionRepository;
import com.brewalgo.domain.repository.UserRepository;
import com.brewalgo.infrastructure.persistence.SubmissionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubmissionServiceTest {
    
    @Mock
    private SubmissionRepository submissionRepository;
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private ProblemRepository problemRepository;
    
    @Mock
    private ContestRepository contestRepository;
    
    @Mock
    private ProblemService problemService;
    
    @Mock
    private UserService userService;
    
    private SubmissionService submissionService;
    
    @BeforeEach
    void setUp() {
        submissionService = new SubmissionServiceImpl(
            submissionRepository,
            userRepository,
            problemRepository,
            contestRepository,
            problemService,
            userService
        );
    }
    
    @Test
    void submitSolution_Success() {
        // Given
        User user = User.builder().id(1L).username("testuser").build();
        Problem problem = Problem.builder().id(1L).title("Two Sum").baseScore(100).build();
        
        Submission savedSubmission = Submission.builder()
            .id(1L)
            .user(user)
            .problem(problem)
            .code("def solution(): pass")
            .language(Submission.Language.PYTHON)
            .status(Submission.Status.PENDING)
            .submittedAt(LocalDateTime.now())
            .build();
        
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(problemRepository.findById(1L)).thenReturn(Optional.of(problem));
        when(submissionRepository.save(any(Submission.class))).thenReturn(savedSubmission);
        
        // When
        SubmissionDTO result = submissionService.submitSolution(1L, 1L, "def solution(): pass", "PYTHON");
        
        // Then
        assertNotNull(result);
        assertEquals(Submission.Status.PENDING.name(), result.getStatus());
        verify(submissionRepository).save(any(Submission.class));
    }
    
    @Test
    void submitSolution_UserNotFound_ThrowsException() {
        // Given
        when(userRepository.findById(999L)).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(BusinessException.class, () -> 
            submissionService.submitSolution(999L, 1L, "code", "PYTHON")
        );
    }
    
    @Test
    void submitSolution_ProblemNotFound_ThrowsException() {
        // Given
        User user = User.builder().id(1L).build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(problemRepository.findById(999L)).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(BusinessException.class, () -> 
            submissionService.submitSolution(1L, 999L, "code", "PYTHON")
        );
    }
    
    @Test
    void getSubmissionById_Success() {
        // Given
        User user = User.builder().id(1L).username("testuser").role(User.UserRole.USER).build();
        Problem problem = Problem.builder().id(1L).title("Two Sum").build();
        
        Submission submission = Submission.builder()
            .id(1L)
            .user(user)
            .problem(problem)
            .status(Submission.Status.ACCEPTED)
            .language(Submission.Language.PYTHON)
            .build();
        
        when(submissionRepository.findById(1L)).thenReturn(Optional.of(submission));
        
        // When
        SubmissionDTO result = submissionService.getSubmissionById(1L);
        
        // Then
        assertNotNull(result);
        assertEquals(Submission.Status.ACCEPTED.name(), result.getStatus());
    }
    
    @Test
    void getSubmissionsByUser_Success() {
        // Given
        User user = User.builder().id(1L).username("testuser").role(User.UserRole.USER).build();
        Problem problem = Problem.builder().id(1L).title("Two Sum").build();
        
        List<Submission> submissions = Arrays.asList(
            Submission.builder().id(1L).user(user).problem(problem).status(Submission.Status.ACCEPTED).language(Submission.Language.PYTHON).build(),
            Submission.builder().id(2L).user(user).problem(problem).status(Submission.Status.WRONG_ANSWER).language(Submission.Language.JAVA).build()
        );
        
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(submissionRepository.findByUser(user)).thenReturn(submissions);
        
        // When
        List<SubmissionDTO> result = submissionService.getSubmissionsByUser(1L);
        
        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
    }
    
    @Test
    void getSubmissionsByUserPageable_Success() {
        // Given
        User user = User.builder().id(1L).username("testuser").role(User.UserRole.USER).build();
        Problem problem = Problem.builder().id(1L).title("Two Sum").build();
        
        List<Submission> submissions = Arrays.asList(
            Submission.builder().id(1L).user(user).problem(problem).language(Submission.Language.PYTHON).status(Submission.Status.ACCEPTED).build()
        );
        Page<Submission> submissionPage = new PageImpl<>(submissions);
        
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(submissionRepository.findByUser(eq(user), any(Pageable.class))).thenReturn(submissionPage);
        
        // When
        Page<SubmissionDTO> result = submissionService.getSubmissionsByUserPageable(1L, Pageable.unpaged());
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
    }
    
    @Test
    void updateSubmissionStatus_Success() {
        // Given
        Submission submission = Submission.builder()
            .id(1L)
            .status(Submission.Status.PENDING)
            .build();
        
        when(submissionRepository.findById(1L)).thenReturn(Optional.of(submission));
        when(submissionRepository.save(any(Submission.class))).thenReturn(submission);
        
        // When
        submissionService.updateSubmissionStatus(1L, "ACCEPTED", 500, 10000, null);
        
        // Then
        verify(submissionRepository).save(argThat(s -> 
            s.getStatus() == Submission.Status.ACCEPTED &&
            s.getExecutionTimeMs() == 500 &&
            s.getMemoryUsedKb() == 10000
        ));
    }
    
    @Test
    void calculateScore_FastExecution() {
        // Given
        Problem problem = Problem.builder().id(1L).baseScore(100).build();
        when(problemRepository.findById(1L)).thenReturn(Optional.of(problem));
        
        // When
        int score = submissionService.calculateScore(1L, 200);
        
        // Then
        assertTrue(score > 100); // Should get bonus for fast execution
        assertTrue(score <= 150); // Max 50% bonus
    }
    
    @Test
    void calculateScore_SlowExecution() {
        // Given
        Problem problem = Problem.builder().id(1L).baseScore(100).build();
        when(problemRepository.findById(1L)).thenReturn(Optional.of(problem));
        
        // When
        int score = submissionService.calculateScore(1L, 2000);
        
        // Then
        assertEquals(100, score); // No bonus for slow execution
    }
    
    @Test
    void hasUserSolvedProblem_True() {
        // Given
        User user = User.builder().id(1L).build();
        Problem problem = Problem.builder().id(1L).build();
        
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(problemRepository.findById(1L)).thenReturn(Optional.of(problem));
        when(submissionRepository.countAcceptedSubmissions(user, problem)).thenReturn(1L);
        
        // When
        boolean result = submissionService.hasUserSolvedProblem(1L, 1L);
        
        // Then
        assertTrue(result);
    }
    
    @Test
    void hasUserSolvedProblem_False() {
        // Given
        User user = User.builder().id(1L).build();
        Problem problem = Problem.builder().id(1L).build();
        
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(problemRepository.findById(1L)).thenReturn(Optional.of(problem));
        when(submissionRepository.countAcceptedSubmissions(user, problem)).thenReturn(0L);
        
        // When
        boolean result = submissionService.hasUserSolvedProblem(1L, 1L);
        
        // Then
        assertFalse(result);
    }
    
    @Test
    void getAcceptedSubmissionsByUser_Success() {
        // Given
        User user = User.builder().id(1L).username("testuser").role(User.UserRole.USER).build();
        Problem problem = Problem.builder().id(1L).title("Two Sum").build();
        
        List<Submission> acceptedSubmissions = Arrays.asList(
            Submission.builder().id(1L).user(user).problem(problem).status(Submission.Status.ACCEPTED).language(Submission.Language.PYTHON).build(),
            Submission.builder().id(2L).user(user).problem(problem).status(Submission.Status.ACCEPTED).language(Submission.Language.JAVA).build()
        );
        
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(submissionRepository.findAcceptedSubmissionsByUser(user)).thenReturn(acceptedSubmissions);
        
        // When
        List<SubmissionDTO> result = submissionService.getAcceptedSubmissionsByUser(1L);
        
        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(s -> s.getStatus().equals("ACCEPTED")));
    }
    
    @Test
    void getSubmissionById_NotFound_ThrowsException() {
        // Given
        when(submissionRepository.findById(999L)).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(BusinessException.class, () -> submissionService.getSubmissionById(999L));
    }
    
    @Test
    void getSubmissionsByProblem_Success() {
        // Given
        User user = User.builder().id(1L).username("testuser").role(User.UserRole.USER).build();
        Problem problem = Problem.builder().id(1L).title("Two Sum").build();
        
        List<Submission> submissions = Arrays.asList(
            Submission.builder().id(1L).user(user).problem(problem).language(Submission.Language.PYTHON).status(Submission.Status.ACCEPTED).build()
        );
        
        when(problemRepository.findById(1L)).thenReturn(Optional.of(problem));
        when(submissionRepository.findByProblem(problem)).thenReturn(submissions);
        
        // When
        List<SubmissionDTO> result = submissionService.getSubmissionsByProblem(1L);
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
    }
    
    @Test
    void getUserProblemSubmissions_Success() {
        // Given
        User user = User.builder().id(1L).username("testuser").role(User.UserRole.USER).build();
        Problem problem = Problem.builder().id(1L).title("Two Sum").build();
        
        List<Submission> submissions = Arrays.asList(
            Submission.builder().id(1L).user(user).problem(problem).language(Submission.Language.PYTHON).status(Submission.Status.ACCEPTED).build()
        );
        
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(problemRepository.findById(1L)).thenReturn(Optional.of(problem));
        when(submissionRepository.findByUserAndProblem(user, problem)).thenReturn(submissions);
        
        // When
        List<SubmissionDTO> result = submissionService.getUserProblemSubmissions(1L, 1L);
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
    }
    
    @Test
    void calculateScore_NullExecutionTime() {
        // Given
        Problem problem = Problem.builder().id(1L).baseScore(100).build();
        when(problemRepository.findById(1L)).thenReturn(Optional.of(problem));
        
        // When
        int score = submissionService.calculateScore(1L, null);
        
        // Then
        assertEquals(100, score); // Base score only
    }
    
    @Test
    void updateSubmissionStatus_InvalidStatus() {
        // Given
        Submission submission = Submission.builder()
            .id(1L)
            .status(Submission.Status.PENDING)
            .build();
        
        when(submissionRepository.findById(1L)).thenReturn(Optional.of(submission));
        when(submissionRepository.save(any(Submission.class))).thenReturn(submission);
        
        // When
        submissionService.updateSubmissionStatus(1L, "INVALID_STATUS", 500, 10000, null);
        
        // Then - Should default to RUNTIME_ERROR
        verify(submissionRepository).save(argThat(s -> 
            s.getStatus() == Submission.Status.RUNTIME_ERROR
        ));
    }
    
    @Test
    void submitSolution_InvalidLanguage_ThrowsException() {
        // Given
        User user = User.builder().id(1L).build();
        Problem problem = Problem.builder().id(1L).build();
        
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(problemRepository.findById(1L)).thenReturn(Optional.of(problem));
        
        // When & Then
        assertThrows(IllegalArgumentException.class, () -> 
            submissionService.submitSolution(1L, 1L, "code", "INVALID_LANGUAGE")
        );
    }
}

