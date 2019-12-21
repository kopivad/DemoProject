package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.model.Question;
import com.kopivad.testingsystem.model.Quiz;
import com.kopivad.testingsystem.model.UserResponce;
import com.kopivad.testingsystem.repository.QuizRepository;
import com.kopivad.testingsystem.service.QuizService;
import com.kopivad.testingsystem.service.UserResponseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final UserResponseService responseService;

    @Override
    public void saveQuiz(Quiz quiz) {
        quizRepository.saveQuiz(quiz);
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
}
