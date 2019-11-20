package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.model.Answer;
import com.kopivad.testingsystem.repository.AnswerRepository;
import com.kopivad.testingsystem.service.AnswerService;
import org.springframework.stereotype.Service;

@Service
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;

    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public void saveAnswer(Answer answer) {
        answerRepository.save(answer);
    }
}
