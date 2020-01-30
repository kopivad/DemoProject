package com.kopivad.testingsystem.repository.jooq;

import com.kopivad.testingsystem.domain.Question;
import com.kopivad.testingsystem.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kopivad.testingsystem.domain.db.Sequences.QUESTIONS_ID_SEQ;
import static com.kopivad.testingsystem.domain.db.Tables.QUESTIONS;
import static org.jooq.impl.DSL.val;

@Repository
@RequiredArgsConstructor
public class QuestionRepositoryJooqImpl implements QuestionRepository {
    private final DSLContext dslContext;
    private final RepositoryUtils repositoryUtils;

    @Override
    public Page<Question> findAllByQuizId(Long quizId, Pageable pageable) {
        int totalPages = dslContext
                .selectCount()
                .from(QUESTIONS)
                .where(QUESTIONS.QUIZ_ID.eq(quizId))
                .fetchOne(0, int.class);

        List<Question> questions = dslContext
                .selectFrom(QUESTIONS)
                .where(QUESTIONS.QUIZ_ID.eq(quizId))
                .orderBy(QUESTIONS.ID.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch()
                .map(getQuestionsRecordQuestionRecordMapper());

        return new PageImpl<>(questions, pageable, totalPages);
    }


    @Override
    public List<Question> findAll() {
        return dslContext
                .selectFrom(QUESTIONS)
                .fetch()
                .map(getQuestionsRecordQuestionRecordMapper());
    }

    @Override
    public List<Question> findAllByQuizId(Long id) {
        return dslContext
                .selectFrom(QUESTIONS)
                .where(QUESTIONS.QUIZ_ID.eq(id))
                .fetch()
                .map(getQuestionsRecordQuestionRecordMapper());
    }


    @Override
    public Question saveQuestion(Question question) {
        return dslContext
                .insertInto(QUESTIONS, QUESTIONS.ID, QUESTIONS.TITLE, QUESTIONS.QUIZ_ID)
                .values(QUESTIONS_ID_SEQ.nextval(), val(question.getTitle()), val(question.getQuiz().getId()))
                .returning(QUESTIONS.ID, QUESTIONS.TITLE, QUESTIONS.QUIZ_ID)
                .fetchOne()
                .map(getQuestionsRecordQuestionRecordMapper());
    }

    @Override
    public Question findQuestionById(Long questionId) {
        return dslContext
                .selectFrom(QUESTIONS)
                .where(QUESTIONS.ID.eq(questionId))
                .fetchOne()
                .map(getQuestionsRecordQuestionRecordMapper());
    }

    @Override
    public Question updateQuestion(Question question) {
        dslContext
                .update(QUESTIONS)
                .set(QUESTIONS.QUIZ_ID, question.getQuiz().getId())
                .set(QUESTIONS.TITLE, question.getTitle())
                .where(QUESTIONS.ID.eq(question.getId()))
                .execute();
        return question;
    }

    @Override
    public long countByQuizId(Long quizId) {
        return dslContext
                .selectCount()
                .from(QUESTIONS)
                .where(QUESTIONS.QUIZ_ID.eq(quizId))
                .fetchOne()
                .into(long.class);
    }

    @Override
    public void deleteQuestion(Long questionId) {
        dslContext
                .deleteFrom(QUESTIONS)
                .where(QUESTIONS.ID.eq(questionId))
                .execute();
    }

    private RecordMapper<Record, Question> getQuestionsRecordQuestionRecordMapper() {
        return r -> Question
                .builder()
                .id(r.getValue(QUESTIONS.QUIZ_ID))
                .title(r.getValue(QUESTIONS.TITLE))
                .quiz(repositoryUtils.getQuizFromRecord(r))
                .answers(repositoryUtils.getAnswersFromRecord(r))
                .userQuestionResponse(repositoryUtils.getUserResponsesFromRecord(r))
                .build();
    }
}
