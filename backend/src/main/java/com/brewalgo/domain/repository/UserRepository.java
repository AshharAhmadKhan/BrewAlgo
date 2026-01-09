package com.brewalgo.domain.repository;

import com.brewalgo.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT u FROM User u ORDER BY u.rating DESC")
    List<User> findTopUsersByRating(org.springframework.data.domain.Pageable pageable);
    
    @Query("SELECT u FROM User u WHERE u.rating >= :minRating ORDER BY u.rating DESC")
    List<User> findUsersByMinimumRating(Integer minRating);
}