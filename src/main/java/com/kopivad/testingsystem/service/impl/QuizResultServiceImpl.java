package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.domain.QuizResult;
import com.kopivad.testingsystem.domain.QuizSession;
import com.kopivad.testingsystem.domain.User;
import com.kopivad.testingsystem.repository.QuizResultRepository;
import com.kopivad.testingsystem.repository.QuizSessionRepository;
import com.kopivad.testingsystem.repository.UserResponseRepository;
import com.kopivad.testingsystem.service.QuizResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizResultServiceImpl implements QuizResultService {
    private final QuizResultRepository resultRepository;
    private final QuizSessionRepository sessionRepository;
    private final UserResponseRepository responseRepository;

    @Override
    public QuizResult saveQuizResult(Long sessionId, User user) {
        if (!resultRepository.isResultExist(sessionId)) {
            QuizSession session = sessionRepository.findQuizSessionById(sessionId);
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
        return responseRepository
                .findAllByQuizSessionId(sessionId).stream()
                .filter(response -> response.getAnswer().isRight())
                .count();
    }

    @Override
    public long getTotalOfAnswersBySessionId(Long sessionId) {
        return responseRepository
                .findAllByQuizSessionId(sessionId)
                .size();
    }

    @Override
    public float getPercentageOfCorrectAnswers(long obtained, long total) {
        return (float) obtained * 100 / total;
    }

    @Override
    public QuizResult getQuizResultBySessionId(Long sessionId) {
        return resultRepository.findBySessionId(sessionId);
    }

    @Override
    public List<QuizResult> getQuizResultByUserId(Long id) {
        return resultRepository.getAllQuizResultsByUserId(id);
    }

    @Override
    public List<QuizResult> getAllQuizResultBySessionId(Long id) {
        return resultRepository.findAllBySessionId(id);
    }
}
