package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.domain.Question;
import com.kopivad.testingsystem.domain.Quiz;
import com.kopivad.testingsystem.repository.QuestionRepository;
import com.kopivad.testingsystem.repository.jooq.QuestionRepositoryJooqImpl;
import com.kopivad.testingsystem.repository.jooq.QuizRepositoryJooqImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class QuestionServiceImplTest {
    @InjectMocks
    private QuestionServiceImpl questionService;
    @Mock
    private QuestionRepositoryJooqImpl questionRepositoryJooq;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveQuestion() {
        Question questionForSave = Question.builder().title("TItle").build();
        Question saved = Question.builder().id(1l).title("TItle").build();
        when(questionRepositoryJooq.saveQuestion(any(Question.class))).thenReturn(saved);
        Question question = questionService.saveQuestion(questionForSave);
        assertEquals(saved, question);
        verify(questionRepositoryJooq, times(1)).saveQuestion(any(Question.class));
    }

    @Test
    void getQuestionByQuizId() {
        List<Question> questionList = List.of(
                Question.builder().id(1l).quiz(Quiz.builder().id(1l).build()).title("title").build(),
                Question.builder().id(1l).quiz(Quiz.builder().id(1l).build()).title("title").build(),
                Question.builder().id(1l).quiz(Quiz.builder().id(1l).build()).title("title").build(),
                Question.builder().id(1l).quiz(Quiz.builder().id(1l).build()).title("title").build()
        );
        when(questionRepositoryJooq.findAllByQuizId(anyLong())).thenReturn(questionList);
        List<Question> questionsByQuizId = questionService.getQuestionByQuizId(anyLong());
        assertEquals(questionList, questionsByQuizId);
        verify(questionRepositoryJooq, times(1)).findAllByQuizId(anyLong());
    }

    @Test
    void getQuestionById() {
        Question question = Question.builder().id(1l).quiz(Quiz.builder().id(1l).build()).title("title").build();
        when(questionRepositoryJooq.findQuestionById(anyLong())).thenReturn(question);
        Question questionById = questionService.getQuestionById(anyLong());
        assertEquals(question, questionById);
        verify(questionRepositoryJooq, times(1)).findQuestionById(anyLong());
    }

    @Test
    void getAllQuestions() {
        List<Question> questionList = List.of(
                Question.builder().id(1l).quiz(Quiz.builder().id(1l).build()).title("title").build(),
                Question.builder().id(1l).quiz(Quiz.builder().id(1l).build()).title("title").build(),
                Question.builder().id(1l).quiz(Quiz.builder().id(1l).build()).title("title").build(),
                Question.builder().id(1l).quiz(Quiz.builder().id(1l).build()).title("title").build()
        );
        when(questionRepositoryJooq.findAll()).thenReturn(questionList);
        List<Question> questionsByQuizId = questionService.getAllQuestions();
        assertEquals(questionList, questionsByQuizId);
        verify(questionRepositoryJooq, times(1)).findAll();
    }

    @Test
    void updateQuestion() {
        Question questionForUpdate = Question.builder().title("Title").build();
        Question updated = Question.builder().id(1l).title("Title").build();
        when(questionRepositoryJooq.saveQuestion(any(Question.class))).thenReturn(updated);
        Question question = questionService.saveQuestion(questionForUpdate);
        assertEquals(updated, question);
        verify(questionRepositoryJooq, times(1)).saveQuestion(any(Question.class));
    }

    @Test
    void countByQuizId() {
        questionService.countByQuizId(anyLong());
        verify(questionRepositoryJooq, times(1)).countByQuizId(anyLong());
    }

    @Test
    void deleteQuestion() {
        questionService.deleteQuestion(anyLong());
        verify(questionRepositoryJooq, times(1)).deleteQuestion(anyLong());
    }
}