package com.kopivad.testingsystem.repository.data;

import com.kopivad.testingsystem.model.QuizSession;
import com.kopivad.testingsystem.repository.QuizSessionRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizSessionRepositorySpringDataImpl extends QuizSessionRepository, JpaRepository<QuizSession, Long> {
    @Override
    default QuizSession saveQuizSession(QuizSession quizSession) {
        return save(quizSession);
    }

    @Override
    QuizSession findQuizSessionById(Long id);
}
