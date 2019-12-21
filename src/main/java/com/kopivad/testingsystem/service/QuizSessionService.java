package com.kopivad.testingsystem.service;

import com.kopivad.testingsystem.model.QuizSession;

public interface QuizSessionService {
    QuizSession saveQuizSession(QuizSession quizSession);

    QuizSession getQuizSessionById(Long sessionId);
}
