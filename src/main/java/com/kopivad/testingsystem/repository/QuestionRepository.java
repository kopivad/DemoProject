package com.kopivad.testingsystem.repository;

import com.kopivad.testingsystem.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface QuestionRepository {
    Page<Question> findAllByQuizId(Long quizId, Pageable pageable);

    List<Question> findAll();

    List<Question> findAllByQuizId(Long id);

    Question saveQuestion(Question question);

    Question findQuestionById(Long questionId);

    void updateQuestion(Question question);

    long countByQuizId(Long quizId);
}
