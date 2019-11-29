package com.kopivad.testingsystem.repository;

import com.kopivad.testingsystem.model.QuizSession;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface QuizSessionRepository {
    QuizSession findByCode(String code);

    QuizSession save(QuizSession quizSession);
}
