package com.kopivad.testingsystem.repository.jooq;

import com.kopivad.testingsystem.domain.Answer;
import com.kopivad.testingsystem.domain.Question;
import com.kopivad.testingsystem.repository.AnswerRepository;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kopivad.testingsystem.domain.db.Sequences.ANSWERS_ID_SEQ;
import static com.kopivad.testingsystem.domain.db.tables.Answers.ANSWERS;
import static org.jooq.impl.DSL.val;

@Repository
@AllArgsConstructor
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
        return dslContext
                .insertInto(ANSWERS, ANSWERS.ID, ANSWERS.TEXT, ANSWERS.IS_RIGHT, ANSWERS.QUESTION_ID)
                .values(ANSWERS_ID_SEQ.nextval() ,val(answer.getText()), val(answer.isRight()), val(answer.getQuestion().getId()))
                .returning(ANSWERS.ID, ANSWERS.TEXT, ANSWERS.QUESTION_ID, ANSWERS.IS_RIGHT)
                .fetchOne()
                .map(this::getAnswerFromRecord);
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
    public Answer updateAnswer(Answer answer) {
        dslContext.update(ANSWERS)
                .set(ANSWERS.TEXT, answer.getText())
                .set(ANSWERS.IS_RIGHT, answer.isRight())
                .set(ANSWERS.QUESTION_ID, answer.getQuestion().getId())
                .where(ANSWERS.ID.eq(answer.getId()))
                .execute();
        return answer;
    }

    @Override
    public void deleteAnswerById(Long id) {
        dslContext
                .deleteFrom(ANSWERS)
                .where(ANSWERS.ID.eq(id))
                .execute();
    }

    private Answer getAnswerFromRecord(Record record) {
        return Answer
                .builder()
                .id(record.getValue(ANSWERS.ID))
                .isRight(record.getValue(ANSWERS.IS_RIGHT))
                .question(Question.builder().id(record.getValue(ANSWERS.QUESTION_ID)).build())
                .text(record.getValue(ANSWERS.TEXT))
                .build();
    }
}
