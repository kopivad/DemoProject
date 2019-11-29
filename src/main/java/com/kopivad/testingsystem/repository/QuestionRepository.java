package com.kopivad.testingsystem.repository;

import com.kopivad.testingsystem.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface QuestionRepository {
    Page<Question> findAllByQuizId(Long quizId, Pageable pageable);
    List<Question> findAll();
    List<Question> findAllByQuizId(Long id);

    Question save(Question question);

    Question findQuestionById(Long questionId);
}
