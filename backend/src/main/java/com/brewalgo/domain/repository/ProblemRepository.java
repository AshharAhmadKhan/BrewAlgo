package com.brewalgo.domain.repository;

import com.brewalgo.domain.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;
import jakarta.persistence.QueryHint;
import java.util.Optional;
import java.util.List;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
    
    @QueryHints(@QueryHint(name = org.hibernate.jpa.HibernateHints.HINT_CACHEABLE, value = "true"))
    Optional<Problem> findBySlug(String slug);
    
    @QueryHints(@QueryHint(name = org.hibernate.jpa.HibernateHints.HINT_CACHEABLE, value = "true"))
    List<Problem> findByDifficulty(Problem.Difficulty difficulty);
    
    @Query("SELECT p FROM Problem p WHERE p.difficulty = :difficulty ORDER BY p.acceptanceRate ASC")
    @QueryHints(@QueryHint(name = org.hibernate.jpa.HibernateHints.HINT_CACHEABLE, value = "true"))
    List<Problem> findByDifficultyOrderedByAcceptanceRate(Problem.Difficulty difficulty);
    
    @Query("SELECT p FROM Problem p ORDER BY p.totalSubmissions DESC")
    @QueryHints(@QueryHint(name = org.hibernate.jpa.HibernateHints.HINT_FETCH_SIZE, value = "50"))
    List<Problem> findMostAttemptedProblems(org.springframework.data.domain.Pageable pageable);
    
    @Query("SELECT p FROM Problem p WHERE p.tags LIKE %:tag%")
    List<Problem> findByTag(String tag);
}