package com.brewalgo.repository;

import com.brewalgo.domain.entity.User;
import com.brewalgo.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest {
    
    @Autowired
    private UserRepository userRepository;
    
    @Test
    void save_Success() {
        // Given
        User user = User.builder()
            .username("testuser")
            .email("test@example.com")
            .passwordHash("hashedPassword")
            .rating(1000)
            .problemsSolved(0)
            .createdAt(LocalDateTime.now())
            .lastLoginAt(LocalDateTime.now())
            .role(User.UserRole.USER)
            .build();
        
        // When
        User saved = userRepository.save(user);
        
        // Then
        assertNotNull(saved.getId());
        assertEquals("testuser", saved.getUsername());
    }
    
    @Test
    void findByUsername_Success() {
        // Given
        User user = User.builder()
            .username("findme")
            .email("findme@example.com")
            .passwordHash("hash")
            .rating(1000)
            .problemsSolved(0)
            .createdAt(LocalDateTime.now())
            .lastLoginAt(LocalDateTime.now())
            .role(User.UserRole.USER)
            .build();
        userRepository.save(user);
        
        // When
        Optional<User> found = userRepository.findByUsername("findme");
        
        // Then
        assertTrue(found.isPresent());
        assertEquals("findme", found.get().getUsername());
    }
    
    @Test
    void existsByUsername_True() {
        // Given
        User user = User.builder()
            .username("exists")
            .email("exists@example.com")
            .passwordHash("hash")
            .rating(1000)
            .problemsSolved(0)
            .createdAt(LocalDateTime.now())
            .lastLoginAt(LocalDateTime.now())
            .role(User.UserRole.USER)
            .build();
        userRepository.save(user);
        
        // When
        boolean exists = userRepository.existsByUsername("exists");
        
        // Then
        assertTrue(exists);
    }
    
    @Test
    void existsByUsername_False() {
        // When
        boolean exists = userRepository.existsByUsername("nonexistent");
        
        // Then
        assertFalse(exists);
    }
    
    @Test
    void existsByEmail_True() {
        // Given
        User user = User.builder()
            .username("user")
            .email("exists@example.com")
            .passwordHash("hash")
            .rating(1000)
            .problemsSolved(0)
            .createdAt(LocalDateTime.now())
            .lastLoginAt(LocalDateTime.now())
            .role(User.UserRole.USER)
            .build();
        userRepository.save(user);
        
        // When
        boolean exists = userRepository.existsByEmail("exists@example.com");
        
        // Then
        assertTrue(exists);
    }
    
    @Test
    void findTopUsersByRating_Success() {
        // Given
        User user1 = createUser("user1", 1500);
        User user2 = createUser("user2", 1400);
        User user3 = createUser("user3", 1600);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        
        // When
        List<User> topUsers = userRepository.findTopUsersByRating(PageRequest.of(0, 2));
        
        // Then
        assertEquals(2, topUsers.size());
        assertEquals(1600, topUsers.get(0).getRating()); // Highest first
    }
    
    @Test
    void findUsersByMinimumRating_Success() {
        // Given
        User user1 = createUser("user1", 1500);
        User user2 = createUser("user2", 900);
        User user3 = createUser("user3", 1200);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        
        // When
        List<User> users = userRepository.findUsersByMinimumRating(1000);
        
        // Then
        assertEquals(2, users.size());
        assertTrue(users.stream().allMatch(u -> u.getRating() >= 1000));
    }
    
    private User createUser(String username, int rating) {
        return User.builder()
            .username(username)
            .email(username + "@example.com")
            .passwordHash("hash")
            .rating(rating)
            .problemsSolved(0)
            .createdAt(LocalDateTime.now())
            .lastLoginAt(LocalDateTime.now())
            .role(User.UserRole.USER)
            .build();
    }
}
