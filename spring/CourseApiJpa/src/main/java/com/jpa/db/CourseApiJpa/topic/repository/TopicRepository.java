package com.jpa.db.CourseApiJpa.topic.repository;

import com.jpa.db.CourseApiJpa.topic.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TopicRepository extends JpaRepository<Topic, Integer> {

}
