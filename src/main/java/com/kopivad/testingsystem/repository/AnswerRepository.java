package com.kopivad.testingsystem.repository;

import com.kopivad.testingsystem.domain.Answer;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface AnswerRepository {
    List<Answer> findAllByQuestionId(Long id);
    List<Answer> findAll();
    Answer saveAnswer(Answer answer);
    Answer findAnswerById(Long id);
    Answer updateAnswer(Answer answer);
    void deleteAnswerById(Long id);
}
