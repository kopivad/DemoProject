package com.kopivad.testingsystem.service;

import com.kopivad.testingsystem.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuestionService {
    void saveQuestion(Question question);

    Page<Question> getQuestionByQuizId(Long id, Pageable pageable);

    Question getQuestionById(Long questionId);

    List<Question> getAllQuestions();

    List<Question> getQuestionByQuizId(Long id);

    void updateQuestion(Question questionForUpdate);

    long countByQuizId(Long quizId);
}
