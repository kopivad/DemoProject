package com.kopivad.testingsystem.repository.jooq;

import com.kopivad.testingsystem.model.Answer;
import com.kopivad.testingsystem.model.Question;
import com.kopivad.testingsystem.model.Quiz;
import com.kopivad.testingsystem.model.UserQuestionResponse;
import com.kopivad.testingsystem.repository.AnswerRepository;
import com.kopivad.testingsystem.repository.QuestionRepository;
import com.kopivad.testingsystem.repository.QuizRepository;
import com.kopivad.testingsystem.repository.UserQuestionResponseRepository;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kopivad.testingsystem.model.db.Tables.QUESTIONS;

@Repository
public class QuestionRepositoryJooqImpl implements QuestionRepository {
    private final DSLContext dslContext;
    private final QuizRepository quizRepository;
    private final AnswerRepository answerRepository;
    private final UserQuestionResponseRepository responseRepository;

    @Autowired
    public QuestionRepositoryJooqImpl(DSLContext dslContext, QuizRepository quizRepository, @Lazy AnswerRepository answerRepository,@Lazy UserQuestionResponseRepository responseRepository) {
        this.dslContext = dslContext;
        this.quizRepository = quizRepository;
        this.answerRepository = answerRepository;
        this.responseRepository = responseRepository;
    }

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
        List<Question> questions = dslContext
                .select()
                .from(QUESTIONS)
                .fetch()
                .map(this::getQuestionFromRecord);

        int size = questions.size();

        return questions;
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
        Question question = dslContext
                .selectFrom(QUESTIONS)
                .where(QUESTIONS.ID.eq(questionId))
                .fetchOne()
                .map(this::getQuestionFromRecord);
        return question;
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

    private Question getQuestionFromRecord(Record r) {
        Long id = r.getValue(QUESTIONS.ID, Long.class);
        String title = r.getValue(QUESTIONS.TITLE, String.class);
        Long quizId = r.getValue(QUESTIONS.QUIZ_ID, Long.class);
        Quiz quiz = quizRepository.findQuizById(quizId);
        List<Answer> answers = answerRepository.findAllByQuestionId(id);
        List<UserQuestionResponse> response = responseRepository.findAllByQuestionId(id);

        return Question
                .builder()
                .id(id)
                .title(title)
                .quiz(quiz)
                .answers(answers)
                .userQuestionResponses(response)
                .build();
    }
}
