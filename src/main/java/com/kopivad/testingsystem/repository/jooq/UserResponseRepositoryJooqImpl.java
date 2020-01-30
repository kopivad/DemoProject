package com.kopivad.testingsystem.repository.jooq;

import com.kopivad.testingsystem.domain.UserResponse;
import com.kopivad.testingsystem.domain.db.tables.records.UserResponcesRecord;
import com.kopivad.testingsystem.repository.UserResponseRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kopivad.testingsystem.domain.db.Sequences.USER_RESPONCES_ID_SEQ;
import static com.kopivad.testingsystem.domain.db.Tables.USER_RESPONCES;
import static org.jooq.impl.DSL.val;

@RequiredArgsConstructor
@Repository
public class UserResponseRepositoryJooqImpl implements UserResponseRepository {
    private final DSLContext dslContext;
    private final RepositoryUtils repositoryUtils;

    @Override
    public List<UserResponse> findAllByQuizSessionId(Long sessionId) {
        return dslContext
                .selectFrom(USER_RESPONCES)
                .where(USER_RESPONCES.SESSION_ID.eq(sessionId))
                .fetch()
                .map(getUserResponcesRecordUserResponseRecordMapper());
    }

    @Override
    public boolean isUserResponceExist(Long questionId, Long sessionId) {
        return dslContext
                .fetchExists(
                        dslContext
                                .selectOne()
                                .from(USER_RESPONCES)
                                .where(USER_RESPONCES.QUESTION_ID.eq(questionId).and(USER_RESPONCES.SESSION_ID.eq(sessionId)))
                );
    }

    @Override
    public UserResponse save(UserResponse userAnswer) {
        dslContext
                .insertInto(USER_RESPONCES, USER_RESPONCES.ID, USER_RESPONCES.SESSION_ID, USER_RESPONCES.ANSWER_ID, USER_RESPONCES.QUESTION_ID)
                .values(USER_RESPONCES_ID_SEQ.nextval(), val(userAnswer.getQuizSession().getId()), val(userAnswer.getAnswer().getId()), val(userAnswer.getQuestion().getId()))
                .execute();
        return userAnswer;
    }

    @Override
    public List<UserResponse> findAllByQuestionId(Long id) {
        return dslContext
                .selectFrom(USER_RESPONCES)
                .where(USER_RESPONCES.QUESTION_ID.eq(id))
                .fetch()
                .map(getUserResponcesRecordUserResponseRecordMapper());
    }

    @Override
    public List<UserResponse> findAllByAnswerId(Long id) {
        return dslContext
                .selectFrom(USER_RESPONCES)
                .where(USER_RESPONCES.ANSWER_ID.eq(id))
                .fetch()
                .map(getUserResponcesRecordUserResponseRecordMapper());
    }

    private RecordMapper<UserResponcesRecord, UserResponse> getUserResponcesRecordUserResponseRecordMapper() {
        return r -> UserResponse
                .builder()
                .id(r.getValue(USER_RESPONCES.ID))
                .quizSession(repositoryUtils.getSessionFromRecord(r))
                .answer(repositoryUtils.getAnswerFromRecord(r))
                .question(repositoryUtils.getQuestionFromRecord(r))
                .build();
    }
}
