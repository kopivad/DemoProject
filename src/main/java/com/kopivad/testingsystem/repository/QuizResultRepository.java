package com.kopivad.testingsystem.repository;

import com.kopivad.testingsystem.domain.QuizResult;

import java.util.List;

public interface QuizResultRepository {
    QuizResult saveQuizResult(QuizResult quizResult);
    QuizResult findBySessionId(Long sessionId);
    List<QuizResult> getAllQuizResultsByUserId(Long id);
    boolean isResultExist(Long sessionId);
    List<QuizResult> findAllBySessionId(Long id);
    List<QuizResult> findAllByUserId(Long id);
}
