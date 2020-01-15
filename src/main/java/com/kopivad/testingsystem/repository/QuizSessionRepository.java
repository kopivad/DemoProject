package com.kopivad.testingsystem.repository;

import com.kopivad.testingsystem.domain.QuizSession;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface QuizSessionRepository {
    QuizSession saveQuizSession(QuizSession quizSession);
    QuizSession findQuizSessionById(Long sessionId);
    List<QuizSession> findQuizSessionByUserId(Long id);
    List<QuizSession> findAllByQuizId(Long id);
}
