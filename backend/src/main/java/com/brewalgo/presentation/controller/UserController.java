package com.brewalgo.presentation.controller;

import com.brewalgo.application.dto.AuthRequestDTO;
import com.brewalgo.application.dto.AuthResponseDTO;
import com.brewalgo.application.dto.UserDTO;
import com.brewalgo.application.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class UserController {
    
    private final UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody AuthRequestDTO request) {
        log.info("POST /api/users/register - username: {}", request.getUsername());
        AuthResponseDTO response = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody Map<String, String> credentials) {
        log.info("POST /api/users/login - username: {}", credentials.get("username"));
        String username = credentials.get("username");
        String password = credentials.get("password");
        AuthResponseDTO response = userService.login(username, password);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        log.info("GET /api/users/{}", id);
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        log.info("GET /api/users/username/{}", username);
        UserDTO user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        log.info("PUT /api/users/{}", id);
        UserDTO updated = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updated);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("DELETE /api/users/{}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/top")
    public ResponseEntity<List<UserDTO>> getTopUsers(
            @RequestParam(defaultValue = "10") int limit) {
        log.info("GET /api/users/top?limit={}", limit);
        List<UserDTO> users = userService.getTopUsersByRating(limit);
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/rating/{minRating}")
    public ResponseEntity<List<UserDTO>> getUsersByMinRating(@PathVariable int minRating) {
        log.info("GET /api/users/rating/{}", minRating);
        List<UserDTO> users = userService.getUsersByMinimumRating(minRating);
        return ResponseEntity.ok(users);
    }
    
    @GetMapping("/exists/username/{username}")
    public ResponseEntity<Map<String, Boolean>> checkUsernameExists(@PathVariable String username) {
        log.info("GET /api/users/exists/username/{}", username);
        boolean exists = userService.existsByUsername(username);
        return ResponseEntity.ok(Map.of("exists", exists));
    }
    
    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Map<String, Boolean>> checkEmailExists(@PathVariable String email) {
        log.info("GET /api/users/exists/email/{}", email);
        boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok(Map.of("exists", exists));
    }
    
    @PatchMapping("/{id}/rating")
    public ResponseEntity<Void> updateRating(
            @PathVariable Long id,
            @RequestBody Map<String, Integer> ratingUpdate) {
        log.info("PATCH /api/users/{}/rating", id);
        userService.updateUserRating(id, ratingUpdate.get("rating"));
        return ResponseEntity.ok().build();
    }
}