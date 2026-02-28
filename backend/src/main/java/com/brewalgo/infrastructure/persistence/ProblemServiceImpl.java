package com.brewalgo.infrastructure.persistence;

import com.brewalgo.application.dto.ProblemDTO;
import com.brewalgo.application.service.ProblemService;
import com.brewalgo.domain.entity.Problem;
import com.brewalgo.domain.exception.BusinessException;
import com.brewalgo.domain.repository.ProblemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProblemServiceImpl implements ProblemService {
    
    private final ProblemRepository problemRepository;
    
    @Override
    @Transactional
    public ProblemDTO createProblem(ProblemDTO problemDTO) {
        log.info("Creating new problem: {}", problemDTO.getTitle());
        
        Problem problem = Problem.builder()
            .slug(generateSlug(problemDTO.getTitle()))
            .title(problemDTO.getTitle())
            .description(problemDTO.getDescription())
            .difficulty(Problem.Difficulty.valueOf(problemDTO.getDifficulty()))
            .baseScore(problemDTO.getBaseScore() != null ? problemDTO.getBaseScore() : 100)
            .acceptanceRate(0)
            .totalSubmissions(0)
            .successfulSubmissions(0)
            .createdAt(LocalDateTime.now())
            .hints(problemDTO.getHints())
            .tags(problemDTO.getTags())
            .build();
        
        Problem savedProblem = problemRepository.save(problem);
        log.info("Problem created: {}", savedProblem.getId());
        
        return ProblemDTO.fromEntity(savedProblem);
    }
    
    @Override
    @Transactional(readOnly = true)
    public ProblemDTO getProblemById(Long id) {
        Problem problem = problemRepository.findById(id)
            .orElseThrow(() -> BusinessException.notFound("Problem", id));
        return ProblemDTO.fromEntity(problem);
    }
    
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "problems", key = "#slug")
    public ProblemDTO getProblemBySlug(String slug) {
        Problem problem = problemRepository.findBySlug(slug)
            .orElseThrow(() -> BusinessException.notFound("Problem", slug));
        return ProblemDTO.fromEntity(problem);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ProblemDTO> getAllProblems() {
        return problemRepository.findAll()
            .stream()
            .map(ProblemDTO::fromEntity)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<ProblemDTO> getAllProblemsPageable(Pageable pageable) {
        Page<Problem> problemPage = problemRepository.findAll(pageable);
        return problemPage.map(ProblemDTO::fromEntity);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ProblemDTO> getProblemsByDifficulty(Problem.Difficulty difficulty) {
        return problemRepository.findByDifficulty(difficulty)
            .stream()
            .map(ProblemDTO::fromEntity)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ProblemDTO> getProblemsByTag(String tag) {
        return problemRepository.findByTag(tag)
            .stream()
            .map(ProblemDTO::fromEntity)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ProblemDTO> getMostAttemptedProblems(int limit) {
        return problemRepository.findMostAttemptedProblems(PageRequest.of(0, limit))
            .stream()
            .map(ProblemDTO::fromEntity)
            .collect(Collectors.toList());
    }
    
    @Override
    @Transactional
    @CacheEvict(value = "problems", key = "#id")
    public ProblemDTO updateProblem(Long id, ProblemDTO problemDTO) {
        Problem problem = problemRepository.findById(id)
            .orElseThrow(() -> BusinessException.notFound("Problem", id));
        
        if (problemDTO.getTitle() != null) {
            problem.setTitle(problemDTO.getTitle());
            problem.setSlug(generateSlug(problemDTO.getTitle()));
        }
        if (problemDTO.getDescription() != null) {
            problem.setDescription(problemDTO.getDescription());
        }
        if (problemDTO.getDifficulty() != null) {
            problem.setDifficulty(Problem.Difficulty.valueOf(problemDTO.getDifficulty()));
        }
        if (problemDTO.getHints() != null) {
            problem.setHints(problemDTO.getHints());
        }
        if (problemDTO.getTags() != null) {
            problem.setTags(problemDTO.getTags());
        }
        
        Problem updatedProblem = problemRepository.save(problem);
        return ProblemDTO.fromEntity(updatedProblem);
    }
    
    @Override
    @Transactional
    public void deleteProblem(Long id) {
        if (!problemRepository.existsById(id)) {
            throw BusinessException.notFound("Problem", id);
        }
        problemRepository.deleteById(id);
        log.info("Problem deleted: {}", id);
    }
    
    @Override
    @Transactional
    public void updateProblemStats(Long problemId, boolean isAccepted) {
        Problem problem = problemRepository.findById(problemId)
            .orElseThrow(() -> BusinessException.notFound("Problem", problemId));
        
        problem.setTotalSubmissions(problem.getTotalSubmissions() + 1);
        
        if (isAccepted) {
            problem.setSuccessfulSubmissions(problem.getSuccessfulSubmissions() + 1);
        }
        
        int acceptanceRate = (int) ((problem.getSuccessfulSubmissions() * 100.0) / problem.getTotalSubmissions());
        problem.setAcceptanceRate(acceptanceRate);
        
        problemRepository.save(problem);
        log.info("Updated stats for problem {}: acceptance rate = {}%", problemId, acceptanceRate);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ProblemDTO> getRecommendedProblems(Long userId, int limit) {
        // TODO: Implement recommendation algorithm based on user's solved problems and difficulty
        // For now, return problems ordered by difficulty
        return problemRepository.findAll(PageRequest.of(0, limit))
            .stream()
            .map(ProblemDTO::fromEntity)
            .collect(Collectors.toList());
    }
    
    private String generateSlug(String title) {
        return title.toLowerCase()
            .replaceAll("[^a-z0-9\\s-]", "")
            .replaceAll("\\s+", "-")
            .replaceAll("-+", "-")
            .trim();
    }
}