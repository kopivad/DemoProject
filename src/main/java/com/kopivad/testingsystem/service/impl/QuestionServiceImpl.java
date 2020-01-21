package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.form.QuestionForm;
import com.kopivad.testingsystem.domain.Question;
import com.kopivad.testingsystem.domain.Quiz;
import com.kopivad.testingsystem.repository.QuestionRepository;
import com.kopivad.testingsystem.repository.QuizRepository;
import com.kopivad.testingsystem.repository.jooq.RepositoryUtils;
import com.kopivad.testingsystem.service.QuestionService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;
    private final RepositoryUtils repositoryUtils;

    @Override
    public Question saveQuestion(Question question) {
        Question questionFromDB = questionRepository.saveQuestion(question);
        return repositoryUtils.getFullQuestion(questionFromDB);
    }

    @Override
    public Page<Question> getQuestionByQuizId(Long id, Pageable pageable) {
//        Question currentQuestionFromDB = questionPageFromDB.getContent().get(0);
//        questionPageFromDB.getContent().set(0, serviceUtils.getFullQuestion(currentQuestionFromDB));
        return questionRepository.findAllByQuizId(id, pageable);
    }

    @SneakyThrows
    @Override
    public Question getQuestionById(Long questionId) {
        Question questionFromDB = questionRepository.findQuestionById(questionId);
        return repositoryUtils.getFullQuestion(questionFromDB);
    }

    @Override
    public List<Question> getAllQuestions() {
        List<Question> questionsFromDB = questionRepository.findAll();
        return questionsFromDB
                .stream()
                .map(repositoryUtils::getFullQuestion)
                .collect(Collectors.toList());
    }

    @Override
    public List<Question> getQuestionByQuizId(Long id) {
        List<Question> questionsFromDB = questionRepository.findAllByQuizId(id);
        return questionsFromDB
                .stream()
                .map(repositoryUtils::getFullQuestion)
                .collect(Collectors.toList());
    }

    @Override
    public Question updateQuestion(Question question) {
        Question questionFromDB = questionRepository.updateQuestion(question);
        return repositoryUtils.getFullQuestion(questionFromDB);
    }

    @Override
    public long countByQuizId(Long quizId) {
        return questionRepository.countByQuizId(quizId);
    }

    @Override
    public Question saveQuestion(QuestionForm form) {
        return this.saveQuestion(getQuestionFromForm(form));
    }

    @SneakyThrows
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
