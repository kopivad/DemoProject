package com.kopivad.demoproject.service;

import com.kopivad.demoproject.dao.TopicRepository;
import com.kopivad.demoproject.model.Topic;
import org.springframework.stereotype.Service;

@Service
public class TopicServiceImpl implements TopicService {
    private final TopicRepository topicRepository;

    public TopicServiceImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public Iterable<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    @Override
    public void addNewTopic(Topic topic) {
        topicRepository.save(topic);
    }
}
