package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.domain.Answer;
import com.kopivad.testingsystem.domain.Question;
import com.kopivad.testingsystem.repository.AnswerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AnswerServiceImplTest {
    @InjectMocks
    private AnswerServiceImpl answerService;
    @Mock
    private AnswerRepository answerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveAnswer() {
        Answer answerForSave = Answer.builder().isRight(false).text("some text 1").question(Question.builder().id(1l).build()).build();
        Answer savedAnswer = Answer.builder().id(1l).isRight(false).text("some text 1").question(Question.builder().id(1l).build()).build();
        when(answerRepository.saveAnswer(answerForSave)).thenReturn(savedAnswer);
        Answer answer = answerService.saveAnswer(answerForSave);
        assertEquals(savedAnswer, answer);
        verify(answerRepository, times(1)).saveAnswer(answerForSave);
    }

    @Test
    void getAnswersByQuestionId() {
        List<Answer> answers = List.of(
                Answer.builder().id(1l).isRight(false).text("some text 1").question(Question.builder().id(1l).build()).build(),
                Answer.builder().id(2l).isRight(false).text("some text 2").question(Question.builder().id(1l).build()).build(),
                Answer.builder().id(3l).isRight(false).text("some text 3").question(Question.builder().id(1l).build()).build(),
                Answer.builder().id(4l).isRight(false).text("some text 4").question(Question.builder().id(1l).build()).build()
        );
        when(answerRepository.findAllByQuestionId(1l)).thenReturn(answers);
        List<Answer> answersByQuestionId = answerService.getAnswersByQuestionId(1l);
        assertEquals(answers, answersByQuestionId);
        verify(answerRepository, times(1)).findAllByQuestionId(1l);
    }

    @Test
    void getAnswerById() {
        Answer answer = Answer.builder().id(1l).isRight(false).text("some text 1").question(Question.builder().id(1l).build()).build();
        when(answerRepository.findAnswerById(1l)).thenReturn(answer);
        Answer answerById = answerService.getAnswerById(1l);
        assertEquals(answer.getId(), answerById.getId());
        verify(answerRepository, times(1)).findAnswerById(1l);
    }

    @Test
    void getAllAnswers() {
        List<Answer> answersFromDB = List.of(
                Answer.builder().id(1l).isRight(false).text("some text 1").question(Question.builder().id(1l).build()).build(),
                Answer.builder().id(2l).isRight(false).text("some text 2").question(Question.builder().id(1l).build()).build(),
                Answer.builder().id(3l).isRight(false).text("some text 3").question(Question.builder().id(1l).build()).build(),
                Answer.builder().id(4l).isRight(false).text("some text 4").question(Question.builder().id(1l).build()).build()
        );
        when(answerRepository.findAll()).thenReturn(answersFromDB);
        List<Answer> answers = answerService.getAllAnswers();
        assertEquals(answersFromDB, answers);
        verify(answerRepository, times(1)).findAll();
    }

    @Test
    void updateAnswer() {
        Answer answerForUpdate = Answer.builder().isRight(false).text("some text 1").question(Question.builder().id(1l).build()).build();
        Answer updatedAnswer = Answer.builder().id(1l).isRight(false).text("some text 1").question(Question.builder().id(1l).build()).build();;
        updatedAnswer.setRight(true);
        when(answerRepository.saveAnswer(answerForUpdate)).thenReturn(updatedAnswer);
        Answer answer = answerService.saveAnswer(answerForUpdate);
        assertEquals(updatedAnswer, answer);
        verify(answerRepository, times(1)).saveAnswer(answerForUpdate);
    }

    @Test
    void deleteAnswerById() {
        answerService.deleteAnswerById(1l);
        verify(answerRepository, times(1)).deleteAnswerById(1l);
    }
}