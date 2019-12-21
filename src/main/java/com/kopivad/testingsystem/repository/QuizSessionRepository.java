package com.kopivad.testingsystem.repository;

import com.kopivad.testingsystem.model.QuizSession;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface QuizSessionRepository {
    QuizSession saveQuizSession(QuizSession quizSession);

    QuizSession findQuizSessionById(Long sessionId);
}
