package com.brewalgo.controller;

import com.brewalgo.application.dto.ProblemDTO;
import com.brewalgo.application.service.ProblemService;
import com.brewalgo.domain.entity.Problem;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProblemControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private ProblemService problemService;
    
    @Test
    @WithMockUser
    void getAllProblems_Success() throws Exception {
        // Given
        ProblemDTO problem1 = new ProblemDTO();
        problem1.setId(1L);
        problem1.setTitle("Two Sum");
        problem1.setDifficulty("EASY");
        
        ProblemDTO problem2 = new ProblemDTO();
        problem2.setId(2L);
        problem2.setTitle("Binary Search");
        problem2.setDifficulty("MEDIUM");
        
        List<ProblemDTO> problems = Arrays.asList(problem1, problem2);
        Page<ProblemDTO> problemPage = new PageImpl<>(problems);
        
        when(problemService.getAllProblemsPageable(any(Pageable.class))).thenReturn(problemPage);
        
        // When & Then
        mockMvc.perform(get("/api/v1/problems?page=0&size=20"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.problems[0].title").value("Two Sum"))
            .andExpect(jsonPath("$.problems[1].title").value("Binary Search"))
            .andExpect(jsonPath("$.totalItems").value(2));
    }
    
    @Test
    @WithMockUser
    void getProblemById_Success() throws Exception {
        // Given
        ProblemDTO problem = new ProblemDTO();
        problem.setId(1L);
        problem.setTitle("Two Sum");
        problem.setSlug("two-sum");
        problem.setDifficulty("EASY");
        problem.setBaseScore(100);
        
        when(problemService.getProblemById(1L)).thenReturn(problem);
        
        // When & Then
        mockMvc.perform(get("/api/v1/problems/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.title").value("Two Sum"))
            .andExpect(jsonPath("$.slug").value("two-sum"));
    }
    
    @Test
    @WithMockUser
    void getProblemBySlug_Success() throws Exception {
        // Given
        ProblemDTO problem = new ProblemDTO();
        problem.setId(1L);
        problem.setTitle("Two Sum");
        problem.setSlug("two-sum");
        
        when(problemService.getProblemBySlug("two-sum")).thenReturn(problem);
        
        // When & Then
        mockMvc.perform(get("/api/v1/problems/slug/two-sum"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Two Sum"));
    }
    
    @Test
    @WithMockUser
    void getProblemsByDifficulty_Success() throws Exception {
        // Given
        ProblemDTO problem1 = new ProblemDTO();
        problem1.setId(1L);
        problem1.setTitle("Easy Problem 1");
        problem1.setDifficulty("EASY");
        
        ProblemDTO problem2 = new ProblemDTO();
        problem2.setId(2L);
        problem2.setTitle("Easy Problem 2");
        problem2.setDifficulty("EASY");
        
        List<ProblemDTO> easyProblems = Arrays.asList(problem1, problem2);
        when(problemService.getProblemsByDifficulty(Problem.Difficulty.EASY)).thenReturn(easyProblems);
        
        // When & Then
        mockMvc.perform(get("/api/v1/problems/difficulty/EASY"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].difficulty").value("EASY"))
            .andExpect(jsonPath("$[1].difficulty").value("EASY"));
    }
    
    @Test
    @WithMockUser
    void getMostAttemptedProblems_Success() throws Exception {
        // Given
        ProblemDTO problem = new ProblemDTO();
        problem.setId(1L);
        problem.setTitle("Popular Problem");
        problem.setTotalSubmissions(1000);
        
        List<ProblemDTO> problems = Arrays.asList(problem);
        when(problemService.getMostAttemptedProblems(10)).thenReturn(problems);
        
        // When & Then
        mockMvc.perform(get("/api/v1/problems/most-attempted?limit=10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].title").value("Popular Problem"));
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void createProblem_Success() throws Exception {
        // Given
        ProblemDTO request = new ProblemDTO();
        request.setTitle("New Problem");
        request.setDescription("Description");
        request.setDifficulty("MEDIUM");
        request.setBaseScore(150);
        
        ProblemDTO created = new ProblemDTO();
        created.setId(1L);
        created.setTitle("New Problem");
        created.setSlug("new-problem");
        
        when(problemService.createProblem(any(ProblemDTO.class))).thenReturn(created);
        
        // When & Then
        mockMvc.perform(post("/api/v1/problems")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title").value("New Problem"));
    }
    
    @Test
    @WithMockUser(roles = "ADMIN")
    void updateProblem_Success() throws Exception {
        // Given
        ProblemDTO request = new ProblemDTO();
        request.setTitle("Updated Title");
        
        ProblemDTO updated = new ProblemDTO();
        updated.setId(1L);
        updated.setTitle("Updated Title");
        
        when(problemService.updateProblem(eq(1L), any(ProblemDTO.class))).thenReturn(updated);
        
        // When & Then
        mockMvc.perform(put("/api/v1/problems/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Updated Title"));
    }
}
