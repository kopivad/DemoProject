package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.model.Question;
import com.kopivad.testingsystem.model.Quiz;
import com.kopivad.testingsystem.model.UserQuestionResponse;
import com.kopivad.testingsystem.repository.QuizRepository;
import com.kopivad.testingsystem.service.QuizService;
import com.kopivad.testingsystem.service.UserQuestionResponseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final UserQuestionResponseService responseService;

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

    @Override
    public List<Question> getQuestionsBySessionCode(String code) {
        Stream<UserQuestionResponse> stream = responseService.getAllResponseByCode(code).stream();
        List<Question> questions = stream.map(UserQuestionResponse::getQuestion).collect(Collectors.toList());
        return questions;
    }

    @Override
    public List<Quiz> getAllQuizzesByUserId(Long id) {
        return quizRepository.findAllByAuthorId(id);
    }
}
