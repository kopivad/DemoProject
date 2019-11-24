package com.kopivad.testingsystem.service;

import com.kopivad.testingsystem.model.Question;
import com.kopivad.testingsystem.model.Quiz;

import java.util.List;
import java.util.Optional;

public interface QuizService {
    void saveQuiz(Quiz quiz);

    List<Quiz> getAllQuizzes();

    Optional<Quiz> getQuizById(Long id);

    long getCountOfCorrectAnswersBySessionCode(String code);

    long getTotalOfAnswersBySessionCode(String code);

    float getPercentageOfCorrectAnswers(long obtained, long total);

    List<Question> getQuestionsBySessionCode(String code);
}
