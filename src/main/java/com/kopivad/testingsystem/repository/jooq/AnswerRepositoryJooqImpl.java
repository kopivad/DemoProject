package com.kopivad.testingsystem.repository.jooq;

import com.kopivad.testingsystem.model.Answer;
import com.kopivad.testingsystem.model.Question;
import com.kopivad.testingsystem.model.UserQuestionResponse;
import com.kopivad.testingsystem.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kopivad.testingsystem.model.db.tables.Answers.ANSWERS;
import static com.kopivad.testingsystem.model.db.tables.Questions.QUESTIONS;
import static com.kopivad.testingsystem.model.db.tables.UserResponces.USER_RESPONCES;

@Repository
@Primary
@RequiredArgsConstructor
public class AnswerRepositoryJooqImpl implements AnswerRepository {
    private final DSLContext dslContext;

    @Override
    public List<Answer> findAllByQuestionId(Long id) {
        return dslContext
                .select()
                .from(ANSWERS)
                .where(ANSWERS.QUESTION_ID.eq(id))
                .fetch()
                .map(this::getAnswerFromRecord);
    }

    @Override
    public List<Answer> findAll() {
        return dslContext
                .selectFrom(ANSWERS)
                .fetch()
                .map(this::getAnswerFromRecord);
    }

    @Override
    public Answer saveAnswer(Answer answer) {
        dslContext
                .insertInto(ANSWERS, ANSWERS.ID, ANSWERS.TEXT, ANSWERS.IS_RIGHT, ANSWERS.QUESTION_ID)
                .values(0L, answer.getText(), answer.isRight(), answer.getQuestion().getId())
                .execute();
        return answer;
    }

    @Override
    public Answer findAnswerById(Long id) {
        return dslContext.select()
                .from(ANSWERS)
                .where(ANSWERS.ID.eq(id))
                .fetchOne()
                .map(this::getAnswerFromRecord);
    }

    @Override
    public void updateAnswer(Answer answer) {
        dslContext.update(ANSWERS)
                .set(ANSWERS.TEXT, answer.getText())
                .set(ANSWERS.IS_RIGHT, answer.isRight())
                .set(ANSWERS.QUESTION_ID, answer.getQuestion().getId())
                .where(ANSWERS.ID.eq(answer.getId()))
                .execute();
    }

    @Override
    public void deleteAnswerById(Long id) {
        dslContext
                .deleteFrom(ANSWERS)
                .where(ANSWERS.ID.eq(id))
                .execute();
    }

    private Answer getAnswerFromRecord(Record record) {
        Long questionId = record.getValue(ANSWERS.QUESTION_ID, Long.class);
        Long id = record.getValue(ANSWERS.ID, Long.class);
        Question question = dslContext
                .selectFrom(QUESTIONS)
                .where(QUESTIONS.ID.eq(questionId))
                .fetchOne()
                .map(r -> Question
                        .builder()
                        .id(r.getValue(QUESTIONS.ID, Long.class))
                        .title(r.getValue(QUESTIONS.TITLE, String.class))
                        .build());

        String text = record.getValue(ANSWERS.TEXT, String.class);
        Boolean isRight = record.getValue(ANSWERS.IS_RIGHT, Boolean.class);
        List<UserQuestionResponse> responses = dslContext
                .selectFrom(USER_RESPONCES)
                .where(USER_RESPONCES.ANSWER_ID.eq(id))
                .fetch()
                .map(r -> UserQuestionResponse
                        .builder()
                        .id(r.getValue(USER_RESPONCES.ID, Long.class))
                        .sessionCode(r.getValue(USER_RESPONCES.SESSION_CODE, String.class))
                        .build()
                );
        return Answer
                .builder()
                .id(id)
                .isRight(isRight)
                .question(
                        Question
                        .builder()
                        .id(question.getId())
                        .title(question.getTitle())
                        .build()
                )
                .userQuestionResponses(responses)
                .text(text)
                .build();
    }
}
