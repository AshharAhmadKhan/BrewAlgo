package com.brewalgo.domain.repository;

import com.brewalgo.domain.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
    
    Optional<Problem> findBySlug(String slug);
    
    List<Problem> findByDifficulty(Problem.Difficulty difficulty);
    
    @Query("SELECT p FROM Problem p WHERE p.difficulty = :difficulty ORDER BY p.acceptanceRate ASC")
    List<Problem> findByDifficultyOrderedByAcceptanceRate(Problem.Difficulty difficulty);
    
    @Query("SELECT p FROM Problem p ORDER BY p.totalSubmissions DESC")
    List<Problem> findMostAttemptedProblems(org.springframework.data.domain.Pageable pageable);
    
    @Query("SELECT p FROM Problem p WHERE p.tags LIKE %:tag%")
    List<Problem> findByTag(String tag);
}