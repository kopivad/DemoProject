package com.kopivad.testingsystem.repository.jooq;

import com.kopivad.testingsystem.domain.Answer;
import com.kopivad.testingsystem.domain.Question;
import com.kopivad.testingsystem.repository.AnswerRepository;
import com.kopivad.testingsystem.repository.QuestionRepository;
import com.kopivad.testingsystem.service.impl.AnswerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

class AnswerRepositoryJooqImplTest {
    @InjectMocks
    AnswerServiceImpl answerService;
    @Mock
    AnswerRepository answerRepository;
    @Mock
    QuestionRepository questionRepository;


    @Test
    void findAllByQuestionIdTest() {
    }

    @Test
    void findAllTest() {
        List<Answer> answers = List.of(
                Answer.builder().id(1l).text("some answer1").isRight(false).question(Question.builder().id(1l).build()).build(),
                Answer.builder().id(2l).text("some answer2").isRight(false).question(Question.builder().id(1l).build()).build(),
                Answer.builder().id(3l).text("some answer3").isRight(false).question(Question.builder().id(1l).build()).build(),
                Answer.builder().id(4l).text("some answer4").isRight(true).question(Question.builder().id(1l).build()).build()
        );

        when(answerRepository.findAll()).thenReturn(answers);
        List<Answer> allAnswers = answerService.getAllAnswers();
        assertEquals(4, allAnswers.size());
        verify(answerRepository, times(1)).findAll();
    }

    @Test
    void saveAnswerTest() {
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findAnswerByIdTest() {
    }

    @Test
    void updateAnswerTest() {
    }

    @Test
    void deleteAnswerByIdTest() {
    }
}