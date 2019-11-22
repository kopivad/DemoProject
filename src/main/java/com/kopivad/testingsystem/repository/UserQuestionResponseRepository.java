package com.kopivad.testingsystem.repository;

import com.kopivad.testingsystem.model.UserQuestionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserQuestionResponseRepository extends JpaRepository<UserQuestionResponse, String> {
    List<UserQuestionResponse> findAllBySessionCode(String secodessionId);
}
