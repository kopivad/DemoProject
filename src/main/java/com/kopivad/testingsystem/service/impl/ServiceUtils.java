package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.domain.*;
import com.kopivad.testingsystem.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ServiceUtils {
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final QuizResultRepository resultRepository;
    private final QuizSessionRepository sessionRepository;
    private final UserResponseRepository responseRepository;


    public Question getFullQuestion(Question question) {
        return Question
                .builder()
                .id(question.getId())
                .title(question.getTitle())
                .quiz(quizRepository.findQuizById(question.getQuiz().getId()))
                .answers(answerRepository.findAllByQuestionId(question.getId()))
                .userQuestionResponse(responseRepository.findAllByQuestionId(question.getId()))
                .build();
    }

    @SneakyThrows
    public Answer getFullAnswer(Answer answer) {
        return Answer
                .builder()
                .id(answer.getId())
                .isRight(answer.isRight())
                .text(answer.getText())
                .question(questionRepository.findQuestionById(answer.getQuestion().getId()))
                .userResponses(responseRepository.findAllByAnswerId(answer.getId()))
                .build();
    }

    public QuizResult getFullQuizResult(QuizResult quizResult) {
        return QuizResult
                .builder()
                .id(quizResult.getId())
                .countOfCorrect(quizResult.getCountOfCorrect())
                .totalAnswers(quizResult.getTotalAnswers())
                .rating(quizResult.getRating())
                .user(userRepository.findUserById(quizResult.getUser().getId()))
                .session(sessionRepository.findQuizSessionById(quizResult.getSession().getId()))
                .build();
    }

    public QuizSession getFullQuizSession(QuizSession session) {
        return QuizSession
                .builder()
                .id(session.getId())
                .created(session.getCreated())
                .quiz(quizRepository.findQuizById(session.getQuiz().getId()))
                .user(userRepository.findUserById(session.getUser().getId()))
                .userResponses(responseRepository.findAllByQuizSessionId(session.getId()))
                .results(resultRepository.findAllBySessionId(session.getId()))
                .build();
    }

    public Quiz getFullQuiz(Quiz quiz) {
        return Quiz
                .builder()
                .id(quiz.getId())
                .active(quiz.isActive())
                .created(quiz.getCreated())
                .title(quiz.getTitle())
                .description(quiz.getDescription())
                .author(userRepository.findUserById(quiz.getAuthor().getId()))
                .questions(questionRepository.findAllByQuizId(quiz.getId()))
                .quizSessions(sessionRepository.findAllByQuizId(quiz.getId()))
                .build();
    }

    @SneakyThrows
    public UserResponse getFullUserResponse(UserResponse userResponse) {
        return UserResponse
                .builder()
                .id(userResponse.getId())
                .answer(answerRepository.findAnswerById(userResponse.getAnswer().getId()))
                .question(questionRepository.findQuestionById(userResponse.getQuestion().getId()))
                .quizSession(sessionRepository.findQuizSessionById(userResponse.getQuizSession().getId()))
                .build();
    }

    public User getFullUser(User user) {
        return User
                .builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .password(user.getPassword())
                .quizzes(quizRepository.findAllByAuthorId(user.getId()))
                .quizSessions(sessionRepository.findQuizSessionByUserId(user.getId()))
                .results(resultRepository.findAllByUserId(user.getId()))
                .roles(user.getRoles())
                .build();
    }
}
