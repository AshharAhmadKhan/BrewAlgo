package com.brewalgo.controller;

import com.brewalgo.application.dto.AuthRequestDTO;
import com.brewalgo.application.dto.AuthResponseDTO;
import com.brewalgo.application.dto.UserDTO;
import com.brewalgo.application.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private UserService userService;
    
    @Test
    void register_Success() throws Exception {
        // Given
        AuthRequestDTO request = new AuthRequestDTO();
        request.setUsername("testuser");
        request.setEmail("test@example.com");
        request.setPassword("password123");
        
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("testuser");
        userDTO.setEmail("test@example.com");
        
        AuthResponseDTO response = AuthResponseDTO.builder()
            .token("jwt-token")
            .user(userDTO)
            .message("Registration successful")
            .build();
        
        when(userService.register(any(AuthRequestDTO.class))).thenReturn(response);
        
        // When & Then
        mockMvc.perform(post("/api/v1/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.token").value("jwt-token"))
            .andExpect(jsonPath("$.user.username").value("testuser"));
    }
    
    @Test
    void register_InvalidRequest_BadRequest() throws Exception {
        // Given - missing required fields
        AuthRequestDTO request = new AuthRequestDTO();
        request.setUsername(""); // Invalid
        
        // When & Then
        mockMvc.perform(post("/api/v1/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    @WithMockUser
    void getUserById_Success() throws Exception {
        // Given
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("testuser");
        userDTO.setRating(1200);
        
        when(userService.getUserById(1L)).thenReturn(userDTO);
        
        // When & Then
        mockMvc.perform(get("/api/v1/users/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.username").value("testuser"))
            .andExpect(jsonPath("$.rating").value(1200));
    }
    
    @Test
    @WithMockUser
    void getTopUsers_Success() throws Exception {
        // Given
        UserDTO user1 = new UserDTO();
        user1.setId(1L);
        user1.setUsername("user1");
        user1.setRating(1500);
        
        UserDTO user2 = new UserDTO();
        user2.setId(2L);
        user2.setUsername("user2");
        user2.setRating(1400);
        
        List<UserDTO> topUsers = Arrays.asList(user1, user2);
        when(userService.getTopUsersByRating(10)).thenReturn(topUsers);
        
        // When & Then
        mockMvc.perform(get("/api/v1/users/top?limit=10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].username").value("user1"))
            .andExpect(jsonPath("$[0].rating").value(1500))
            .andExpect(jsonPath("$[1].username").value("user2"));
    }
    
    @Test
    @WithMockUser
    void checkUsernameExists_True() throws Exception {
        // Given
        when(userService.existsByUsername("existinguser")).thenReturn(true);
        
        // When & Then
        mockMvc.perform(get("/api/v1/users/exists/username/existinguser"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.exists").value(true));
    }
    
    @Test
    @WithMockUser
    void checkUsernameExists_False() throws Exception {
        // Given
        when(userService.existsByUsername("newuser")).thenReturn(false);
        
        // When & Then
        mockMvc.perform(get("/api/v1/users/exists/username/newuser"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.exists").value(false));
    }
}
