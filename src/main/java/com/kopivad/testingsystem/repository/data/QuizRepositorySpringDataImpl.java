package com.kopivad.testingsystem.repository.data;

import com.kopivad.testingsystem.model.Quiz;
import com.kopivad.testingsystem.repository.QuizRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepositorySpringDataImpl extends QuizRepository, JpaRepository<Quiz, Long> {
    @Override
    List<Quiz> findAll();

    @Override
    Quiz save(Quiz quiz);

    @Override
    Quiz findQuizById(Long id);
}
