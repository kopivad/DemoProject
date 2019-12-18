package com.kopivad.testingsystem.repository.jooq;


import com.kopivad.testingsystem.model.Question;
import com.kopivad.testingsystem.model.Quiz;
import com.kopivad.testingsystem.model.User;
import com.kopivad.testingsystem.repository.QuestionRepository;
import com.kopivad.testingsystem.repository.QuizRepository;
import com.kopivad.testingsystem.repository.UserRepository;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.kopivad.testingsystem.model.db.Tables.QUESTIONS;
import static com.kopivad.testingsystem.model.db.Tables.QUIZZES;

@Repository
@Primary
public class QuizRepositoryJooqImpl implements QuizRepository {
    private final UserRepository userRepository;
    private final DSLContext dslContext;

    @Autowired
    public QuizRepositoryJooqImpl(UserRepository userRepository, DSLContext dslContext, @Lazy QuestionRepository questionRepository) {
        this.userRepository = userRepository;
        this.dslContext = dslContext;
    }

    @Override
    public List<Quiz> findAll() {
        return dslContext
                .selectFrom(QUIZZES)
                .fetch()
                .map(this::getQuizFromRecord);
    }

    @Override
    public Quiz saveQuiz(Quiz quiz) {
        dslContext
                .insertInto(QUIZZES, QUIZZES.ID, QUIZZES.TITLE, QUIZZES.DESCRIPTION, QUIZZES.USER_ID)
                .values(0L, quiz.getTitle(), quiz.getDescription(), quiz.getAuthor().getId())
                .execute();
        return quiz;
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
    public void updateQuiz(Quiz quiz) {
        dslContext
                .update(QUIZZES)
                .set(QUIZZES.DESCRIPTION, quiz.getDescription())
                .set(QUIZZES.TITLE, quiz.getTitle())
                .set(QUIZZES.USER_ID, quiz.getAuthor().getId())
                .where(QUIZZES.ID.eq(quiz.getId()))
                .execute();
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
        Long id = r.getValue(QUIZZES.ID, Long.class);
        String title = r.getValue(QUIZZES.TITLE, String.class);
        String description = r.getValue(QUIZZES.DESCRIPTION);
        Long userId = r.getValue(QUIZZES.USER_ID, Long.class);
        User user = userRepository.findUserById(userId);
        List<Question> questions = dslContext
                .selectFrom(QUESTIONS)
                .where(QUESTIONS.QUIZ_ID.eq(id))
                .fetch()
                .map(record -> Question
                        .builder()
                        .id(record.getValue(QUESTIONS.ID, Long.class))
                        .title(record.getValue(QUESTIONS.TITLE, String.class))
                        .build()
                );
        return Quiz
                .builder()
                .id(id)
                .description(description)
                .questions(questions)
                .author(
                        User
                                .builder()
                                .id(user.getId())
                                .email(user.getEmail())
                                .nickname(user.getNickname())
                                .password(user.getPassword())
                                .build()
                )
                .title(title)
                .build();
    }
}
