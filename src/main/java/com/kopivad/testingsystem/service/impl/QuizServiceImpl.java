package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.domain.Quiz;
import com.kopivad.testingsystem.domain.User;
import com.kopivad.testingsystem.form.QuizForm;
import com.kopivad.testingsystem.repository.QuizRepository;
import com.kopivad.testingsystem.service.MailService;
import com.kopivad.testingsystem.service.QuizService;
import com.kopivad.testingsystem.service.QuizSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

import static java.lang.System.currentTimeMillis;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final QuizSessionService quizSessionService;
    private final MailService mailService;

    @Override
    public Quiz saveQuiz(Quiz quiz) {
        quiz.setActive(true);
        quiz.setCreated(new Timestamp(currentTimeMillis()));
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
        return quizRepository.updateQuiz(quiz);
    }

    @Override
    public Quiz saveQuiz(QuizForm quizForm) {
        Quiz quiz = getQuizFromForm(quizForm);
        quiz.setAuthor(User.builder().id(quizForm.getAuthorId()).build());
        return this.saveQuiz(quiz);
    }

    @Override
    public List<Quiz> getAllQuizzesByUserId(Long id) {
        return quizRepository.findAllByAuthorId(id);
    }

    @Override
    public void shareQuiz(Long quizId, String email) {
        String subject = "Quiz invation";
        String text = String.format("Hello, you have invation on Quizzes App, http://localhost:8080/quiz/%d", quizId);
        mailService.sendMassage(email, subject, text);
    }

    public Quiz getQuizFromForm(QuizForm form) {
        return Quiz
                .builder()
                .id(form.getQuizId())
                .description(form.getDescription())
                .title(form.getTitle())
                .build();

    }

    public void deleteQuiz(Long id) {
        quizRepository.deleteQuizById(id);
    }
}
