package com.kopivad.demoproject.dao;

import com.kopivad.demoproject.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> getAllByTopicId(Long id);
}
