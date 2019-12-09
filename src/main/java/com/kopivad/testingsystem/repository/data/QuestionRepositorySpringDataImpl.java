package com.kopivad.testingsystem.repository.data;

import com.kopivad.testingsystem.model.Question;
import com.kopivad.testingsystem.repository.QuestionRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public interface QuestionRepositorySpringDataImpl extends QuestionRepository, PagingAndSortingRepository<Question, Long> {
    Page<Question> findAllByQuizId(Long quizId, Pageable pageable);

    List<Question> findAll();

    List<Question> findAllByQuizId(Long id);

    @Override
    default Question saveQuestion(Question question) {
        return save(question);
    }

    @Override
    Question findQuestionById(Long questionId);

    @Override
    default void updateQuestion(Question question) {
        save(question);
    }
}
