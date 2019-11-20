package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.model.Question;
import com.kopivad.testingsystem.repository.QuestionRepository;
import com.kopivad.testingsystem.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }


    @Override
    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }

    @Override
    public Page<Question> getQuestionByQuizId(Long id, Pageable pageable) {
        return questionRepository.findAllByQuizId(id, pageable);
    }

    @Override
    public Optional<Question> getQuestionById(Long questionId) {
        return questionRepository.findById(questionId);
    }
}
