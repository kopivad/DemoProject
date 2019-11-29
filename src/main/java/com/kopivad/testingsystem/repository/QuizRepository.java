package com.kopivad.testingsystem.repository;

import com.kopivad.testingsystem.model.Quiz;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface QuizRepository {
    List<Quiz> findAll();

    Quiz save(Quiz quiz);

    Quiz findQuizById(Long id);
}
