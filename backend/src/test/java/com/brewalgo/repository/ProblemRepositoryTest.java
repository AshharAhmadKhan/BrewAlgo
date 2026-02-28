package com.brewalgo.repository;

import com.brewalgo.domain.entity.Problem;
import com.brewalgo.domain.repository.ProblemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class ProblemRepositoryTest {
    
    @Autowired
    private ProblemRepository problemRepository;
    
    @Test
    void save_Success() {
        // Given
        Problem problem = Problem.builder()
            .slug("two-sum")
            .title("Two Sum")
            .description("Find two numbers")
            .difficulty(Problem.Difficulty.EASY)
            .baseScore(100)
            .acceptanceRate(0)
            .totalSubmissions(0)
            .successfulSubmissions(0)
            .createdAt(LocalDateTime.now())
            .build();
        
        // When
        Problem saved = problemRepository.save(problem);
        
        // Then
        assertNotNull(saved.getId());
        assertEquals("two-sum", saved.getSlug());
    }
    
    @Test
    void findBySlug_Success() {
        // Given
        Problem problem = Problem.builder()
            .slug("binary-search")
            .title("Binary Search")
            .description("Implement binary search")
            .difficulty(Problem.Difficulty.EASY)
            .baseScore(100)
            .acceptanceRate(0)
            .totalSubmissions(0)
            .successfulSubmissions(0)
            .createdAt(LocalDateTime.now())
            .build();
        problemRepository.save(problem);
        
        // When
        Optional<Problem> found = problemRepository.findBySlug("binary-search");
        
        // Then
        assertTrue(found.isPresent());
        assertEquals("Binary Search", found.get().getTitle());
    }
    
    @Test
    void findByDifficulty_Success() {
        // Given
        Problem easy1 = createProblem("easy1", Problem.Difficulty.EASY);
        Problem easy2 = createProblem("easy2", Problem.Difficulty.EASY);
        Problem medium = createProblem("medium1", Problem.Difficulty.MEDIUM);
        problemRepository.save(easy1);
        problemRepository.save(easy2);
        problemRepository.save(medium);
        
        // When
        List<Problem> easyProblems = problemRepository.findByDifficulty(Problem.Difficulty.EASY);
        
        // Then
        assertEquals(2, easyProblems.size());
        assertTrue(easyProblems.stream().allMatch(p -> p.getDifficulty() == Problem.Difficulty.EASY));
    }
    
    @Test
    void findByTag_Success() {
        // Given
        Problem problem1 = createProblem("problem1", Problem.Difficulty.EASY);
        problem1.setTags("array,hash-table");
        
        Problem problem2 = createProblem("problem2", Problem.Difficulty.MEDIUM);
        problem2.setTags("array,two-pointers");
        
        Problem problem3 = createProblem("problem3", Problem.Difficulty.HARD);
        problem3.setTags("dynamic-programming");
        
        problemRepository.save(problem1);
        problemRepository.save(problem2);
        problemRepository.save(problem3);
        
        // When
        List<Problem> arrayProblems = problemRepository.findByTag("array");
        
        // Then
        assertEquals(2, arrayProblems.size());
    }
    
    @Test
    void findMostAttemptedProblems_Success() {
        // Given
        Problem problem1 = createProblem("problem1", Problem.Difficulty.EASY);
        problem1.setTotalSubmissions(1000);
        
        Problem problem2 = createProblem("problem2", Problem.Difficulty.MEDIUM);
        problem2.setTotalSubmissions(500);
        
        Problem problem3 = createProblem("problem3", Problem.Difficulty.HARD);
        problem3.setTotalSubmissions(1500);
        
        problemRepository.save(problem1);
        problemRepository.save(problem2);
        problemRepository.save(problem3);
        
        // When
        List<Problem> mostAttempted = problemRepository.findMostAttemptedProblems(PageRequest.of(0, 2));
        
        // Then
        assertEquals(2, mostAttempted.size());
        assertEquals(1500, mostAttempted.get(0).getTotalSubmissions()); // Highest first
    }
    
    private Problem createProblem(String slug, Problem.Difficulty difficulty) {
        return Problem.builder()
            .slug(slug)
            .title(slug.toUpperCase())
            .description("Description for " + slug)
            .difficulty(difficulty)
            .baseScore(100)
            .acceptanceRate(0)
            .totalSubmissions(0)
            .successfulSubmissions(0)
            .createdAt(LocalDateTime.now())
            .build();
    }
}
