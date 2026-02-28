package com.brewalgo.controller;

import com.brewalgo.application.dto.ExecutionResult;
import com.brewalgo.application.dto.SubmissionDTO;
import com.brewalgo.application.dto.SubmissionRequest;
import com.brewalgo.application.dto.UserDTO;
import com.brewalgo.application.service.CodeExecutionService;
import com.brewalgo.application.service.SubmissionService;
import com.brewalgo.application.service.UserService;
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

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SubmissionControllerIntegrationTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private SubmissionService submissionService;
    
    @MockBean
    private CodeExecutionService codeExecutionService;
    
    @MockBean
    private UserService userService;
    
    @Test
    @WithMockUser(username = "testuser")
    void submitSolution_Success() throws Exception {
        // Given
        SubmissionRequest request = new SubmissionRequest();
        request.setProblemId(1L);
        request.setCode("def solution(): return True");
        request.setLanguage("PYTHON");
        
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("testuser");
        
        SubmissionDTO submissionDTO = new SubmissionDTO();
        submissionDTO.setId(1L);
        submissionDTO.setUserId(1L);
        submissionDTO.setProblemId(1L);
        submissionDTO.setStatus("PENDING");
        
        ExecutionResult executionResult = ExecutionResult.builder()
            .status("ACCEPTED")
            .executionTimeMs(250L)
            .memoryUsedKb(15000L)
            .build();
        
        when(userService.getUserByUsername("testuser")).thenReturn(userDTO);
        when(submissionService.submitSolution(anyLong(), anyLong(), anyString(), anyString()))
            .thenReturn(submissionDTO);
        when(codeExecutionService.executeCode(anyLong(), anyString(), anyString()))
            .thenReturn(executionResult);
        when(submissionService.getSubmissionById(1L)).thenReturn(submissionDTO);
        
        // When & Then
        mockMvc.perform(post("/api/v1/submissions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.submission").exists())
            .andExpect(jsonPath("$.executionResult").exists());
    }
    
    @Test
    @WithMockUser(username = "testuser")
    void submitSolution_InvalidRequest_BadRequest() throws Exception {
        // Given - missing required fields
        SubmissionRequest request = new SubmissionRequest();
        // Missing problemId, code, language
        
        // When & Then
        mockMvc.perform(post("/api/v1/submissions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }
    
    @Test
    @WithMockUser
    void getSubmissionById_Success() throws Exception {
        // Given
        SubmissionDTO submission = new SubmissionDTO();
        submission.setId(1L);
        submission.setUserId(1L);
        submission.setProblemId(1L);
        submission.setStatus("ACCEPTED");
        
        when(submissionService.getSubmissionById(1L)).thenReturn(submission);
        
        // When & Then
        mockMvc.perform(get("/api/v1/submissions/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.status").value("ACCEPTED"));
    }
    
    @Test
    @WithMockUser
    void getSubmissionsByUser_Success() throws Exception {
        // Given
        SubmissionDTO submission1 = new SubmissionDTO();
        submission1.setId(1L);
        submission1.setUserId(1L);
        submission1.setStatus("ACCEPTED");
        
        SubmissionDTO submission2 = new SubmissionDTO();
        submission2.setId(2L);
        submission2.setUserId(1L);
        submission2.setStatus("WRONG_ANSWER");
        
        List<SubmissionDTO> submissions = Arrays.asList(submission1, submission2);
        Page<SubmissionDTO> submissionPage = new PageImpl<>(submissions);
        
        when(submissionService.getSubmissionsByUserPageable(eq(1L), any(Pageable.class)))
            .thenReturn(submissionPage);
        
        // When & Then
        mockMvc.perform(get("/api/v1/submissions/user/1?page=0&size=20"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.submissions[0].status").value("ACCEPTED"))
            .andExpect(jsonPath("$.submissions[1].status").value("WRONG_ANSWER"))
            .andExpect(jsonPath("$.totalItems").value(2));
    }
    
    @Test
    @WithMockUser
    void getAcceptedSubmissions_Success() throws Exception {
        // Given
        SubmissionDTO submission1 = new SubmissionDTO();
        submission1.setId(1L);
        submission1.setStatus("ACCEPTED");
        
        SubmissionDTO submission2 = new SubmissionDTO();
        submission2.setId(2L);
        submission2.setStatus("ACCEPTED");
        
        List<SubmissionDTO> acceptedSubmissions = Arrays.asList(submission1, submission2);
        when(submissionService.getAcceptedSubmissionsByUser(1L)).thenReturn(acceptedSubmissions);
        
        // When & Then
        mockMvc.perform(get("/api/v1/submissions/user/1/accepted"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].status").value("ACCEPTED"))
            .andExpect(jsonPath("$[1].status").value("ACCEPTED"));
    }
    
    @Test
    @WithMockUser
    void checkIfSolved_True() throws Exception {
        // Given
        when(submissionService.hasUserSolvedProblem(1L, 1L)).thenReturn(true);
        
        // When & Then
        mockMvc.perform(get("/api/v1/submissions/user/1/problem/1/solved"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.solved").value(true));
    }
    
    @Test
    @WithMockUser
    void checkIfSolved_False() throws Exception {
        // Given
        when(submissionService.hasUserSolvedProblem(1L, 1L)).thenReturn(false);
        
        // When & Then
        mockMvc.perform(get("/api/v1/submissions/user/1/problem/1/solved"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.solved").value(false));
    }
}
