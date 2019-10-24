package com.kopivad.demoproject.service;

import com.kopivad.demoproject.model.Topic;
import org.springframework.stereotype.Service;

@Service
public interface TopicService {
    Iterable<Topic> getAllTopics();

    void addNewTopic(Topic topic);
}
