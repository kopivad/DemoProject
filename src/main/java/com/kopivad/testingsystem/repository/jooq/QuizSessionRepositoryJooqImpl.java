package com.kopivad.testingsystem.repository.jooq;

import com.kopivad.testingsystem.domain.QuizSession;
import com.kopivad.testingsystem.repository.QuizSessionRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kopivad.testingsystem.domain.db.Sequences.QUIZ_SESSIONS_ID_SEQ;
import static com.kopivad.testingsystem.domain.db.tables.QuizSessions.QUIZ_SESSIONS;
import static org.jooq.impl.DSL.val;

@Repository
@RequiredArgsConstructor
public class QuizSessionRepositoryJooqImpl implements QuizSessionRepository {
    private final DSLContext dslContext;
    private final RepositoryUtils repositoryUtils;

    @Override
    public QuizSession saveQuizSession(QuizSession quizSession) {
        return dslContext
                .insertInto(QUIZ_SESSIONS, QUIZ_SESSIONS.ID, QUIZ_SESSIONS.USER_ID, QUIZ_SESSIONS.CREATED, QUIZ_SESSIONS.QUIZ_ID)
                .values(QUIZ_SESSIONS_ID_SEQ.nextval(), val(quizSession.getUser().getId()), val(quizSession.getCreated()), val(quizSession.getQuiz().getId()))
                .returning(QUIZ_SESSIONS.ID, QUIZ_SESSIONS.USER_ID, QUIZ_SESSIONS.CREATED, QUIZ_SESSIONS.QUIZ_ID)
                .fetchOne()
                .map(getRecordQuizSessionRecordMapper());
    }

    @Override
    public QuizSession findQuizSessionById(Long sessionId) {
        return dslContext
                .selectFrom(QUIZ_SESSIONS)
                .where(QUIZ_SESSIONS.ID.eq(sessionId))
                .fetchOne()
                .map(getRecordQuizSessionRecordMapper());
    }

    @Override
    public List<QuizSession> findQuizSessionByUserId(Long id) {
        return dslContext
                .selectFrom(QUIZ_SESSIONS)
                .where(QUIZ_SESSIONS.USER_ID.eq(id))
                .fetch()
                .map(getRecordQuizSessionRecordMapper());
    }

    @Override
    public List<QuizSession> findAllByQuizId(Long id) {
        return dslContext
                .selectFrom(QUIZ_SESSIONS)
                .where(QUIZ_SESSIONS.QUIZ_ID.eq(id))
                .fetch()
                .map(getRecordQuizSessionRecordMapper());
    }

    private RecordMapper<Record, QuizSession> getRecordQuizSessionRecordMapper() {
        return r -> QuizSession
                .builder()
                .id(r.getValue(QUIZ_SESSIONS.ID))
                .user(repositoryUtils.getUserFromRecord(r))
                .quiz(repositoryUtils.getQuizFromRecord(r))
                .results(repositoryUtils.getQuizResultsFromRecord(r))
                .created(r.getValue(QUIZ_SESSIONS.CREATED))
                .build();
    }
}
