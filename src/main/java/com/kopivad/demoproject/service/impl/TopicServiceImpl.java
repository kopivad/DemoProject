package com.kopivad.demoproject.service.impl;

import com.kopivad.demoproject.dao.TopicRepository;
import com.kopivad.demoproject.model.Test;
import com.kopivad.demoproject.model.Topic;
import com.kopivad.demoproject.service.TestService;
import com.kopivad.demoproject.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicServiceImpl implements TopicService {
    private TopicRepository topicRepository;
    private TestService testService;

    @Autowired
    public void setTopicRepository(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Autowired
    public void setTestService(TestService testService) {
        this.testService = testService;
    }

    @Override
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    @Override
    public List<Test> getAllTestsByTopicId(Long id) {
        return testService.getTestsByTopicId(id);
    }

    @Override
    public void addNewTopic(Topic topic) {
        topicRepository.save(topic);
    }

    @Override
    public Topic findTopicById(Long id) {
        return topicRepository.findTopicById(id);
    }
}
