package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.form.QuizForm;
import com.kopivad.testingsystem.domain.Quiz;
import com.kopivad.testingsystem.domain.User;
import com.kopivad.testingsystem.repository.QuizRepository;
import com.kopivad.testingsystem.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.System.currentTimeMillis;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final QuizSessionService quizSessionService;
    private final UserResponseService responseService;
    private final QuizResultService resultService;
    private final ServiceUtils serviceUtils;

    @Override
    public Quiz saveQuiz(Quiz quiz) {
        quiz.setActive(true);
        quiz.setCreated(new Timestamp(currentTimeMillis()));
        return quizRepository.saveQuiz(quiz);
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        List<Quiz> quizzesFromDB = quizRepository.findAll();
        return quizzesFromDB
                .stream()
                .map(serviceUtils::getFullQuiz)
                .collect(Collectors.toList());
    }

    @Override
    public Quiz getQuizById(Long id) {
        Quiz quizFromDB = quizRepository.findQuizById(id);
        return serviceUtils.getFullQuiz(quizFromDB);
    }

    @Override
    public Long startQuiz(Long id, User user) {
        Quiz quiz = getQuizById(id);
        return quizSessionService.createSession(quiz, user).getId();
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
        Quiz quizFromDB = quizRepository.updateQuiz(quiz);
        return serviceUtils.getFullQuiz(quizFromDB);
    }

    @Override
    public Quiz saveQuiz(QuizForm quizForm, User user) {
        Quiz quiz = getQuizFromForm(quizForm);
        quiz.setAuthor(user);
        return this.saveQuiz(quiz);
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
