package com.kopivad.testingsystem.repository.jooq;

import com.kopivad.testingsystem.model.Answer;
import com.kopivad.testingsystem.model.Question;
import com.kopivad.testingsystem.model.UserQuestionResponse;
import com.kopivad.testingsystem.repository.UserQuestionResponseRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kopivad.testingsystem.model.db.Tables.*;

@RequiredArgsConstructor
@Repository
@Primary
public class UserQuestionResponseRepositoryJooqImpl implements UserQuestionResponseRepository {
    private final DSLContext dslContext;

    @Override
    public List<UserQuestionResponse> findAllBySessionCode(String sessionCode) {
        return dslContext
                .selectFrom(USER_RESPONCES)
                .where(USER_RESPONCES.SESSION_CODE.eq(sessionCode))
                .fetch()
                .map(this::getUserQuestionResponseFromRecord);
    }

    @Override
    public UserQuestionResponse save(UserQuestionResponse userAnswer) {
        dslContext
                .insertInto(USER_RESPONCES, USER_RESPONCES.ID, USER_RESPONCES.SESSION_CODE, USER_RESPONCES.ANSWER_ID, USER_RESPONCES.QUESTION_ID)
                .values(0L, userAnswer.getSessionCode(), userAnswer.getAnswer().getId(), userAnswer.getQuestion().getId())
                .execute();
        return userAnswer;
    }

    @Override
    public List<UserQuestionResponse> findAllByQuestionId(Long id) {
        return dslContext
                .selectFrom(USER_RESPONCES)
                .where(USER_RESPONCES.QUESTION_ID.eq(id))
                .fetch()
                .map(this::getUserQuestionResponseFromRecord);
    }

    @Override
    public List<UserQuestionResponse> findAllByAnswerId(Long id) {
        return dslContext
                .selectFrom(USER_RESPONCES)
                .where(USER_RESPONCES.ANSWER_ID.eq(id))
                .fetch()
                .map(this::getUserQuestionResponseFromRecord);
    }

    private UserQuestionResponse getUserQuestionResponseFromRecord(Record r) {
        Long id = r.getValue(USER_RESPONCES.ID, Long.class);
        Long answerId = r.getValue(USER_RESPONCES.ANSWER_ID);
        Long questionId = r.getValue(USER_RESPONCES.QUESTION_ID);
        String sessionCode = r.getValue(USER_RESPONCES.SESSION_CODE);
        Answer answer = dslContext
                .selectFrom(ANSWERS)
                .where(ANSWERS.ID.eq(answerId))
                .fetchOne()
                .map(record -> Answer
                        .builder()
                        .id(record.getValue(ANSWERS.ID))
                        .text(record.getValue(ANSWERS.TEXT))
                        .isRight(record.getValue(ANSWERS.IS_RIGHT))
                        .build());
        Question question = dslContext
                .selectFrom(QUESTIONS)
                .where(QUESTIONS.ID.eq(questionId))
                .fetchOne()
                .map(record -> Question
                        .builder()
                        .id(record.getValue(QUESTIONS.ID))
                        .title(record.getValue(QUESTIONS.TITLE))
                        .build()
                );
        return UserQuestionResponse
                .builder()
                .id(id)
                .sessionCode(sessionCode)
                .answer(answer)
                .question(question)
                .build();
    }
}
