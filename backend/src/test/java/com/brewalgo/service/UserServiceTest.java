package com.brewalgo.service;

import com.brewalgo.application.dto.AuthRequestDTO;
import com.brewalgo.application.dto.AuthResponseDTO;
import com.brewalgo.application.dto.UserDTO;
import com.brewalgo.application.service.UserService;
import com.brewalgo.domain.entity.User;
import com.brewalgo.domain.exception.BusinessException;
import com.brewalgo.domain.repository.UserRepository;
import com.brewalgo.infrastructure.persistence.UserServiceImpl;
import com.brewalgo.infrastructure.security.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @Mock
    private JwtUtil jwtUtil;
    
    @Mock
    private UserDetailsService userDetailsService;
    
    private UserService userService;
    
    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, passwordEncoder, jwtUtil, userDetailsService);
    }
    
    @Test
    void register_Success() {
        // Given
        AuthRequestDTO request = new AuthRequestDTO();
        request.setUsername("testuser");
        request.setEmail("test@example.com");
        request.setPassword("password123");
        
        User savedUser = User.builder()
            .id(1L)
            .username("testuser")
            .email("test@example.com")
            .passwordHash("hashedPassword")
            .rating(1000)
            .problemsSolved(0)
            .createdAt(LocalDateTime.now())
            .lastLoginAt(LocalDateTime.now())
            .role(User.UserRole.USER)
            .build();
        
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(mock(UserDetails.class));
        when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn("jwt-token");
        
        // When
        AuthResponseDTO response = userService.register(request);
        
        // Then
        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals("testuser", response.getUser().getUsername());
        verify(userRepository).save(any(User.class));
    }
    
    @Test
    void register_UsernameExists_ThrowsException() {
        // Given
        AuthRequestDTO request = new AuthRequestDTO();
        request.setUsername("existinguser");
        request.setEmail("test@example.com");
        request.setPassword("password123");
        
        when(userRepository.existsByUsername("existinguser")).thenReturn(true);
        
        // When & Then
        assertThrows(BusinessException.class, () -> userService.register(request));
        verify(userRepository, never()).save(any(User.class));
    }
    
    @Test
    void getUserById_Success() {
        // Given
        User user = User.builder()
            .id(1L)
            .username("testuser")
            .email("test@example.com")
            .rating(1000)
            .problemsSolved(5)
            .role(User.UserRole.USER)
            .build();
        
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        
        // When
        UserDTO result = userService.getUserById(1L);
        
        // Then
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals(1000, result.getRating());
    }
    
    @Test
    void getUserById_NotFound_ThrowsException() {
        // Given
        when(userRepository.findById(999L)).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(BusinessException.class, () -> userService.getUserById(999L));
    }
    
    @Test
    void updateUserRating_Success() {
        // Given
        User user = User.builder()
            .id(1L)
            .username("testuser")
            .rating(1000)
            .role(User.UserRole.USER)
            .build();
        
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        
        // When
        userService.updateUserRating(1L, 1200);
        
        // Then
        verify(userRepository).save(argThat(u -> u.getRating() == 1200));
    }
    
    @Test
    void register_EmailExists_ThrowsException() {
        // Given
        AuthRequestDTO request = new AuthRequestDTO();
        request.setUsername("newuser");
        request.setEmail("existing@example.com");
        request.setPassword("password123");
        
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail("existing@example.com")).thenReturn(true);
        
        // When & Then
        assertThrows(BusinessException.class, () -> userService.register(request));
        verify(userRepository, never()).save(any(User.class));
    }
    
    @Test
    void login_Success() {
        // Given
        User user = User.builder()
            .id(1L)
            .username("testuser")
            .email("test@example.com")
            .passwordHash("hashedPassword")
            .rating(1000)
            .role(User.UserRole.USER)
            .build();
        
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("password123", "hashedPassword")).thenReturn(true);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(mock(UserDetails.class));
        when(jwtUtil.generateToken(any(UserDetails.class))).thenReturn("jwt-token");
        
        // When
        AuthResponseDTO response = userService.login("testuser", "password123");
        
        // Then
        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        verify(userRepository).save(any(User.class)); // Updates lastLoginAt
    }
    
    @Test
    void login_InvalidPassword_ThrowsException() {
        // Given
        User user = User.builder()
            .id(1L)
            .username("testuser")
            .passwordHash("hashedPassword")
            .build();
        
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongpassword", "hashedPassword")).thenReturn(false);
        
        // When & Then
        assertThrows(BusinessException.class, () -> userService.login("testuser", "wrongpassword"));
    }
    
    @Test
    void login_UserNotFound_ThrowsException() {
        // Given
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(BusinessException.class, () -> userService.login("nonexistent", "password"));
    }
    
    @Test
    void getUserByUsername_Success() {
        // Given
        User user = User.builder()
            .id(1L)
            .username("testuser")
            .email("test@example.com")
            .rating(1200)
            .role(User.UserRole.USER)
            .build();
        
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        
        // When
        UserDTO result = userService.getUserByUsername("testuser");
        
        // Then
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        assertEquals(1200, result.getRating());
    }
    
    @Test
    void updateUser_Success() {
        // Given
        User user = User.builder()
            .id(1L)
            .username("testuser")
            .email("old@example.com")
            .role(User.UserRole.USER)
            .build();
        
        UserDTO updateRequest = new UserDTO();
        updateRequest.setEmail("new@example.com");
        
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail("new@example.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);
        
        // When
        UserDTO result = userService.updateUser(1L, updateRequest);
        
        // Then
        assertNotNull(result);
        verify(userRepository).save(any(User.class));
    }
    
    @Test
    void deleteUser_Success() {
        // Given
        when(userRepository.existsById(1L)).thenReturn(true);
        
        // When
        userService.deleteUser(1L);
        
        // Then
        verify(userRepository).deleteById(1L);
    }
    
    @Test
    void getTopUsersByRating_Success() {
        // Given
        List<User> topUsers = Arrays.asList(
            User.builder().id(1L).username("user1").rating(1500).role(User.UserRole.USER).build(),
            User.builder().id(2L).username("user2").rating(1400).role(User.UserRole.USER).build()
        );
        
        when(userRepository.findTopUsersByRating(any(PageRequest.class))).thenReturn(topUsers);
        
        // When
        List<UserDTO> result = userService.getTopUsersByRating(10);
        
        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getUsername());
    }
    
    @Test
    void incrementProblemsSolved_Success() {
        // Given
        User user = User.builder()
            .id(1L)
            .username("testuser")
            .problemsSolved(5)
            .role(User.UserRole.USER)
            .build();
        
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);
        
        // When
        userService.incrementProblemsSolved(1L);
        
        // Then
        verify(userRepository).save(argThat(u -> u.getProblemsSolved() == 6));
    }
}

