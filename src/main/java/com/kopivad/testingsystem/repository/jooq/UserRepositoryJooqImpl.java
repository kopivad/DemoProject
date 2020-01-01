package com.kopivad.testingsystem.repository.jooq;

import com.kopivad.testingsystem.exception.UserNotFoundException;
import com.kopivad.testingsystem.model.Quiz;
import com.kopivad.testingsystem.model.Role;
import com.kopivad.testingsystem.model.User;
import com.kopivad.testingsystem.model.db.tables.records.UsersRecord;
import com.kopivad.testingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.kopivad.testingsystem.model.db.Sequences.USERS_ID_SEQ;
import static com.kopivad.testingsystem.model.db.tables.Quizzes.QUIZZES;
import static com.kopivad.testingsystem.model.db.tables.UserRoles.USER_ROLES;
import static com.kopivad.testingsystem.model.db.tables.Users.USERS;
import static org.jooq.impl.DSL.val;

@Repository
@RequiredArgsConstructor
@Primary
public class UserRepositoryJooqImpl implements UserRepository {
    private final DSLContext dslContext;

    @Override
    public User findByEmail(String email) throws UserNotFoundException {
        UsersRecord record = dslContext
                .selectFrom(USERS)
                .where(USERS.EMAIL.eq(email))
                .fetchOne();

        if (record == null)
            throw new UserNotFoundException(String.format("User with email %s not found", email));

        return record.map(this::getUserFromRecord);
    }

    @Override
    public User saveUser(User user) {
        User savedUser = dslContext
                .insertInto(USERS, USERS.ID, USERS.NICKNAME, USERS.EMAIL, USERS.PASSWORD)
                .values(USERS_ID_SEQ.nextval() , val(user.getNickname()), val(user.getEmail()), val(user.getPassword()))
                .returning(USERS.ID, USERS.NICKNAME, USERS.EMAIL, USERS.PASSWORD)
                .fetchOne()
                .map(this::getUserFromRecord);

        dslContext
                .insertInto(USER_ROLES, USER_ROLES.USER_ID, USER_ROLES.ROLES)
                .values(savedUser.getId(), "USER")
                .execute();

        return savedUser;
    }

    @Override
    public User findUserById(Long userId) {
        return dslContext
                .selectFrom(USERS)
                .where(USERS.ID.eq(userId))
                .fetchOne()
                .map(this::getUserFromRecord);
    }

    private User getUserFromRecord(Record record) {
        Long id = record.getValue(USERS.ID, Long.class);
        String nickname = record.getValue(USERS.NICKNAME, String.class);
        String email = record.getValue(USERS.EMAIL, String.class);
        String password = record.getValue(USERS.PASSWORD, String.class);
        List<Quiz> quizzes = dslContext
                .selectFrom(QUIZZES)
                .where(QUIZZES.ID.eq(id))
                .fetch()
                .map(r -> Quiz
                        .builder()
                        .id(r.getValue(QUIZZES.ID))
                        .description(r.getValue(QUIZZES.DESCRIPTION))
                        .title(r.getValue(QUIZZES.TITLE))
                        .build()
                );
        Set<Role> roles = new HashSet<>(dslContext
                .selectDistinct(USER_ROLES.ROLES)
                .from(USER_ROLES)
                .where(USER_ROLES.USER_ID.eq(id))
                .fetch()
                .map(r -> Role.valueOf(r.getValue(USER_ROLES.ROLES))));
        return User
                .builder()
                .id(id)
                .nickname(nickname)
                .email(email)
                .password(password)
                .quizzes(quizzes)
                .roles(roles)
                .build();
    }
}
