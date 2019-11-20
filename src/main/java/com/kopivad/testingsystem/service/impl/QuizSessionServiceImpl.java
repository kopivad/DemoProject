package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.model.QuizSession;
import com.kopivad.testingsystem.repository.QuizSessionRepository;
import com.kopivad.testingsystem.service.QuizSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizSessionServiceImpl implements QuizSessionService {
    private final QuizSessionRepository quizSessionRepository;

    @Autowired
    public QuizSessionServiceImpl(QuizSessionRepository quizSessionRepository) {
        this.quizSessionRepository = quizSessionRepository;
    }

    @Override
    public void createQuizSession(QuizSession quizSession) {
         quizSessionRepository.save(quizSession);
    }
}
