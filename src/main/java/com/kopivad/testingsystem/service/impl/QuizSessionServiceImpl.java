package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.model.QuizSession;
import com.kopivad.testingsystem.repository.QuizSessionRepository;
import com.kopivad.testingsystem.service.QuizSessionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class QuizSessionServiceImpl implements QuizSessionService {
    private final QuizSessionRepository quizSessionRepository;

    @Override
    public void createQuizSession(QuizSession quizSession) {
         quizSessionRepository.save(quizSession);
    }

    @Override
    public QuizSession getSessionByCode(String code) {

        return quizSessionRepository.findByCode(code);
    }
}
