package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.form.AnswerForm;
import com.kopivad.testingsystem.model.Answer;
import com.kopivad.testingsystem.model.Question;
import com.kopivad.testingsystem.repository.AnswerRepository;
import com.kopivad.testingsystem.repository.QuestionRepository;
import com.kopivad.testingsystem.service.AnswerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    @Override
    public Answer saveAnswer(Answer answer) {
        return answerRepository.saveAnswer(answer);
    }

    public List<Answer> getAnswersByQuestionId(Long id) {
        return answerRepository.findAllByQuestionId(id);
    }

    public Answer getAnswerById(Long id) {
        return answerRepository.findAnswerById(id);
    }

    @Override
    public List<Answer> getAllAnswers() {
        return answerRepository.findAll();
    }

    @Override
    public Answer updateAnswer(Answer answer) {
        return answerRepository.updateAnswer(answer);
    }

    @Override
    public void deleteAnswerById(Long id) {
        answerRepository.deleteAnswerById(id);
    }

    @Override
    public Answer saveAnswer(AnswerForm answerForm) {
        return this.saveAnswer(getAnswerFromAnswerForm(answerForm));
    }

    @Override
    public Answer updateAnswer(AnswerForm answerForm) {
        Answer answerForUpdate = answerRepository.findAnswerById(answerForm.getAnswerId());
        Question currentQuestion = questionRepository.findQuestionById(answerForm.getQuestionId());
        answerForUpdate.setRight(answerForm.getIsRight() != null);
        answerForUpdate.setText(answerForm.getText());
        answerForUpdate.setQuestion(currentQuestion);
        return this.updateAnswer(answerForUpdate);
    }

    public Answer getAnswerFromAnswerForm(AnswerForm answerForm) {
        return Answer
                .builder()
                .text(answerForm.getText())
                .question(questionRepository.findQuestionById(answerForm.getQuestionId()))
                .isRight(answerForm.getIsRight() != null)
                .id(answerForm.getAnswerId())
                .build();
    }


}
