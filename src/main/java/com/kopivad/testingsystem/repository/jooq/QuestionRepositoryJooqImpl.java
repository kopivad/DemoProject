package com.kopivad.testingsystem.repository.jooq;

import com.kopivad.testingsystem.model.Answer;
import com.kopivad.testingsystem.model.Question;
import com.kopivad.testingsystem.model.Quiz;
import com.kopivad.testingsystem.model.UserQuestionResponse;
import com.kopivad.testingsystem.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kopivad.testingsystem.model.db.Tables.*;

@Repository
@Primary
@RequiredArgsConstructor
public class QuestionRepositoryJooqImpl implements QuestionRepository {
    private final DSLContext dslContext;

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
                .map(this::getQuestionFromRecord);
        return new PageImpl<>(questions, pageable, totalPages);
    }


    @Override
    public List<Question> findAll() {
        return dslContext
                .select()
                .from(QUESTIONS)
                .fetch()
                .map(this::getQuestionFromRecord);
    }

    @Override
    public List<Question> findAllByQuizId(Long id) {
        return dslContext
                .selectFrom(QUESTIONS)
                .where(QUESTIONS.QUIZ_ID.eq(id))
                .fetch()
                .map(this::getQuestionFromRecord);
    }

    @Override
    public Question saveQuestion(Question question) {
        dslContext
                .insertInto(QUESTIONS, QUESTIONS.ID, QUESTIONS.TITLE, QUESTIONS.QUIZ_ID)
                .values(0L, question.getTitle(), question.getQuiz().getId())
                .execute();
        return question;
    }

    @Override
    public Question findQuestionById(Long questionId) {
        return dslContext
                .selectFrom(QUESTIONS)
                .where(QUESTIONS.ID.eq(questionId))
                .fetchOne()
                .map(this::getQuestionFromRecord);
    }

    @Override
    public void updateQuestion(Question question) {
        dslContext
                .update(QUESTIONS)
                .set(QUESTIONS.QUIZ_ID, question.getQuiz().getId())
                .set(QUESTIONS.TITLE, question.getTitle())
                .where(QUESTIONS.ID.eq(question.getId()))
                .execute();
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

    private Question getQuestionFromRecord(Record r) {
        Long id = r.getValue(QUESTIONS.ID, Long.class);
        String title = r.getValue(QUESTIONS.TITLE, String.class);
        Long quizId = r.getValue(QUESTIONS.QUIZ_ID, Long.class);
        Quiz quiz = dslContext
                .selectFrom(QUIZZES)
                .where(QUIZZES.ID.eq(quizId))
                .fetchOne()
                .map(record -> Quiz
                        .builder()
                        .id(record.getValue(QUIZZES.ID, Long.class))
                        .title(record.getValue(QUIZZES.TITLE, String.class))
                        .description(record.getValue(QUIZZES.DESCRIPTION, String.class))
                        .build()
                );
        List<Answer> answers = dslContext
                .selectFrom(ANSWERS)
                .where(ANSWERS.QUESTION_ID.eq(id))
                .fetch()
                .map(record -> Answer
                        .builder()
                        .id(record.getValue(ANSWERS.ID, Long.class))
                        .isRight(record.getValue(ANSWERS.IS_RIGHT, Boolean.class))
                        .text(record.getValue(ANSWERS.TEXT, String.class))
                        .build()
                );
        List<UserQuestionResponse> responses = dslContext
                .selectFrom(USER_RESPONCES)
                .where(USER_RESPONCES.QUESTION_ID.eq(id))
                .fetch()
                .map(record -> UserQuestionResponse
                        .builder()
                        .id(record.getValue(USER_RESPONCES.ID, Long.class))
                        .sessionCode(record.getValue(USER_RESPONCES.SESSION_CODE, String.class))
                        .build()
                );
        return Question
                .builder()
                .id(id)
                .title(title)
                .quiz(
                        Quiz
                                .builder()
                                .id(quiz.getId())
                                .title(quiz.getTitle())
                                .description(quiz.getDescription())
                                .build()
                )
                .answers(answers)
                .userQuestionResponses(responses)
                .build();
    }
}
