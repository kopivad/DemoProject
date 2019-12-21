package com.kopivad.testingsystem.service;

import com.kopivad.testingsystem.model.Question;
import com.kopivad.testingsystem.model.Quiz;

import java.util.List;

public interface QuizService {
    void saveQuiz(Quiz quiz);

    List<Quiz> getAllQuizzes();

    Quiz getQuizById(Long id);

    long getCountOfCorrectAnswersBySessionId(Long sessionId);

    long getTotalOfAnswersBySessionId(Long sessionId);

    float getPercentageOfCorrectAnswers(long obtained, long total);

    List<Question> getQuestionsBySessionId(Long sessionId);

    List<Quiz> getAllQuizzesByUserId(Long id);
}
