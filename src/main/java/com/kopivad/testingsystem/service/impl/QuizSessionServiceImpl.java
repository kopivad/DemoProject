package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.domain.Quiz;
import com.kopivad.testingsystem.domain.QuizSession;
import com.kopivad.testingsystem.domain.User;
import com.kopivad.testingsystem.repository.*;
import com.kopivad.testingsystem.repository.jooq.RepositoryUtils;
import com.kopivad.testingsystem.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class QuizSessionServiceImpl implements QuizSessionService {
    private final QuizSessionRepository sessionRepository;
    private final RepositoryUtils repositoryUtils;

    @Override
    public QuizSession saveQuizSession(QuizSession quizSession) {
        QuizSession sessionFromDB = sessionRepository.saveQuizSession(quizSession);
        return repositoryUtils.getFullQuizSession(sessionFromDB);
    }

    @Override
    public QuizSession getQuizSessionById(Long sessionId) {
        QuizSession sessionFromDB = sessionRepository.findQuizSessionById(sessionId);
        return repositoryUtils.getFullQuizSession(sessionFromDB);
    }

    @Override
    public QuizSession createSession(Quiz quiz, User user) {
        QuizSession quizFromDB = saveQuizSession(
                QuizSession
                        .builder()
                        .user(user)
                        .quiz(quiz)
                        .created(new Timestamp(System.currentTimeMillis()))
                        .build());
        return repositoryUtils.getFullQuizSession(quizFromDB);
    }


}
