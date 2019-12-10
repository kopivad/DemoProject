package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.model.Question;
import com.kopivad.testingsystem.repository.QuestionRepository;
import com.kopivad.testingsystem.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    private QuestionRepository questionRepository;

    @Override
    public void saveQuestion(Question question) {
        questionRepository.saveQuestion(question);
    }

    @Override
    public Page<Question> getQuestionByQuizId(Long id, Pageable pageable) {
        return questionRepository.findAllByQuizId(id, pageable);
    }

    @Override
    public Question getQuestionById(Long questionId) {
        return questionRepository.findQuestionById(questionId);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public List<Question> getQuestionByQuizId(Long id) {
        return questionRepository.findAllByQuizId(id);
    }

    @Override
    public void updateQuestion(Question questionForUpdate) {
        questionRepository.updateQuestion(questionForUpdate);
    }

    @Override
    public long countByQuizId(Long quizId) {
        return questionRepository.countByQuizId(quizId);
    }

    @Autowired
    public void setQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }
}
