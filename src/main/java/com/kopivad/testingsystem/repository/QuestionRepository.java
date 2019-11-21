package com.kopivad.testingsystem.repository;

import com.kopivad.testingsystem.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends PagingAndSortingRepository<Question, Long> {
    Page<Question> findAllByQuizId(Long quizId, Pageable pageable);
    List<Question> findAll();
}
