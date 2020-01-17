package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.domain.QuizResult;
import com.kopivad.testingsystem.domain.QuizSession;
import com.kopivad.testingsystem.domain.User;
import com.kopivad.testingsystem.domain.UserResponse;
import com.kopivad.testingsystem.repository.QuizResultRepository;
import com.kopivad.testingsystem.repository.QuizSessionRepository;
import com.kopivad.testingsystem.repository.UserResponseRepository;
import com.kopivad.testingsystem.repository.jooq.RepositoryUtils;
import com.kopivad.testingsystem.service.QuizResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizResultServiceImpl implements QuizResultService {
    private final QuizResultRepository resultRepository;
    private final QuizSessionRepository sessionRepository;
    private final UserResponseRepository responseRepository;
    private final RepositoryUtils repositoryUtils;

    @Override
    public QuizResult saveQuizResult(Long sessionId, User user) {
        if (!resultRepository.isResultExist(sessionId)) {
            QuizSession session = repositoryUtils.getFullQuizSession(sessionRepository.findQuizSessionById(sessionId));
            long countOfCorrect = getCountOfCorrectAnswersBySessionId(sessionId);
            long totalAnswers = getTotalOfAnswersBySessionId(sessionId);
            return resultRepository.saveQuizResult(
                    QuizResult
                            .builder()
                            .session(session)
                            .countOfCorrect(countOfCorrect)
                            .totalAnswers(totalAnswers)
                            .user(user)
                            .rating(getPercentageOfCorrectAnswers(countOfCorrect, totalAnswers))
                            .build()
            );
        }
        return resultRepository.findBySessionId(sessionId);
    }

    @Override
    public long getCountOfCorrectAnswersBySessionId(Long sessionId) {
        List<UserResponse> sessionFromDB = responseRepository
                .findAllByQuizSessionId(sessionId)
                .stream()
                .map(repositoryUtils::getFullUserResponse)
                .collect(Collectors.toList());
        return sessionFromDB
                .stream()
                .filter(response -> response.getAnswer().isRight())
                .count();
    }

    @Override
    public long getTotalOfAnswersBySessionId(Long sessionId) {
        List<UserResponse> sessionFromDB = responseRepository
                .findAllByQuizSessionId(sessionId)
                .stream()
                .map(repositoryUtils::getFullUserResponse)
                .collect(Collectors.toList());
        return sessionFromDB
                .size();
    }

    @Override
    public float getPercentageOfCorrectAnswers(long obtained, long total) {
        return (float) obtained * 100 / total;
    }

    @Override
    public QuizResult getQuizResultBySessionId(Long sessionId) {
        QuizResult sessionFromDB = resultRepository.findBySessionId(sessionId);
        return repositoryUtils.getFullQuizResult(sessionFromDB);
    }

    @Override
    public List<QuizResult> getQuizResultByUserId(Long id) {
        List<QuizResult> quizResultsFromDB = resultRepository.getAllQuizResultsByUserId(id);
        return quizResultsFromDB
                .stream()
                .map(repositoryUtils::getFullQuizResult)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuizResult> getAllQuizResultBySessionId(Long id) {
        List<QuizResult> resultsFromDB = resultRepository.findAllBySessionId(id);
        return resultsFromDB
                .stream()
                .map(repositoryUtils::getFullQuizResult)
                .collect(Collectors.toList());
    }
}
