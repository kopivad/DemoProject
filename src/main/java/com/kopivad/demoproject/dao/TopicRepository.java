package com.kopivad.demoproject.dao;

import com.kopivad.demoproject.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findAllById(Long id);
    List<Topic> findAll();
    Topic findTopicById(Long id);
}
