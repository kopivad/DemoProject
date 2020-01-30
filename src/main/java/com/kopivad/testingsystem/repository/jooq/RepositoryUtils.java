package com.kopivad.testingsystem.repository.jooq;

import com.kopivad.testingsystem.domain.*;
import com.kopivad.testingsystem.domain.db.Tables;
import com.kopivad.testingsystem.domain.db.tables.QuizSessions;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

import static com.kopivad.testingsystem.domain.db.Tables.QUIZZES;
import static com.kopivad.testingsystem.domain.db.Tables.QUIZ_SESSIONS;
import static com.kopivad.testingsystem.domain.db.tables.Answers.ANSWERS;
import static com.kopivad.testingsystem.domain.db.tables.Questions.QUESTIONS;
import static com.kopivad.testingsystem.domain.db.tables.QuizResults.QUIZ_RESULTS;
import static com.kopivad.testingsystem.domain.db.tables.UserResponces.USER_RESPONCES;
import static com.kopivad.testingsystem.domain.db.tables.UserRoles.USER_ROLES;
import static com.kopivad.testingsystem.domain.db.tables.Users.USERS;

@RequiredArgsConstructor
@Component
public class RepositoryUtils {
    private final DSLContext dslContext;

    public List<QuizResult> getQuizResultsFromRecord(Record r) {
        return dslContext
                .selectFrom(Tables.QUIZ_RESULTS)
                .where(Tables.QUIZ_RESULTS.SESSION_ID.eq(r.getValue(QuizSessions.QUIZ_SESSIONS.ID)))
                .fetch(r1 -> QuizResult
                        .builder()
                        .id(r1.getId())
                        .countOfCorrect(r1.getCountOfCorrect())
                        .rating(r1.getRating())
                        .totalAnswers(r1.getTotalAnswers())
                        .session(QuizSession.builder().id(r1.getSessionId()).build())
                        .user(User.builder().id(r1.getUserId()).build())
                        .build()
                );
    }

    public QuizSession getSessionFromRecord(Record r) {
        return dslContext
                .selectFrom(QUIZ_SESSIONS)
                .where(QUIZ_SESSIONS.ID.eq(r.getValue(QUIZ_RESULTS.SESSION_ID)))
                .fetchOne(r1 -> QuizSession
                        .builder()
                        .id(r1.getId())
                        .created(r1.getCreated())
                        .user(User.builder().id(r1.getUserId()).build())
                        .quiz(Quiz.builder().id(r1.getUserId()).build())
                        .build()
                );
    }

    public List<QuizSession> getQuizSessionsFromRecord(Record r) {
        return dslContext
                .selectFrom(QUIZ_SESSIONS)
                .where(QUIZ_SESSIONS.ID.eq(r.getValue(QUIZZES.ID)))
                .fetch(r1 -> QuizSession
                        .builder()
                        .id(r1.getId())
                        .created(r1.getCreated())
                        .quiz(Quiz.builder().id(r1.getQuizId()).build())
                        .user(User.builder().id(r1.getUserId()).build())
                        .build());
    }

    public User getUserFromRecord(Record r) {
        return dslContext
                .selectFrom(USERS)
                .where(USERS.ID.eq(r.getValue(USERS.ID)))
                .fetchOne(r1 -> User
                        .builder()
                        .id(r1.getId())
                        .nickname(r1.getNickname())
                        .email(r1.getEmail())
                        .password(r1.getPassword())
                        .roles(new HashSet<>(getRolesFromRecord(r1)))
                        .build());
    }

    private List<Role> getRolesFromRecord(Record r) {
        return dslContext
                .selectDistinct(USER_ROLES.ROLES)
                .from(USER_ROLES)
                .where(USER_ROLES.USER_ID.eq(r.getValue(USERS.ID)))
                .fetch(r1 -> Role.valueOf(r1.getValue(USER_ROLES.ROLES)));
    }

    public Quiz getQuizFromRecord(Record r) {
        return dslContext.selectFrom(QUIZZES)
                .where(QUIZZES.ID.eq(r.getValue(Tables.QUESTIONS.QUIZ_ID)))
                .fetchOne(r1 -> Quiz.builder()
                        .id(r1.getId())
                        .description(r1.getDescription())
                        .title(r1.getTitle())
                        .author(User.builder().id(r1.getUserId()).build())
                        .active(r1.getActive())
                        .created(r1.getCreated())
                        .build());
    }

    public List<Answer> getAnswersFromRecord(Record r) {
        return dslContext.selectFrom(ANSWERS)
                .where(ANSWERS.QUESTION_ID.eq(r.getValue(Tables.QUESTIONS.QUIZ_ID)))
                .fetch(r2 -> Answer.builder()
                        .id(r2.getId())
                        .question(Question.builder().id(r2.getQuestionId()).build())
                        .isRight(r2.getIsRight())
                        .build());
    }

    public Question getQuestionFromRecord(Record r) {
        return dslContext
                .selectFrom(QUESTIONS)
                .where(QUESTIONS.ID.eq(r.getValue(ANSWERS.QUESTION_ID)))
                .fetchOne(r2 -> Question
                        .builder()
                        .id(r2.getId())
                        .title(r2.getTitle())
                        .quiz(Quiz.builder().id(r2.getQuizId()).build())
                        .build()
                );
    }

    public Answer getAnswerFromRecord(Record r) {
        return dslContext
                .selectFrom(ANSWERS)
                .where(ANSWERS.ID.eq(r.getValue(ANSWERS.QUESTION_ID)))
                .fetchOne(r2 -> Answer.builder()
                        .id(r2.getId())
                        .question(Question.builder().id(r2.getQuestionId()).build())
                        .isRight(r2.getIsRight())
                        .build());
    }

    public List<UserResponse> getUserResponsesFromRecord(Record r) {
        return dslContext
                .selectFrom(USER_RESPONCES)
                .where(USER_RESPONCES.ANSWER_ID.eq(r.getValue(ANSWERS.ID)))
                .fetch(r3 -> UserResponse
                        .builder()
                        .id(r3.getId())
                        .answer(Answer.builder().id(r3.getAnswerId()).build())
                        .question(Question.builder().id(r3.getQuestionId()).build())
                        .quizSession(QuizSession.builder().id(r3.getSessionId()).build())
                        .build());
    }
}
