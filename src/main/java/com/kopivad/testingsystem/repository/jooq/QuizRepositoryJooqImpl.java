package com.kopivad.testingsystem.repository.jooq;


import com.kopivad.testingsystem.model.Question;
import com.kopivad.testingsystem.model.Quiz;
import com.kopivad.testingsystem.model.User;
import com.kopivad.testingsystem.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kopivad.testingsystem.model.db.Sequences.QUIZZES_ID_SEQ;
import static com.kopivad.testingsystem.model.db.Tables.*;
import static org.jooq.impl.DSL.val;

@Repository
@RequiredArgsConstructor
public class QuizRepositoryJooqImpl implements QuizRepository {
    private final DSLContext dslContext;

    @Override
    public List<Quiz> findAll() {
        return dslContext
                .selectFrom(QUIZZES)
                .fetch()
                .map(this::getQuizFromRecord);
    }

    @Override
    public Quiz saveQuiz(Quiz quiz) {
        return dslContext
                .insertInto(QUIZZES, QUIZZES.ID, QUIZZES.TITLE, QUIZZES.DESCRIPTION, QUIZZES.USER_ID, QUIZZES.CREATED, QUIZZES.ACTIVE)
                .values(QUIZZES_ID_SEQ.nextval(), val(quiz.getTitle()), val(quiz.getDescription()), val(quiz.getAuthor().getId()), val(quiz.getCreated()), val(quiz.isActive()))
                .returning(QUIZZES.ID, QUIZZES.TITLE, QUIZZES.DESCRIPTION, QUIZZES.USER_ID, QUIZZES.CREATED, QUIZZES.ACTIVE)
                .fetchOne()
                .map(this::getQuizFromRecord);
    }

    @Override
    public Quiz findQuizById(Long id) {
        return dslContext
                .selectFrom(QUIZZES)
                .where(QUIZZES.ID.eq(id))
                .fetchOne()
                .map(this::getQuizFromRecord);
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
                .map(this::getQuizFromRecord);
    }

    private Quiz getQuizFromRecord(Record r) {
        User author = dslContext
                .selectFrom(USERS)
                .where(USERS.ID.eq(r.getValue(QUIZZES.USER_ID)))
                .fetchOne()
                .map(record -> User
                        .builder()
                        .id(record.getValue(USERS.ID))
                        .password(record.getValue(USERS.PASSWORD))
                        .nickname(record.getValue(USERS.NICKNAME))
                        .email(record.getValue(USERS.EMAIL))
                        .build()
                );
        List<Question> questions = dslContext
                .selectFrom(QUESTIONS)
                .where(QUESTIONS.QUIZ_ID.eq(r.getValue(QUIZZES.ID)))
                .fetch()
                .map(record -> Question
                        .builder()
                        .id(record.getValue(QUESTIONS.ID))
                        .title(record.getValue(QUESTIONS.TITLE))
                        .build()
                );
        return Quiz
                .builder()
                .id(r.getValue(QUIZZES.ID))
                .description(r.getValue(QUIZZES.DESCRIPTION))
                .questions(questions)
                .author(author)
                .title(r.getValue(QUIZZES.TITLE))
                .created(r.getValue(QUIZZES.CREATED))
                .active(r.getValue(QUIZZES.ACTIVE))
                .build();
    }
}
