package com.kopivad.testingsystem.service;

import com.kopivad.testingsystem.form.AnswerForm;
import com.kopivad.testingsystem.model.Answer;

import java.util.List;

public interface AnswerService {
    void saveAnswer(Answer answer);

    List<Answer> getAnswersByQuestionId(Long id);

    Answer getAnswerById(Long id);

    List<Answer> getAllAnswers();

    void updateAnswer(Answer answer);

    void deleteAnswerById(Long id);

    void saveAnswerFromForm(AnswerForm answerForm);
}
