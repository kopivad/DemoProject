package com.kopivad.testingsystem.repository;

import com.kopivad.testingsystem.model.Quiz;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface QuizRepository {
    List<Quiz> findAll();

    Quiz saveQuiz(Quiz quiz);

    Quiz findQuizById(Long id);

    void updateQuiz(Quiz quiz);

    List<Quiz> findAllByAuthorId(Long id);
}
