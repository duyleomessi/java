package com.jpa.db.CourseApiJpa.course.controller;

import com.jpa.db.CourseApiJpa.course.model.Course;
import com.jpa.db.CourseApiJpa.course.service.CourseService;
import com.jpa.db.CourseApiJpa.custom.CustomErrorType;
import com.jpa.db.CourseApiJpa.topic.model.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {
    @Autowired
    private CourseService courseService;

    public final Logger logger = LoggerFactory.getLogger(CourseController.class);

    // get all courses
    @GetMapping("/course/list")
    public ResponseEntity<List<Course>> listAllCourse() {
        List<Course> courses = courseService.listAllCourse();
        if (courses.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // no_content or not_found
        }
        return new ResponseEntity<List<Course>>(courses, HttpStatus.OK);
    }

    // get course by id
    @GetMapping("/course/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Integer id) {
        Course course = courseService.getCourseById(id);
        if (course == null) {
            logger.error("User with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Course with id " + id + " not found."), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Course>(course, HttpStatus.NOT_FOUND);
    }

    // get course by topic id
    @GetMapping("/topic/{topicId}/course/list")
    public List<Course> getCourseByTopicId(@PathVariable Integer topicId) {
        return courseService.getCourseByTopicId(topicId);
    }

    // post new course
    @PostMapping("/topic/{topicId}/course/new")
    public void addCourse(@RequestBody Course course, @PathVariable Integer topicId) {
        course.setTopic(new Topic(topicId, "", ""));
        courseService.addCourse(course);
    }

    // update course
    @PutMapping("/topic/{topicId}/course/{courseId}")
    public void updateCourse(@PathVariable Integer topicId, @RequestBody Course course, @PathVariable Integer courseId) {
        courseService.updateCourse(topicId, courseId, course);
    }

    // delete course by id
    @DeleteMapping("/course/{courseId}")
    public void deleteCourse(@PathVariable Integer courseId) {
        courseService.deleteCourse(courseId);
    }

}
