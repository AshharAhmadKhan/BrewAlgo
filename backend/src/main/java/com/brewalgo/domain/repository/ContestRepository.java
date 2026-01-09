package com.brewalgo.domain.repository;

import com.brewalgo.domain.entity.Contest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Repository
public interface ContestRepository extends JpaRepository<Contest, Long> {
    
    Optional<Contest> findBySlug(String slug);
    
    List<Contest> findByStatus(Contest.Status status);
    
    @Query("SELECT c FROM Contest c WHERE c.startTime > :now ORDER BY c.startTime ASC")
    List<Contest> findUpcomingContests(LocalDateTime now);
    
    @Query("SELECT c FROM Contest c WHERE c.startTime <= :now AND c.endTime >= :now")
    List<Contest> findLiveContests(LocalDateTime now);
    
    @Query("SELECT c FROM Contest c WHERE c.endTime < :now ORDER BY c.endTime DESC")
    List<Contest> findCompletedContests(LocalDateTime now);
    
    @Query("SELECT c FROM Contest c WHERE c.status = 'LIVE' AND c.currentParticipants < c.maxParticipants")
    List<Contest> findJoinableContests();
}