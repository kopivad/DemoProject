package com.kopivad.testingsystem.service;

import com.kopivad.testingsystem.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionService {
    Page<Question> getQuestionsByQuizId(Long id, Pageable pageable);
}
