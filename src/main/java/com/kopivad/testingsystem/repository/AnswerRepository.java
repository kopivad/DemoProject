package com.kopivad.testingsystem.repository;

import com.kopivad.testingsystem.model.Answer;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface AnswerRepository {
    List<Answer> findAllByQuestionId(Long id);
    List<Answer> findAll();
    Answer save(Answer answer);
    Answer findAnswerById(Long id);
}
