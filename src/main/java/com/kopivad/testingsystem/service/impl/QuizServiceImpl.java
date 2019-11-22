package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.model.UserQuestionResponse;
import com.kopivad.testingsystem.repository.QuizRepository;
import com.kopivad.testingsystem.model.Quiz;
import com.kopivad.testingsystem.service.QuizService;
import com.kopivad.testingsystem.service.UserQuestionResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final UserQuestionResponseService responseService;

    @Autowired
    public QuizServiceImpl(QuizRepository quizRepository, UserQuestionResponseService responseService) {
        this.quizRepository = quizRepository;
        this.responseService = responseService;
    }

    @Override
    public void saveQuiz(Quiz quiz) {
        quizRepository.save(quiz);
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    @Override
    public Optional<Quiz> getQuizById(Long id) {
        return quizRepository.findById(id);
    }

    @Override
    public long getCountOfCorrectAnswersBySessionCode(String code) {
        List<UserQuestionResponse> allResponseByCode = responseService.getAllResponseByCode(code);
        long correctAmount = allResponseByCode.stream().filter(response -> response.getAnswer().isRight()).count();
        return correctAmount;
    }

    @Override
    public long getTotalOfAnswersBySessionCode(String code) {
        List<UserQuestionResponse> allResponseByCode = responseService.getAllResponseByCode(code);
        int totalResponses = allResponseByCode.size();
        return totalResponses;
    }

    @Override
    public float getPercentageOfCorrectAnswers(long obtained, long total) {
        float percentageOfCorrect = (float)obtained * 100 / total;
        return percentageOfCorrect;
    }
}
