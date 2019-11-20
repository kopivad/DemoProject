package com.kopivad.testingsystem.service;

import com.kopivad.testingsystem.model.Quiz;

import java.util.List;
import java.util.Optional;

public interface QuizService {
    void saveQuiz(Quiz quiz);

    List<Quiz> getAllQuizzes();

    Optional<Quiz> getQuizById(Long id);
}
