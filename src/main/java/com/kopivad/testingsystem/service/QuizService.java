package com.kopivad.testingsystem.service;

import com.kopivad.testingsystem.form.QuizForm;
import com.kopivad.testingsystem.model.Question;
import com.kopivad.testingsystem.model.Quiz;
import com.kopivad.testingsystem.model.User;

import java.util.List;

public interface QuizService {
    Quiz saveQuiz(Quiz quiz);

    List<Quiz> getAllQuizzes();

    Quiz getQuizById(Long id);

    long getCountOfCorrectAnswersBySessionId(Long sessionId);

    long getTotalOfAnswersBySessionId(Long sessionId);

    float getPercentageOfCorrectAnswers(long obtained, long total);

    List<Question> getQuestionsBySessionId(Long sessionId);

    List<Quiz> getAllQuizzesByUserId(Long id);

    Long startQuiz(Long id, User user);

    Quiz saveQuiz(QuizForm quizForm);

    Quiz updateQuiz(QuizForm quizForm);

    Quiz updateQuiz(Quiz quizForUpdate);
}
