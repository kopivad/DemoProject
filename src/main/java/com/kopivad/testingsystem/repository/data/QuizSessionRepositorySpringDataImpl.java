package com.kopivad.testingsystem.repository.data;

import com.kopivad.testingsystem.model.QuizSession;
import com.kopivad.testingsystem.repository.QuizSessionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizSessionRepositorySpringDataImpl extends QuizSessionRepository, JpaRepository<QuizSession, String> {
    @Override
    QuizSession findByCode(String code);

    @Override
    QuizSession save(QuizSession quizSession);
}
