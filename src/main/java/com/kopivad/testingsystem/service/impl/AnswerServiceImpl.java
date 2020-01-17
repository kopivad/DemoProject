package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.form.AnswerForm;
import com.kopivad.testingsystem.domain.Answer;
import com.kopivad.testingsystem.domain.Question;
import com.kopivad.testingsystem.repository.AnswerRepository;
import com.kopivad.testingsystem.repository.QuestionRepository;
import com.kopivad.testingsystem.repository.jooq.RepositoryUtils;
import com.kopivad.testingsystem.service.AnswerService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
//(onConstructor = @__({@Lazy}))
@Service
@AllArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final RepositoryUtils repositoryUtils;


    @Override
    public Answer saveAnswer(Answer answer) {
        Answer answerFromDB = answerRepository.saveAnswer(answer);
        return repositoryUtils.getFullAnswer(answerFromDB);
    }

    public List<Answer> getAnswersByQuestionId(Long id) {
        List<Answer> answersFromDB = answerRepository.findAllByQuestionId(id);
        return answersFromDB
                .stream()
                .map(repositoryUtils::getFullAnswer)
                .collect(Collectors.toList());
    }

    public Answer getAnswerById(Long id) {
        Answer answerFromDB = answerRepository.findAnswerById(id);
        return repositoryUtils.getFullAnswer(answerFromDB);
    }

    @Override
    public List<Answer> getAllAnswers() {
        List<Answer> answersFromDB = answerRepository.findAll();
        return answersFromDB
                .stream()
                .map(repositoryUtils::getFullAnswer)
                .collect(Collectors.toList());
    }

    @Override
    public Answer updateAnswer(Answer answer) {
        Answer answerFromDB = answerRepository.updateAnswer(answer);
        return repositoryUtils.getFullAnswer(answerFromDB);
    }

    @Override
    public void deleteAnswerById(Long id) {
        answerRepository.deleteAnswerById(id);
    }

    @Override
    public Answer saveAnswer(AnswerForm answerForm) {
        return this.saveAnswer(getAnswerFromAnswerForm(answerForm));
    }

    @Override
    public Answer updateAnswer(AnswerForm answerForm) {
        Answer answerForUpdate = answerRepository.findAnswerById(answerForm.getAnswerId());
        Question currentQuestion = questionRepository.findQuestionById(answerForm.getQuestionId());
        answerForUpdate.setRight(answerForm.getIsRight() != null);
        answerForUpdate.setText(answerForm.getText());
        answerForUpdate.setQuestion(currentQuestion);
        return this.updateAnswer(answerForUpdate);
    }

    private Answer getAnswerFromAnswerForm(AnswerForm answerForm) {
        return Answer
                .builder()
                .text(answerForm.getText())
                .question(questionRepository.findQuestionById(answerForm.getQuestionId()))
                .isRight(answerForm.getIsRight() != null)
                .id(answerForm.getAnswerId())
                .build();
    }
}
