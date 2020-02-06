package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.domain.Quiz;
import com.kopivad.testingsystem.repository.jooq.QuizRepositoryJooqImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.util.List;

import static java.lang.System.currentTimeMillis;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class QuizServiceImplTest {
    @InjectMocks
    private QuizServiceImpl quizService;
    @Mock
    private QuizRepositoryJooqImpl quizRepository;
    @Mock
    private MailServiceImpl mailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveQuiz() {
        Quiz quizForSave = Quiz.builder().id(1l).description("Some desc").title("Some title").build();
        Quiz quizFromDB = Quiz.builder().id(1l).description("Some desc").created(new Timestamp(currentTimeMillis())).title("Some title").active(true).build();
        when(quizRepository.saveQuiz(any(Quiz.class))).thenReturn(quizFromDB);
        Quiz quiz = quizService.saveQuiz(quizForSave);
        assertEquals(quizFromDB, quiz);
        verify(quizRepository, times(1)).saveQuiz(any(Quiz.class));
    }

    @Test
    void getAllQuizzes() {
        List<Quiz> quizzesFromDB = List.of(
                Quiz.builder().id(1l).description("Some desc").created(new Timestamp(currentTimeMillis())).title("Some title").active(true).build(),
                Quiz.builder().id(1l).description("Some desc").created(new Timestamp(currentTimeMillis())).title("Some title").active(true).build(),
                Quiz.builder().id(1l).description("Some desc").created(new Timestamp(currentTimeMillis())).title("Some title").active(true).build(),
                Quiz.builder().id(1l).description("Some desc").created(new Timestamp(currentTimeMillis())).title("Some title").active(true).build()
        );
        when(quizRepository.findAll()).thenReturn(quizzesFromDB);
        List<Quiz> quizzes = quizService.getAllQuizzes();
        assertEquals(quizzesFromDB, quizzes);
        verify(quizRepository, times(1)).findAll();
    }

    @Test
    void getQuizById() {
        Quiz quizFromDB = Quiz.builder().id(1l).description("Some desc").created(new Timestamp(currentTimeMillis())).title("Some title").active(true).build();
        when((quizRepository.findQuizById(anyLong()))).thenReturn(quizFromDB);
        Quiz quiz = quizService.getQuizById(1l);
        assertEquals(quizFromDB, quiz);
        verify(quizRepository, times(1)).findQuizById(anyLong());
    }

    @Test
    void updateQuiz() {
        Quiz quizForUpdate = Quiz.builder().id(1l).description("Some desc").title("Some title").build();
        Quiz quizFromDB = Quiz.builder().id(1l).description("Some desc").created(new Timestamp(currentTimeMillis())).title("Some title").active(true).build();
        when(quizRepository.saveQuiz(any(Quiz.class))).thenReturn(quizFromDB);
        Quiz quiz = quizService.saveQuiz(quizForUpdate);
        assertEquals(quizFromDB, quiz);
        verify(quizRepository, times(1)).saveQuiz(any(Quiz.class));
    }

    @Test
    void getAllQuizzesByUserId() {
        List<Quiz> quizzesFromDB = List.of(
                Quiz.builder().id(1l).description("Some desc").created(new Timestamp(currentTimeMillis())).title("Some title").active(true).build(),
                Quiz.builder().id(1l).description("Some desc").created(new Timestamp(currentTimeMillis())).title("Some title").active(true).build(),
                Quiz.builder().id(1l).description("Some desc").created(new Timestamp(currentTimeMillis())).title("Some title").active(true).build(),
                Quiz.builder().id(1l).description("Some desc").created(new Timestamp(currentTimeMillis())).title("Some title").active(true).build()
        );
        when(quizRepository.findAllByAuthorId(anyLong())).thenReturn(quizzesFromDB);
        List<Quiz> allQuizzesByUserId = quizService.getAllQuizzesByUserId(1l);
        assertEquals(quizzesFromDB, allQuizzesByUserId);
        verify(quizRepository, times(1)).findAllByAuthorId(anyLong());
    }

    @Test
    void shareQuiz() {
        String email = "some@email.com";
        quizService.shareQuiz(1l, email);
        verify(mailService, times(1)).sendMassage(anyString(), anyString(), anyString());
    }

    @Test
    void deleteQuiz() {
        quizService.deleteQuiz(1l);
        verify(quizRepository, times(1)).deleteQuizById(anyLong());
    }
}