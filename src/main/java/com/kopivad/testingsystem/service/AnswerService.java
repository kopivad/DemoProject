package com.kopivad.testingsystem.service;

import com.kopivad.testingsystem.model.Answer;

import java.util.List;
import java.util.Optional;

public interface AnswerService {
    void saveAnswer(Answer answer);

    List<Answer> getAnswersByQuestionId(Long id);

    Optional<Answer> getAnswerById(Long id);

    List<Answer> getAllAnswers();
}
