/*
 * This file is generated by jOOQ.
 */
package com.kopivad.testingsystem.domain.db.tables;


import com.kopivad.testingsystem.domain.db.Indexes;
import com.kopivad.testingsystem.domain.db.Keys;
import com.kopivad.testingsystem.domain.db.Public;
import com.kopivad.testingsystem.domain.db.tables.records.QuizResultsRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.processing.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row6;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.12.3"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class QuizResults extends TableImpl<QuizResultsRecord> {

    private static final long serialVersionUID = -1471376333;

    /**
     * The reference instance of <code>public.quiz_results</code>
     */
    public static final QuizResults QUIZ_RESULTS = new QuizResults();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<QuizResultsRecord> getRecordType() {
        return QuizResultsRecord.class;
    }

    /**
     * The column <code>public.quiz_results.id</code>.
     */
    public final TableField<QuizResultsRecord, Long> ID = createField(DSL.name("id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false).defaultValue(org.jooq.impl.DSL.field("nextval('quiz_results_id_seq'::regclass)", org.jooq.impl.SQLDataType.BIGINT)), this, "");

    /**
     * The column <code>public.quiz_results.count_of_correct</code>.
     */
    public final TableField<QuizResultsRecord, Long> COUNT_OF_CORRECT = createField(DSL.name("count_of_correct"), org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.quiz_results.rating</code>.
     */
    public final TableField<QuizResultsRecord, Float> RATING = createField(DSL.name("rating"), org.jooq.impl.SQLDataType.REAL.nullable(false), this, "");

    /**
     * The column <code>public.quiz_results.total_answers</code>.
     */
    public final TableField<QuizResultsRecord, Long> TOTAL_ANSWERS = createField(DSL.name("total_answers"), org.jooq.impl.SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.quiz_results.session_id</code>.
     */
    public final TableField<QuizResultsRecord, Long> SESSION_ID = createField(DSL.name("session_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>public.quiz_results.user_id</code>.
     */
    public final TableField<QuizResultsRecord, Long> USER_ID = createField(DSL.name("user_id"), org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * Create a <code>public.quiz_results</code> table reference
     */
    public QuizResults() {
        this(DSL.name("quiz_results"), null);
    }

    /**
     * Create an aliased <code>public.quiz_results</code> table reference
     */
    public QuizResults(String alias) {
        this(DSL.name(alias), QUIZ_RESULTS);
    }

    /**
     * Create an aliased <code>public.quiz_results</code> table reference
     */
    public QuizResults(Name alias) {
        this(alias, QUIZ_RESULTS);
    }

    private QuizResults(Name alias, Table<QuizResultsRecord> aliased) {
        this(alias, aliased, null);
    }

    private QuizResults(Name alias, Table<QuizResultsRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> QuizResults(Table<O> child, ForeignKey<O, QuizResultsRecord> key) {
        super(child, key, QUIZ_RESULTS);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.QUIZ_RESULTS_PKEY);
    }

    @Override
    public Identity<QuizResultsRecord, Long> getIdentity() {
        return Keys.IDENTITY_QUIZ_RESULTS;
    }

    @Override
    public UniqueKey<QuizResultsRecord> getPrimaryKey() {
        return Keys.QUIZ_RESULTS_PKEY;
    }

    @Override
    public List<UniqueKey<QuizResultsRecord>> getKeys() {
        return Arrays.<UniqueKey<QuizResultsRecord>>asList(Keys.QUIZ_RESULTS_PKEY);
    }

    @Override
    public List<ForeignKey<QuizResultsRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<QuizResultsRecord, ?>>asList(Keys.QUIZ_RESULTS__FK8B0O8JG288Q504M3J6PF4414U, Keys.QUIZ_RESULTS__FKC31XKN83Q9V6YF9GH2SPKVXRC);
    }

    public QuizSessions quizSessions() {
        return new QuizSessions(this, Keys.QUIZ_RESULTS__FK8B0O8JG288Q504M3J6PF4414U);
    }

    public Users users() {
        return new Users(this, Keys.QUIZ_RESULTS__FKC31XKN83Q9V6YF9GH2SPKVXRC);
    }

    @Override
    public QuizResults as(String alias) {
        return new QuizResults(DSL.name(alias), this);
    }

    @Override
    public QuizResults as(Name alias) {
        return new QuizResults(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public QuizResults rename(String name) {
        return new QuizResults(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public QuizResults rename(Name name) {
        return new QuizResults(name, null);
    }

    // -------------------------------------------------------------------------
    // Row6 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row6<Long, Long, Float, Long, Long, Long> fieldsRow() {
        return (Row6) super.fieldsRow();
    }
}