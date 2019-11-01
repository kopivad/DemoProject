package com.kopivad.demoproject.service;

import com.kopivad.demoproject.model.Test;
import com.kopivad.demoproject.model.Topic;

import java.util.List;

public interface TestService {
    List<Test> getAllTestsByTopicId(Long id);

    Topic findCurrentTopicById(Long id);

    List<Test> getTestsByTopicId(Long id);

    void addNewTest(Test newTest);
}
