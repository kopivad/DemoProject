package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.domain.Quiz;
import com.kopivad.testingsystem.domain.QuizSession;
import com.kopivad.testingsystem.domain.User;
import com.kopivad.testingsystem.repository.QuizSessionRepository;
import com.kopivad.testingsystem.service.QuizSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class QuizSessionServiceImpl implements QuizSessionService {
    private final QuizSessionRepository sessionRepository;

    @Override
    public QuizSession saveQuizSession(QuizSession quizSession) {
        return sessionRepository.saveQuizSession(quizSession);
    }

    @Override
    public QuizSession getQuizSessionById(Long sessionId) {
        return sessionRepository.findQuizSessionById(sessionId);
    }

    @Override
    public QuizSession createSession(Quiz quiz, User user) {
        return saveQuizSession(
                QuizSession
                        .builder()
                        .user(user)
                        .quiz(quiz)
                        .created(new Timestamp(System.currentTimeMillis()))
                        .build());
    }


}
