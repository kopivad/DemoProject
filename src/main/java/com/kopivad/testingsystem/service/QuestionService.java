package com.kopivad.testingsystem.service;

import com.kopivad.testingsystem.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    void saveQuestion(Question question);
    Page<Question> getQuestionByQuizId(Long id, Pageable pageable);
    Optional<Question> getQuestionById(Long questionId);
}
