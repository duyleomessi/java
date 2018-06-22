package com.jpa.db.CourseApiJpa.course.repository;

import com.jpa.db.CourseApiJpa.course.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    public List<Course> findByTopicId(Integer topicId);
}
