package com.kopivad.testingsystem.service;

import com.kopivad.testingsystem.form.QuizForm;
import com.kopivad.testingsystem.domain.Quiz;
import com.kopivad.testingsystem.domain.User;

import java.util.List;

public interface QuizService {
    Quiz saveQuiz(Quiz quiz);
    List<Quiz> getAllQuizzes();
    Quiz getQuizById(Long id);
    Long startQuiz(Long id, User user);
    Quiz updateQuiz(QuizForm quizForm);
    Quiz updateQuiz(Quiz quizForUpdate);
    Quiz saveQuiz(QuizForm quizForm, User user);
}
