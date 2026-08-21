package com.platformcommons.sms.service;

import com.platformcommons.sms.dto.CourseRequest;
import com.platformcommons.sms.dto.CourseResponse;
import com.platformcommons.sms.entity.Course;
import com.platformcommons.sms.exception.ResourceNotFoundException;
import com.platformcommons.sms.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public CourseResponse createCourse(CourseRequest request) {
        Course course = new Course();
        course.setName(request.getName());
        course.setDescription(request.getDescription());
        course.setCourseType(request.getCourseType());
        course.setDuration(request.getDuration());
        if (request.getTopics() != null) {
            course.setTopics(request.getTopics());
        }
        return toResponse(courseRepository.save(course));
    }

    public List<CourseResponse> getAllCourses() {
        return courseRepository.findAll().stream().map(this::toResponse).toList();
    }

    public Course getCourseEntity(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + courseId));
    }

    private CourseResponse toResponse(Course course) {
        CourseResponse response = new CourseResponse();
        response.setId(course.getId());
        response.setName(course.getName());
        response.setDescription(course.getDescription());
        response.setCourseType(course.getCourseType());
        response.setDuration(course.getDuration());
        response.setTopics(course.getTopics());
        return response;
    }
}
