package com.kopivad.demoproject.service.impl;

import com.kopivad.demoproject.dao.TestRepository;
import com.kopivad.demoproject.model.Test;
import com.kopivad.demoproject.model.Topic;
import com.kopivad.demoproject.service.TestService;
import com.kopivad.demoproject.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    private TestRepository testRepository;
    private TopicService topicService;


    @Autowired
    public void setTestRepository(TestRepository testRepository) {
        this.testRepository = testRepository;
    }
    @Autowired
    public void setTopicService(TopicService topicService) {
        this.topicService = topicService;
    }

    @Override
    public List<Test> getAllTestsByTopicId(Long id) {
        return testRepository.getAllByTopicId(id);
    }

    @Override
    public Topic findCurrentTopicById(Long id) {
        return topicService.findTopicById(id);
    }

    @Override
    public List<Test> getTestsByTopicId(Long id) {
        return testRepository.getAllByTopicId(id);
    }

    @Override
    public void addNewTest(Test newTest) {
        testRepository.save(newTest);
    }
}
