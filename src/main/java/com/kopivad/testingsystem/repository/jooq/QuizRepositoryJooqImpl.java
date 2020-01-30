package com.kopivad.testingsystem.repository.jooq;


import com.kopivad.testingsystem.domain.Quiz;
import com.kopivad.testingsystem.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.kopivad.testingsystem.domain.db.Sequences.QUIZZES_ID_SEQ;
import static com.kopivad.testingsystem.domain.db.Tables.QUIZZES;
import static org.jooq.impl.DSL.val;

@Repository
@RequiredArgsConstructor
public class QuizRepositoryJooqImpl implements QuizRepository {
    private final DSLContext dslContext;
    private final RepositoryUtils repositoryUtils;

    @Override
    public List<Quiz> findAll() {
        return dslContext
                .selectFrom(QUIZZES)
                .fetch()
                .map(getQuizzesRecordQuizRecordMapper());
    }

    @Override
    @Transactional
    public Quiz saveQuiz(Quiz quiz) {
        return dslContext
                .insertInto(QUIZZES, QUIZZES.ID, QUIZZES.TITLE, QUIZZES.DESCRIPTION, QUIZZES.USER_ID, QUIZZES.CREATED, QUIZZES.ACTIVE)
                .values(QUIZZES_ID_SEQ.nextval(), val(quiz.getTitle()), val(quiz.getDescription()), val(quiz.getAuthor().getId()), val(quiz.getCreated()), val(quiz.isActive()))
                .returning(QUIZZES.ID, QUIZZES.TITLE, QUIZZES.DESCRIPTION, QUIZZES.USER_ID, QUIZZES.CREATED, QUIZZES.ACTIVE)
                .fetchOne()
                .map(getQuizzesRecordQuizRecordMapper());
    }

    @Override
    public Quiz findQuizById(Long id) {
        return dslContext
                .selectFrom(QUIZZES)
                .where(QUIZZES.ID.eq(id))
                .fetchOne(getQuizzesRecordQuizRecordMapper());
    }

    @Override
    public Quiz updateQuiz(Quiz quiz) {
        dslContext
                .update(QUIZZES)
                .set(QUIZZES.DESCRIPTION, quiz.getDescription())
                .set(QUIZZES.TITLE, quiz.getTitle())
                .set(QUIZZES.USER_ID, quiz.getAuthor().getId())
                .set(QUIZZES.CREATED, quiz.getCreated())
                .set(QUIZZES.ACTIVE, quiz.isActive())
                .where(QUIZZES.ID.eq(quiz.getId()))
                .execute();
        return quiz;
    }

    @Override
    public List<Quiz> findAllByAuthorId(Long id) {
        return dslContext
                .selectFrom(QUIZZES)
                .where(QUIZZES.USER_ID.eq(id))
                .fetch()
                .map(getQuizzesRecordQuizRecordMapper());
    }

    @Override
    public void deleteQuizById(Long id) {
        dslContext
                .deleteFrom(QUIZZES)
                .where(QUIZZES.ID.eq(id))
                .execute();
    }

    private RecordMapper<Record, Quiz> getQuizzesRecordQuizRecordMapper() {
        return r -> Quiz
                .builder()
                .id(r.getValue(QUIZZES.ID))
                .description(r.getValue(QUIZZES.DESCRIPTION))
                .author(repositoryUtils.getUserFromRecord(r))
                .quizSessions(repositoryUtils.getQuizSessionsFromRecord(r))
                .title(r.getValue(QUIZZES.TITLE))
                .created(r.getValue(QUIZZES.CREATED))
                .active(r.getValue(QUIZZES.ACTIVE))
                .build();
    }
}
