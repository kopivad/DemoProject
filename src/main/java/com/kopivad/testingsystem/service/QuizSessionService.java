package com.kopivad.testingsystem.service;

import com.kopivad.testingsystem.model.Quiz;
import com.kopivad.testingsystem.model.QuizSession;
import com.kopivad.testingsystem.model.User;

public interface QuizSessionService {
    QuizSession saveQuizSession(QuizSession quizSession);

    QuizSession getQuizSessionById(Long sessionId);

    QuizSession createSession(Quiz quiz, User user);
}
