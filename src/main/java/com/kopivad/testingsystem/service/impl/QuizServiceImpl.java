package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.form.QuizForm;
import com.kopivad.testingsystem.model.*;
import com.kopivad.testingsystem.repository.QuizRepository;
import com.kopivad.testingsystem.service.QuizService;
import com.kopivad.testingsystem.service.QuizSessionService;
import com.kopivad.testingsystem.service.UserResponseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final QuizSessionService quizSessionService;
    private final UserResponseService responseService;

    @Override
    public Quiz saveQuiz(Quiz quiz) {
        return quizRepository.saveQuiz(quiz);
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    @Override
    public Quiz getQuizById(Long id) {
        return quizRepository.findQuizById(id);
    }

    @Override
    public long getCountOfCorrectAnswersBySessionId(Long sessionId) {
        return responseService
                .getAllResponseBySessionId(sessionId)
                .stream()
                .filter(response -> response.getAnswer().isRight())
                .count();
    }

    @Override
    public long getTotalOfAnswersBySessionId(Long sessionId) {
        return responseService
                .getAllResponseBySessionId(sessionId)
                .size();
    }

    @Override
    public float getPercentageOfCorrectAnswers(long obtained, long total) {
        return (float) obtained * 100 / total;
    }

    @Override
    public List<Question> getQuestionsBySessionId(Long sessionId) {
        return responseService
                .getAllResponseBySessionId(getTotalOfAnswersBySessionId(sessionId))
                .stream()
                .map(UserResponce::getQuestion)
                .collect(Collectors.toList());
    }

    @Override
    public List<Quiz> getAllQuizzesByUserId(Long id) {
        return quizRepository.findAllByAuthorId(id);
    }

    @Override
    public Long startQuiz(Long id, User user) {
        Quiz quiz = getQuizById(id);
        return quizSessionService.createSession(quiz, user).getId();
    }

    @Override
    public Quiz saveQuiz(QuizForm form) {
        return this.saveQuiz(getQuizFromForm(form));
    }

    @Override
    public Quiz updateQuiz(QuizForm quizForm) {
        Quiz quizForUpdate = quizRepository.findQuizById(quizForm.getQuizId());
        quizForUpdate.setTitle(quizForm.getTitle());
        quizForUpdate.setDescription(quizForm.getDescription());
        return this.updateQuiz(quizForUpdate);
    }

    @Override
    public Quiz updateQuiz(Quiz quiz) {
        return quizRepository.updateQuiz(quiz);
    }

    public Quiz getQuizFromForm(QuizForm form) {
        return Quiz
                .builder()
                .id(form.getQuizId())
                .description(form.getDescription())
                .title(form.getTitle())
                .build();

    }
}
