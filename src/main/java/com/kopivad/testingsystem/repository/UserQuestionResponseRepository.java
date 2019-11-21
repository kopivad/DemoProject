package com.kopivad.testingsystem.repository;

import com.kopivad.testingsystem.model.UserQuestionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserQuestionResponseRepository extends JpaRepository<UserQuestionResponse, Long> {
}
