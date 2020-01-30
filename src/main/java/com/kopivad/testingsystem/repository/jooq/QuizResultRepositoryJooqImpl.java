package com.kopivad.testingsystem.repository.jooq;

import com.kopivad.testingsystem.domain.QuizResult;
import com.kopivad.testingsystem.repository.QuizResultRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kopivad.testingsystem.domain.db.Sequences.QUIZ_RESULTS_ID_SEQ;
import static com.kopivad.testingsystem.domain.db.tables.QuizResults.QUIZ_RESULTS;
import static org.jooq.impl.DSL.val;

@Repository
@RequiredArgsConstructor
public class QuizResultRepositoryJooqImpl implements QuizResultRepository {
    private final DSLContext dslContext;
    private final RepositoryUtils repositoryUtils;

    @Override
    public QuizResult saveQuizResult(QuizResult quizResult) {
        return dslContext
                .insertInto(QUIZ_RESULTS, QUIZ_RESULTS.ID, QUIZ_RESULTS.SESSION_ID, QUIZ_RESULTS.USER_ID, QUIZ_RESULTS.COUNT_OF_CORRECT, QUIZ_RESULTS.RATING, QUIZ_RESULTS.TOTAL_ANSWERS)
                .values(QUIZ_RESULTS_ID_SEQ.nextval(), val(quizResult.getSession().getId()), val(quizResult.getUser().getId()), val(quizResult.getCountOfCorrect()), val(quizResult.getRating()), val(quizResult.getTotalAnswers()))
                .returning(QUIZ_RESULTS.ID, QUIZ_RESULTS.SESSION_ID, QUIZ_RESULTS.USER_ID, QUIZ_RESULTS.COUNT_OF_CORRECT, QUIZ_RESULTS.RATING, QUIZ_RESULTS.TOTAL_ANSWERS)
                .fetchOne()
                .map(getRecordQuizResultRecordMapper());
    }

    @Override
    public QuizResult findBySessionId(Long sessionId) {
        return dslContext
                .selectFrom(QUIZ_RESULTS)
                .where(QUIZ_RESULTS.SESSION_ID.eq(sessionId))
                .fetchOne()
                .map(getRecordQuizResultRecordMapper());
    }


    @Override
    public List<QuizResult> getAllQuizResultsByUserId(Long id) {
        return dslContext
                .selectFrom(QUIZ_RESULTS)
                .where(QUIZ_RESULTS.USER_ID.eq(id))
                .fetch()
                .map(getRecordQuizResultRecordMapper());
    }

    @Override
    public boolean isResultExist(Long sessionId) {
        return dslContext
                .fetchExists(
                        dslContext
                                .selectOne()
                                .from(QUIZ_RESULTS)
                                .where(QUIZ_RESULTS.SESSION_ID.eq(sessionId))
                );
    }

    @Override
    public List<QuizResult> findAllBySessionId(Long id) {
        return dslContext
                .selectFrom(QUIZ_RESULTS)
                .where(QUIZ_RESULTS.SESSION_ID.eq(id))
                .fetch()
                .map(getRecordQuizResultRecordMapper());
    }

    @Override
    public List<QuizResult> findAllByUserId(Long id) {
        return dslContext
                .selectFrom(QUIZ_RESULTS)
                .where(QUIZ_RESULTS.USER_ID.eq(id))
                .fetch()
                .map(getRecordQuizResultRecordMapper());
    }

    private RecordMapper<Record, QuizResult> getRecordQuizResultRecordMapper() {
        return r -> QuizResult
                .builder()
                .id(r.getValue(QUIZ_RESULTS.ID))
                .session(repositoryUtils.getSessionFromRecord(r))
                .user(repositoryUtils.getUserFromRecord(r))
                .totalAnswers(r.getValue(QUIZ_RESULTS.TOTAL_ANSWERS))
                .rating(r.getValue(QUIZ_RESULTS.RATING))
                .countOfCorrect(r.getValue(QUIZ_RESULTS.COUNT_OF_CORRECT))
                .build();
    }
}
