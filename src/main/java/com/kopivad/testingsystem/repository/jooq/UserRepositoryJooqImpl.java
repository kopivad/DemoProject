package com.kopivad.testingsystem.repository.jooq;

import com.kopivad.testingsystem.domain.Role;
import com.kopivad.testingsystem.domain.User;
import com.kopivad.testingsystem.domain.db.tables.records.UsersRecord;
import com.kopivad.testingsystem.exception.UserNotFoundException;
import com.kopivad.testingsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static com.kopivad.testingsystem.domain.db.Sequences.USERS_ID_SEQ;
import static com.kopivad.testingsystem.domain.db.tables.UserRoles.USER_ROLES;
import static com.kopivad.testingsystem.domain.db.tables.Users.USERS;
import static org.jooq.impl.DSL.val;

@Repository
@RequiredArgsConstructor
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
                .values(USERS_ID_SEQ.nextval(), val(user.getNickname()), val(user.getEmail()), val(user.getPassword()))
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
                .fetchOne(r -> User
                        .builder()
                        .id(r.getValue(USERS.ID))
                        .nickname(r.getValue(USERS.NICKNAME))
                        .email(r.getValue(USERS.EMAIL))
                        .password(r.getValue(USERS.PASSWORD))
                        .roles(new HashSet<>(dslContext
                                .selectDistinct(USER_ROLES.ROLES)
                                .from(USER_ROLES)
                                .where(USER_ROLES.USER_ID.eq(r.getValue(USERS.ID)))
                                .fetch(r1 -> Role.valueOf(r1.getValue(USER_ROLES.ROLES)))))
                        .build());
    }

    @Override
    public User updateUser(User user) {
        dslContext.update(USERS)
                .set(USERS.EMAIL, user.getEmail())
                .set(USERS.NICKNAME, user.getNickname())
                .set(USERS.PASSWORD, user.getPassword())
                .where(USERS.ID.eq(user.getId()))
                .execute();

//        dslContext.update(USER_ROLES)
//                .set(USER_ROLES.ROLES, user.getRoles())
//                .where(USER_ROLES.USER_ID.eq(user.getId()))
//                .execute();

        return user;
    }

    @Override
    public List<User> findAll() {
        return dslContext
                .selectFrom(USERS)
                .fetchInto(User.class);
    }

    private User getUserFromRecord(Record record) {
        return User
                .builder()
                .id(record.getValue(USERS.ID))
                .nickname(record.getValue(USERS.NICKNAME))
                .email(record.getValue(USERS.EMAIL))
                .password(record.getValue(USERS.PASSWORD))
                .roles(new HashSet<>(dslContext
                        .selectDistinct(USER_ROLES.ROLES)
                        .from(USER_ROLES)
                        .where(USER_ROLES.USER_ID.eq(record.getValue(USERS.ID)))
                        .fetch()
                        .map(r -> Role.valueOf(r.getValue(USER_ROLES.ROLES)))))
                .build();
    }


}
