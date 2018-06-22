package com.jpa.db.CourseApiJpa.topic.service;

import com.jpa.db.CourseApiJpa.topic.model.Topic;
import com.jpa.db.CourseApiJpa.topic.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;

    // get all user
    public List<Topic> listAllTopic() {
        return topicRepository.findAll();
    }

    // add new user
    public void addTopic(Topic topic) {
        topicRepository.save(topic);
    }

    // update user
    public void updateTopic(Integer id, Topic topic) {
        topicRepository.save(topic);
    }

    // delete user
    public void deleteTopic(Integer id) {
        topicRepository.deleteById(id);
    }

}
