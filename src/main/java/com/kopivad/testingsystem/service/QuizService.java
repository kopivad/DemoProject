package com.kopivad.testingsystem.service;

import com.kopivad.testingsystem.model.Quiz;

import java.util.List;

public interface QuizService {
    void saveQuiz(Quiz quiz);

    List<Quiz> getAllQuizzes();
}
