package com.kopivad.testingsystem.repository.jooq;

import com.kopivad.testingsystem.model.Answer;
import com.kopivad.testingsystem.model.Question;
import com.kopivad.testingsystem.model.Quiz;
import com.kopivad.testingsystem.model.UserQuestionResponse;
import com.kopivad.testingsystem.repository.AnswerRepository;
import com.kopivad.testingsystem.repository.QuestionRepository;
import com.kopivad.testingsystem.repository.UserQuestionResponseRepository;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.kopivad.testingsystem.model.db.tables.Answers.ANSWERS;

@Repository
public class AnswerRepositoryJooqImpl implements AnswerRepository {
    private final DSLContext dslContext;
    private final QuestionRepository questionRepository;
    private final UserQuestionResponseRepository responseRepository;

    @Autowired
    public AnswerRepositoryJooqImpl(DSLContext dslContext, @Lazy QuestionRepository questionRepository, @Lazy UserQuestionResponseRepository responseRepository) {
        this.dslContext = dslContext;
        this.questionRepository = questionRepository;
        this.responseRepository = responseRepository;
    }

    @Override
    public List<Answer> findAllByQuestionId(Long id) {
        return dslContext
                .select()
                .from(ANSWERS)
                .where(ANSWERS.ID.eq(id))
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

    private Answer getAnswerFromRecord(Record record) {
        Long questionId = record.getValue(ANSWERS.QUESTION_ID, Long.class);
        Long id = record.getValue(ANSWERS.ID, Long.class);
        Question question = questionRepository.findQuestionById(questionId);
        String text = record.getValue(ANSWERS.TEXT, String.class);
        Boolean isRight = record.getValue(ANSWERS.IS_RIGHT, Boolean.class);
//        List<UserQuestionResponse> responses = responseRepository.findAllByAnswerId(id);
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
                .userQuestionResponses(new ArrayList<>())
                .text(text)
                .build();
    }
}
