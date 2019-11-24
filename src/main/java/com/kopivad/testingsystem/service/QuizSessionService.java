package com.kopivad.testingsystem.service;

import com.kopivad.testingsystem.model.QuizSession;

public interface QuizSessionService {
    void createQuizSession(QuizSession quizSession);

    QuizSession getSessionByCode(String code);
}
