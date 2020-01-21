package com.kopivad.testingsystem.service;

import com.kopivad.testingsystem.form.QuestionForm;
import com.kopivad.testingsystem.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface QuestionService {
    Question saveQuestion(Question question);
    Page<Question> getQuestionByQuizId(Long id, Pageable pageable);
    Question getQuestionById(Long questionId);
    List<Question> getAllQuestions();
    List<Question> getQuestionByQuizId(Long id);
    Question updateQuestion(Question questionForUpdate);
    long countByQuizId(Long quizId);
    Question saveQuestion(QuestionForm form);
    Question updateQuestion(QuestionForm form);
    void deleteQuestion(Long questionId);
}
