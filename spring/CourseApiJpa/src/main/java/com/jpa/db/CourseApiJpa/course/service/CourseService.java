package com.jpa.db.CourseApiJpa.course.service;

import com.jpa.db.CourseApiJpa.course.model.Course;
import com.jpa.db.CourseApiJpa.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public List<Course> listAllCourse() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Integer id) {
        return courseRepository.getOne(id);
    }

    public List<Course> getCourseByTopicId(Integer topicId) {
        return courseRepository.findByTopicId(topicId);
    }

    public void addCourse(Course course) {
        courseRepository.save(course);
    }

    public void updateCourse(Integer topicId, Integer courseId, Course course) {
        courseRepository.save(course);
    }

    public void deleteCourse(Integer courseId) {
        courseRepository.deleteById(courseId);
    }
}
