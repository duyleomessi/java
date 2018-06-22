package com.jpa.db.CourseApiJpa.topic.controller;

import com.jpa.db.CourseApiJpa.topic.model.Topic;
import com.jpa.db.CourseApiJpa.topic.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="")
public class TopicController {
    @Autowired
    private TopicService topicService;

    @GetMapping("/topic/list")
    public List<Topic> listAllUser() {
        return topicService.listAllTopic();
    }

    @PostMapping("/topic/new")
    public void addUser(@RequestBody Topic topic) {
        topicService.addTopic(topic);
    }

    @PutMapping("/topic/{id}")
    public void updateUser(@PathVariable Integer id, @RequestBody Topic topic) {
        topicService.updateTopic(id, topic);
    }

    @DeleteMapping("topic/{id}")
    public void deleteUser(@PathVariable Integer id) {
        topicService.deleteTopic(id);
    }
}

