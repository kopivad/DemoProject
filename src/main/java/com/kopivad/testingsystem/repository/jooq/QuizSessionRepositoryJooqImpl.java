package com.kopivad.testingsystem.repository.jooq;

import com.kopivad.testingsystem.domain.Quiz;
import com.kopivad.testingsystem.domain.QuizSession;
import com.kopivad.testingsystem.domain.User;
import com.kopivad.testingsystem.repository.QuizSessionRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kopivad.testingsystem.domain.db.Sequences.QUIZ_SESSIONS_ID_SEQ;
import static com.kopivad.testingsystem.domain.db.tables.QuizSessions.QUIZ_SESSIONS;
import static org.jooq.impl.DSL.val;

@Repository
@RequiredArgsConstructor
public class QuizSessionRepositoryJooqImpl implements QuizSessionRepository {
    private final DSLContext dslContext;

    @Override
    public QuizSession saveQuizSession(QuizSession quizSession) {
        return dslContext
                .insertInto(QUIZ_SESSIONS, QUIZ_SESSIONS.ID, QUIZ_SESSIONS.USER_ID, QUIZ_SESSIONS.CREATED, QUIZ_SESSIONS.QUIZ_ID)
                .values(QUIZ_SESSIONS_ID_SEQ.nextval(), val(quizSession.getUser().getId()), val(quizSession.getCreated()), val(quizSession.getQuiz().getId()))
                .returning(QUIZ_SESSIONS.ID, QUIZ_SESSIONS.USER_ID, QUIZ_SESSIONS.CREATED, QUIZ_SESSIONS.QUIZ_ID)
                .fetchOne()
                .map(this::getQuizSessionFromRecord);
    }

    @Override
    public QuizSession findQuizSessionById(Long sessionId) {
        return dslContext
                .selectFrom(QUIZ_SESSIONS)
                .where(QUIZ_SESSIONS.ID.eq(sessionId))
                .fetchOne()
                .map(this::getQuizSessionFromRecord);
    }

    @Override
    public List<QuizSession> findQuizSessionByUserId(Long id) {
        return dslContext
                .selectFrom(QUIZ_SESSIONS)
                .where(QUIZ_SESSIONS.USER_ID.eq(id))
                .fetch()
                .map(this::getQuizSessionFromRecord);
    }

    @Override
    public List<QuizSession> findAllByQuizId(Long id) {
        return dslContext
                .selectFrom(QUIZ_SESSIONS)
                .where(QUIZ_SESSIONS.QUIZ_ID.eq(id))
                .fetch()
                .map(this::getQuizSessionFromRecord);
    }

    private QuizSession getQuizSessionFromRecord(Record record) {
        return QuizSession
                .builder()
                .id(record.getValue(QUIZ_SESSIONS.ID))
                .user(User.builder().id(record.getValue(QUIZ_SESSIONS.USER_ID)).build())
                .quiz(Quiz.builder().id(record.getValue(QUIZ_SESSIONS.QUIZ_ID)).build())
                .created(record.getValue(QUIZ_SESSIONS.CREATED))
                .build();
    }
}
