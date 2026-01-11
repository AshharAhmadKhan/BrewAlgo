package com.brewalgo.infrastructure.persistence;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.brewalgo.infrastructure.security.JwtUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.brewalgo.application.dto.AuthRequestDTO;
import com.brewalgo.application.dto.AuthResponseDTO;
import com.brewalgo.application.dto.UserDTO;
import com.brewalgo.application.service.UserService;
import com.brewalgo.domain.entity.User;
import com.brewalgo.domain.exception.BusinessException;
import com.brewalgo.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    @Transactional
    public AuthResponseDTO register(AuthRequestDTO request) {
        log.info("Registering new user: {}", request.getUsername());

        if (existsByUsername(request.getUsername())) {
            throw BusinessException.badRequest("Username already exists");
        }

        if (existsByEmail(request.getEmail())) {
            throw BusinessException.badRequest("Email already exists");
        }

        User user = User.builder()
            .username(request.getUsername())
            .email(request.getEmail())
            .passwordHash(hashPassword(request.getPassword()))
            .rating(1000)
            .problemsSolved(0)
            .createdAt(LocalDateTime.now())
            .lastLoginAt(LocalDateTime.now())
            .role(User.UserRole.USER)
            .build();

        User savedUser = userRepository.save(user);
        log.info("User registered successfully: {}", savedUser.getId());

        UserDetails userDetails =
            userDetailsService.loadUserByUsername(savedUser.getUsername());
        String token = jwtUtil.generateToken(userDetails);

        return AuthResponseDTO.builder()
            .token(token)
            .user(UserDTO.fromEntity(savedUser))
            .message("Registration successful")
            .build();
    }

    @Override
    @Transactional
    public AuthResponseDTO login(String username, String password) {
        log.info("User login attempt: {}", username);

        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> BusinessException.unauthorized("Invalid credentials"));

        if (!verifyPassword(password, user.getPasswordHash())) {
            throw BusinessException.unauthorized("Invalid credentials");
        }

        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);

        log.info("User logged in successfully: {}", user.getId());

        UserDetails userDetails =
            userDetailsService.loadUserByUsername(user.getUsername());
        String token = jwtUtil.generateToken(userDetails);

        return AuthResponseDTO.builder()
            .token(token)
            .user(UserDTO.fromEntity(user))
            .message("Login successful")
            .build();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> BusinessException.notFound("User", id));
        return UserDTO.fromEntity(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> BusinessException.notFound("User", username));
        return UserDTO.fromEntity(user);
    }

    @Override
    @Transactional
    public UserDTO updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> BusinessException.notFound("User", id));

        if (userDTO.getEmail() != null && !userDTO.getEmail().equals(user.getEmail())) {
            if (existsByEmail(userDTO.getEmail())) {
                throw BusinessException.badRequest("Email already exists");
            }
            user.setEmail(userDTO.getEmail());
        }

        User updatedUser = userRepository.save(user);
        return UserDTO.fromEntity(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw BusinessException.notFound("User", id);
        }
        userRepository.deleteById(id);
        log.info("User deleted: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getTopUsersByRating(int limit) {
        return userRepository.findTopUsersByRating(PageRequest.of(0, limit))
            .stream()
            .map(UserDTO::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getUsersByMinimumRating(int minRating) {
        return userRepository.findUsersByMinimumRating(minRating)
            .stream()
            .map(UserDTO::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    @Transactional
    public void updateUserRating(Long userId, int newRating) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> BusinessException.notFound("User", userId));
        user.setRating(newRating);
        userRepository.save(user);
        log.info("Updated rating for user {}: {}", userId, newRating);
    }

    @Override
    @Transactional
    public void incrementProblemsSolved(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> BusinessException.notFound("User", userId));
        user.setProblemsSolved(user.getProblemsSolved() + 1);
        userRepository.save(user);
        log.info(
            "Incremented problems solved for user {}: {}",
            userId,
            user.getProblemsSolved()
        );
    }

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private boolean verifyPassword(String rawPassword, String hashedPassword) {
        return passwordEncoder.matches(rawPassword, hashedPassword);
    }
}
