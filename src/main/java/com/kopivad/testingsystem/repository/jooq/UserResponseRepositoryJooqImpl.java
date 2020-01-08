package com.kopivad.testingsystem.repository.jooq;

import com.kopivad.testingsystem.model.Answer;
import com.kopivad.testingsystem.model.Question;
import com.kopivad.testingsystem.model.QuizSession;
import com.kopivad.testingsystem.model.UserResponce;
import com.kopivad.testingsystem.repository.UserResponseRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kopivad.testingsystem.model.db.Sequences.USER_RESPONCES_ID_SEQ;
import static com.kopivad.testingsystem.model.db.Tables.*;
import static org.jooq.impl.DSL.val;

@RequiredArgsConstructor
@Repository
public class UserResponseRepositoryJooqImpl implements UserResponseRepository {
    private final DSLContext dslContext;

    @Override
    public List<UserResponce> findAllByQuizSessionId(Long sessionId) {
        return dslContext
                .selectFrom(USER_RESPONCES)
                .where(USER_RESPONCES.SESSION_ID.eq(sessionId))
                .fetch()
                .map(this::getUserQuestionResponseFromRecord);
    }

    @Override
    public UserResponce save(UserResponce userAnswer) {
        dslContext
                .insertInto(USER_RESPONCES, USER_RESPONCES.ID, USER_RESPONCES.SESSION_ID, USER_RESPONCES.ANSWER_ID, USER_RESPONCES.QUESTION_ID)
                .values(USER_RESPONCES_ID_SEQ.nextval(), val(userAnswer.getQuizSession().getId()), val(userAnswer.getAnswer().getId()), val(userAnswer.getQuestion().getId()))
                .execute();
        return userAnswer;
    }

    @Override
    public List<UserResponce> findAllByQuestionId(Long id) {
        return dslContext
                .selectFrom(USER_RESPONCES)
                .where(USER_RESPONCES.QUESTION_ID.eq(id))
                .fetch()
                .map(this::getUserQuestionResponseFromRecord);
    }

    @Override
    public List<UserResponce> findAllByAnswerId(Long id) {
        return dslContext
                .selectFrom(USER_RESPONCES)
                .where(USER_RESPONCES.ANSWER_ID.eq(id))
                .fetch()
                .map(this::getUserQuestionResponseFromRecord);
    }

    private UserResponce getUserQuestionResponseFromRecord(Record r) {
        Long id = r.getValue(USER_RESPONCES.ID, Long.class);
        Long answerId = r.getValue(USER_RESPONCES.ANSWER_ID);
        Long questionId = r.getValue(USER_RESPONCES.QUESTION_ID);
        Long sessionId = r.getValue(USER_RESPONCES.SESSION_ID);
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

        QuizSession quizSession = dslContext
                .selectFrom(QUIZ_SESSIONS)
                .where(QUIZ_SESSIONS.ID.eq(sessionId))
                .fetchOne()
                .map(record -> QuizSession
                        .builder()
                        .id(record.getValue(QUIZ_SESSIONS.ID))
                        .build()
                );
        return UserResponce
                .builder()
                .id(id)
                .quizSession(quizSession)
                .answer(answer)
                .question(question)
                .build();
    }
}
