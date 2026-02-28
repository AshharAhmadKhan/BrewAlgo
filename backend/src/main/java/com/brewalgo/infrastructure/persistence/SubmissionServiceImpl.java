package com.brewalgo.infrastructure.persistence;

import com.brewalgo.application.dto.SubmissionDTO;
import com.brewalgo.application.service.ProblemService;
import com.brewalgo.application.service.SubmissionService;
import com.brewalgo.application.service.UserService;
import com.brewalgo.domain.entity.Contest;
import com.brewalgo.domain.entity.Problem;
import com.brewalgo.domain.entity.Submission;
import com.brewalgo.domain.entity.User;
import com.brewalgo.domain.exception.BusinessException;
import com.brewalgo.domain.repository.ContestRepository;
import com.brewalgo.domain.repository.ProblemRepository;
import com.brewalgo.domain.repository.SubmissionRepository;
import com.brewalgo.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final UserRepository userRepository;
    private final ProblemRepository problemRepository;
    private final ContestRepository contestRepository;
    private final ProblemService problemService;
    private final UserService userService;

    @Override
    @Transactional
    public SubmissionDTO submitSolution(Long userId, Long problemId, String code, String language) {
        log.info("User {} submitting solution for problem {}", userId, problemId);

        User user = userRepository.findById(userId)
            .orElseThrow(() -> BusinessException.notFound("User", userId));

        Problem problem = problemRepository.findById(problemId)
            .orElseThrow(() -> BusinessException.notFound("Problem", problemId));

        Submission submission = Submission.builder()
            .user(user)
            .problem(problem)
            .contest(null)
            .code(code)
            .language(Submission.Language.valueOf(language.toUpperCase()))
            .status(Submission.Status.PENDING)
            .submittedAt(LocalDateTime.now())
            .scoreAwarded(0)
            .build();

        Submission savedSubmission = submissionRepository.save(submission);
        log.info("Submission created: {}", savedSubmission.getId());

        return SubmissionDTO.fromEntity(savedSubmission);
    }

    @Override
    @Transactional
    public SubmissionDTO submitContestSolution(Long userId, Long problemId, Long contestId, String code, String language) {
        log.info("User {} submitting contest solution for problem {} in contest {}", userId, problemId, contestId);

        User user = userRepository.findById(userId)
            .orElseThrow(() -> BusinessException.notFound("User", userId));

        Problem problem = problemRepository.findById(problemId)
            .orElseThrow(() -> BusinessException.notFound("Problem", problemId));

        Contest contest = contestRepository.findById(contestId)
            .orElseThrow(() -> BusinessException.notFound("Contest", contestId));

        if (contest.getStatus() != Contest.Status.LIVE) {
            throw BusinessException.badRequest("Contest is not active");
        }

        Submission submission = Submission.builder()
            .user(user)
            .problem(problem)
            .contest(contest)
            .code(code)
            .language(Submission.Language.valueOf(language.toUpperCase()))
            .status(Submission.Status.PENDING)
            .submittedAt(LocalDateTime.now())
            .scoreAwarded(0)
            .build();

        Submission savedSubmission = submissionRepository.save(submission);
        log.info("Contest submission created: {}", savedSubmission.getId());

        return SubmissionDTO.fromEntity(savedSubmission);
    }

    @Override
    @Transactional(readOnly = true)
    public SubmissionDTO getSubmissionById(Long id) {
        Submission submission = submissionRepository.findById(id)
            .orElseThrow(() -> BusinessException.notFound("Submission", id));
        return SubmissionDTO.fromEntity(submission);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubmissionDTO> getSubmissionsByUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> BusinessException.notFound("User", userId));

        return submissionRepository.findByUser(user)
            .stream()
            .map(SubmissionDTO::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SubmissionDTO> getSubmissionsByUserPageable(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> BusinessException.notFound("User", userId));

        Page<Submission> submissionPage = submissionRepository.findByUser(user, pageable);
        return submissionPage.map(SubmissionDTO::fromEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubmissionDTO> getSubmissionsByProblem(Long problemId) {
        Problem problem = problemRepository.findById(problemId)
            .orElseThrow(() -> BusinessException.notFound("Problem", problemId));

        return submissionRepository.findByProblem(problem)
            .stream()
            .map(SubmissionDTO::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubmissionDTO> getSubmissionsByContest(Long contestId) {
        Contest contest = contestRepository.findById(contestId)
            .orElseThrow(() -> BusinessException.notFound("Contest", contestId));

        return submissionRepository.findByContest(contest)
            .stream()
            .map(SubmissionDTO::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubmissionDTO> getUserProblemSubmissions(Long userId, Long problemId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> BusinessException.notFound("User", userId));

        Problem problem = problemRepository.findById(problemId)
            .orElseThrow(() -> BusinessException.notFound("Problem", problemId));

        return submissionRepository.findByUserAndProblem(user, problem)
            .stream()
            .map(SubmissionDTO::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubmissionDTO> getAcceptedSubmissionsByUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> BusinessException.notFound("User", userId));

        return submissionRepository.findAcceptedSubmissionsByUser(user)
            .stream()
            .map(SubmissionDTO::fromEntity)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SubmissionDTO evaluateSubmission(Long submissionId) {
        log.info("Evaluating submission: {}", submissionId);

        Submission submission = submissionRepository.findById(submissionId)
            .orElseThrow(() -> BusinessException.notFound("Submission", submissionId));

        // TODO: Implement actual code execution and evaluation
        // For now, simulate evaluation
        boolean isAccepted = Math.random() > 0.3; // 70% success rate for demo

        Submission.Status status = isAccepted ? Submission.Status.ACCEPTED : Submission.Status.WRONG_ANSWER;
        Integer executionTime = isAccepted ? (int) (Math.random() * 1000) + 100 : null;
        Integer memoryUsed = isAccepted ? (int) (Math.random() * 50000) + 10000 : null;
        String errorMessage = isAccepted ? null : "Expected output does not match actual output";

        updateSubmissionStatus(submissionId, status, executionTime, memoryUsed, errorMessage);

        if (isAccepted) {
            int score = calculateScore(submission.getProblem().getId(), executionTime);
            submission.setScoreAwarded(score);
            submissionRepository.save(submission);

            // Update problem stats
            problemService.updateProblemStats(submission.getProblem().getId(), true);

            // Check if this is user's first solve for this problem
            if (!hasUserSolvedProblem(submission.getUser().getId(), submission.getProblem().getId())) {
                userService.incrementProblemsSolved(submission.getUser().getId());
            }
        } else {
            problemService.updateProblemStats(submission.getProblem().getId(), false);
        }

        return SubmissionDTO.fromEntity(submission);
    }

    // NEW METHOD: String version (implements interface)
    @Override
    @Transactional
    public void updateSubmissionStatus(Long submissionId, String status,
                                      Integer executionTime, Integer memoryUsed, String errorMessage) {
        Submission.Status statusEnum;
        try {
            statusEnum = Submission.Status.valueOf(status.toUpperCase().replace(" ", "_"));
        } catch (IllegalArgumentException e) {
            log.warn("Invalid status string: {}, defaulting to RUNTIME_ERROR", status);
            statusEnum = Submission.Status.RUNTIME_ERROR;
        }
        
        updateSubmissionStatus(submissionId, statusEnum, executionTime, memoryUsed, errorMessage);
    }

    // EXISTING METHOD: Enum version
    @Transactional
    public void updateSubmissionStatus(Long submissionId, Submission.Status status,
                                      Integer executionTime, Integer memoryUsed, String errorMessage) {
        Submission submission = submissionRepository.findById(submissionId)
            .orElseThrow(() -> BusinessException.notFound("Submission", submissionId));

        submission.setStatus(status);
        submission.setExecutionTimeMs(executionTime);
        submission.setMemoryUsedKb(memoryUsed);
        submission.setErrorMessage(errorMessage);

        submissionRepository.save(submission);
        log.info("Updated submission {} status to {}", submissionId, status);
    }

    @Override
    public int calculateScore(Long problemId, Integer executionTime) {
        Problem problem = problemRepository.findById(problemId)
            .orElseThrow(() -> BusinessException.notFound("Problem", problemId));

        int baseScore = problem.getBaseScore();

        if (executionTime == null) {
            return baseScore;
        }

        // Bonus for fast execution (max 50% bonus)
        int bonus = Math.max(0, (1000 - executionTime) / 20);
        return Math.min(baseScore + bonus, (int) (baseScore * 1.5));
    }

    @Override
    public boolean hasUserSolvedProblem(Long userId, Long problemId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> BusinessException.notFound("User", userId));

        Problem problem = problemRepository.findById(problemId)
            .orElseThrow(() -> BusinessException.notFound("Problem", problemId));

        return submissionRepository.countAcceptedSubmissions(user, problem) > 0;
    }
}