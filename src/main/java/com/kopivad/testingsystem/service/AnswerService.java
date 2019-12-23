package com.kopivad.testingsystem.service;

import com.kopivad.testingsystem.form.AnswerForm;
import com.kopivad.testingsystem.model.Answer;

import java.util.List;

public interface AnswerService {
    Answer saveAnswer(Answer answer);

    List<Answer> getAnswersByQuestionId(Long id);

    Answer getAnswerById(Long id);

    List<Answer> getAllAnswers();

    Answer updateAnswer(Answer answer);

    void deleteAnswerById(Long id);

    Answer saveAnswer(AnswerForm answerForm);

    Answer updateAnswer(AnswerForm answerForm);
}
