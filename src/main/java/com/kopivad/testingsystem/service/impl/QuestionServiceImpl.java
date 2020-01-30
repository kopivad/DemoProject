package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.domain.Question;
import com.kopivad.testingsystem.domain.Quiz;
import com.kopivad.testingsystem.form.QuestionForm;
import com.kopivad.testingsystem.repository.QuestionRepository;
import com.kopivad.testingsystem.repository.QuizRepository;
import com.kopivad.testingsystem.service.QuestionService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;

    @Override
    public Question saveQuestion(Question question) {
        return questionRepository.saveQuestion(question);
    }

    @Override
    public Page<Question> getQuestionByQuizId(Long id, Pageable pageable) {
        return questionRepository.findAllByQuizId(id, pageable);
    }

    @SneakyThrows
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
    public Question updateQuestion(Question question) {
        return questionRepository.updateQuestion(question);
    }

    @Override
    public long countByQuizId(Long quizId) {
        return questionRepository.countByQuizId(quizId);
    }

    @Override
    public Question saveQuestion(QuestionForm form) {
        return this.saveQuestion(getQuestionFromForm(form));
    }

    @Override
    public Question updateQuestion(QuestionForm form) {
        Question questionForUpdate = questionRepository.findQuestionById(form.getQuestionId());
        Quiz currentQuiz = quizRepository.findQuizById(form.getQuizId());
        questionForUpdate.setTitle(form.getTitle());
        questionForUpdate.setQuiz(currentQuiz);
        return this.updateQuestion(questionForUpdate);
    }

    @Override
    public void deleteQuestion(Long questionId) {
        questionRepository.deleteQuestion(questionId);
    }

    private Question getQuestionFromForm(QuestionForm form) {
        return Question
                .builder()
                .title(form.getTitle())
                .quiz(quizRepository.findQuizById(form.getQuizId()))
                .build();
    }


}
