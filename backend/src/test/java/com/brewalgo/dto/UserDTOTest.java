package com.brewalgo.dto;

import com.brewalgo.application.dto.UserDTO;
import com.brewalgo.domain.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserDTOTest {
    
    @Test
    void fromEntity_Success() {
        // Given
        User user = User.builder()
            .id(1L)
            .username("testuser")
            .email("test@example.com")
            .rating(1200)
            .problemsSolved(10)
            .createdAt(LocalDateTime.now())
            .lastLoginAt(LocalDateTime.now())
            .role(User.UserRole.USER)
            .build();
        
        // When
        UserDTO dto = UserDTO.fromEntity(user);
        
        // Then
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("testuser", dto.getUsername());
        assertEquals("test@example.com", dto.getEmail());
        assertEquals(1200, dto.getRating());
        assertEquals(10, dto.getProblemsSolved());
        assertEquals("USER", dto.getRole());
    }
    
    @Test
    void fromEntity_AdminRole() {
        // Given
        User admin = User.builder()
            .id(1L)
            .username("admin")
            .email("admin@example.com")
            .rating(2000)
            .problemsSolved(100)
            .createdAt(LocalDateTime.now())
            .lastLoginAt(LocalDateTime.now())
            .role(User.UserRole.ADMIN)
            .build();
        
        // When
        UserDTO dto = UserDTO.fromEntity(admin);
        
        // Then
        assertEquals("ADMIN", dto.getRole());
    }
    
    @Test
    void builder_Success() {
        // When
        UserDTO dto = UserDTO.builder()
            .id(1L)
            .username("testuser")
            .email("test@example.com")
            .rating(1000)
            .problemsSolved(5)
            .role("USER")
            .build();
        
        // Then
        assertNotNull(dto);
        assertEquals(1L, dto.getId());
        assertEquals("testuser", dto.getUsername());
    }
}
