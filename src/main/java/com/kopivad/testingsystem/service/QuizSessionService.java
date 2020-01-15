package com.kopivad.testingsystem.service;

import com.kopivad.testingsystem.domain.Quiz;
import com.kopivad.testingsystem.domain.QuizSession;
import com.kopivad.testingsystem.domain.User;

public interface QuizSessionService {
    QuizSession saveQuizSession(QuizSession quizSession);
    QuizSession getQuizSessionById(Long sessionId);
    QuizSession createSession(Quiz quiz, User user);
}
