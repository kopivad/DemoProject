/*
 * This file is generated by jOOQ.
 */
package com.kopivad.testingsystem.model.db;


import com.kopivad.testingsystem.model.db.tables.Answers;
import com.kopivad.testingsystem.model.db.tables.Questions;
import com.kopivad.testingsystem.model.db.tables.QuizSessions;
import com.kopivad.testingsystem.model.db.tables.Quizzes;
import com.kopivad.testingsystem.model.db.tables.UserResponces;
import com.kopivad.testingsystem.model.db.tables.Users;

import javax.annotation.processing.Generated;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables of the <code>public</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index ANSWERS_PKEY = Indexes0.ANSWERS_PKEY;
    public static final Index QUESTIONS_PKEY = Indexes0.QUESTIONS_PKEY;
    public static final Index QUIZ_SESSIONS_PKEY = Indexes0.QUIZ_SESSIONS_PKEY;
    public static final Index QUIZZES_PKEY = Indexes0.QUIZZES_PKEY;
    public static final Index USER_RESPONCES_PKEY = Indexes0.USER_RESPONCES_PKEY;
    public static final Index USERS_PKEY = Indexes0.USERS_PKEY;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index ANSWERS_PKEY = Internal.createIndex("answers_pkey", Answers.ANSWERS, new OrderField[] { Answers.ANSWERS.ID }, true);
        public static Index QUESTIONS_PKEY = Internal.createIndex("questions_pkey", Questions.QUESTIONS, new OrderField[] { Questions.QUESTIONS.ID }, true);
        public static Index QUIZ_SESSIONS_PKEY = Internal.createIndex("quiz_sessions_pkey", QuizSessions.QUIZ_SESSIONS, new OrderField[] { QuizSessions.QUIZ_SESSIONS.ID }, true);
        public static Index QUIZZES_PKEY = Internal.createIndex("quizzes_pkey", Quizzes.QUIZZES, new OrderField[] { Quizzes.QUIZZES.ID }, true);
        public static Index USER_RESPONCES_PKEY = Internal.createIndex("user_responces_pkey", UserResponces.USER_RESPONCES, new OrderField[] { UserResponces.USER_RESPONCES.ID }, true);
        public static Index USERS_PKEY = Internal.createIndex("users_pkey", Users.USERS, new OrderField[] { Users.USERS.ID }, true);
    }
}