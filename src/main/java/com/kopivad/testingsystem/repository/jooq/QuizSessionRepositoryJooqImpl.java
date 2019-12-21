package com.kopivad.testingsystem.repository.jooq;

import com.kopivad.testingsystem.model.Quiz;
import com.kopivad.testingsystem.model.QuizSession;
import com.kopivad.testingsystem.model.User;
import com.kopivad.testingsystem.model.UserResponce;
import com.kopivad.testingsystem.repository.QuizSessionRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static com.kopivad.testingsystem.model.db.tables.QuizSessions.QUIZ_SESSIONS;
import static com.kopivad.testingsystem.model.db.tables.Quizzes.*;
import static com.kopivad.testingsystem.model.db.tables.UserResponces.USER_RESPONCES;
import static com.kopivad.testingsystem.model.db.tables.Users.USERS;

@Repository
@Primary
@RequiredArgsConstructor
public class QuizSessionRepositoryJooqImpl implements QuizSessionRepository {
    private final DSLContext dslContext;

    @Override
    public QuizSession saveQuizSession(QuizSession quizSession) {
        QuizSession session = dslContext
                .insertInto(QUIZ_SESSIONS, QUIZ_SESSIONS.ID, QUIZ_SESSIONS.USER_ID, QUIZ_SESSIONS.QUIZ_ID)
                .values(0L, quizSession.getUser().getId(), quizSession.getQuiz().getId())
                .returning(QUIZ_SESSIONS.ID, QUIZ_SESSIONS.USER_ID, QUIZ_SESSIONS.QUIZ_ID)
                .fetchOne()
                .map(this::getQuizSessionFromRecord);

        System.out.println(session.getId());
        return session;
    }

    @Override
    public QuizSession findQuizSessionById(Long sessionId) {
        return dslContext
                .selectFrom(QUIZ_SESSIONS)
                .where(QUIZ_SESSIONS.ID.eq(sessionId))
                .fetchOne()
                .map(this::getQuizSessionFromRecord);
    }

    private QuizSession getQuizSessionFromRecord(Record record) {
        User user = dslContext
                .selectFrom(USERS)
                .where(USERS.ID.eq(record.getValue(QUIZ_SESSIONS.USER_ID)))
                .fetchOne()
                .map(r -> User
                        .builder()
                        .id(r.getValue(USERS.ID))
                        .password(r.getValue(USERS.PASSWORD))
                        .email(r.getValue(USERS.EMAIL))
                        .nickname(r.getValue(USERS.NICKNAME))
                        .build()
                );

        Quiz quiz = dslContext
                .selectFrom(QUIZZES)
                .where(QUIZZES.ID.eq(record.getValue(QUIZ_SESSIONS.QUIZ_ID)))
                .fetchOne()
                .map(r -> Quiz
                        .builder()
                        .id(r.getValue(QUIZZES.ID))
                        .title(r.getValue(QUIZZES.TITLE))
                        .description(r.getValue(QUIZZES.DESCRIPTION))
                        .build()
                );


        List<UserResponce> userResponces = dslContext
                .selectFrom(USER_RESPONCES)
                .where(USER_RESPONCES.SESSION_ID.eq(record.getValue(QUIZ_SESSIONS.ID)))
                .fetch()
                .map(r -> UserResponce
                        .builder()
                        .id(r.getValue(USER_RESPONCES.ID))
                        .build()
                );

        return QuizSession
                .builder()
                .id(record.getValue(QUIZ_SESSIONS.ID))
                .user(user)
                .quiz(quiz)
                .userResponces(userResponces)
                .build();
    }
}
