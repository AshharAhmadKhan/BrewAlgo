package com.brewalgo.domain.repository;

import com.brewalgo.domain.entity.Submission;
import com.brewalgo.domain.entity.User;
import com.brewalgo.domain.entity.Problem;
import com.brewalgo.domain.entity.Contest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    
    List<Submission> findByUser(User user);
    
    List<Submission> findByProblem(Problem problem);
    
    List<Submission> findByContest(Contest contest);
    
    List<Submission> findByUserAndProblem(User user, Problem problem);
    
    @Query("SELECT s FROM Submission s WHERE s.user = :user AND s.status = 'ACCEPTED'")
    List<Submission> findAcceptedSubmissionsByUser(User user);
    
    @Query("SELECT s FROM Submission s WHERE s.contest = :contest AND s.status = 'ACCEPTED' ORDER BY s.submittedAt ASC")
    List<Submission> findContestAcceptedSubmissionsOrdered(Contest contest);
    
    @Query("SELECT COUNT(s) FROM Submission s WHERE s.user = :user AND s.problem = :problem AND s.status = 'ACCEPTED'")
    long countAcceptedSubmissions(User user, Problem problem);
    
    Optional<Submission> findFirstByUserAndProblemAndStatusOrderBySubmittedAtDesc(
        User user, Problem problem, Submission.Status status
    );
}