package com.kopivad.testingsystem.service;

import com.kopivad.testingsystem.domain.QuizResult;
import com.kopivad.testingsystem.domain.User;

import java.util.List;

public interface QuizResultService {
    QuizResult saveQuizResult(Long sessionId, User user);
    long getCountOfCorrectAnswersBySessionId(Long sessionId);
    long getTotalOfAnswersBySessionId(Long sessionId);
    float getPercentageOfCorrectAnswers(long obtained, long total);
    QuizResult getQuizResultBySessionId(Long sessionId);
    List<QuizResult> getQuizResultByUserId(Long id);
    List<QuizResult> getAllQuizResultBySessionId(Long id);
}
