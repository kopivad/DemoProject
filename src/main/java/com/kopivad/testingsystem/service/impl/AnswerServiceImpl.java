package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.model.Answer;
import com.kopivad.testingsystem.repository.AnswerRepository;
import com.kopivad.testingsystem.service.AnswerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;

    @Override
    public void saveAnswer(Answer answer) {
        answerRepository.saveAnswer(answer);
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
    public void updateAnswer(Answer answer) {
        answerRepository.updateAnswer(answer);
    }

    @Override
    public void deleteAnswerById(Long id) {
        answerRepository.deleteAnswerById(id);
    }


}
